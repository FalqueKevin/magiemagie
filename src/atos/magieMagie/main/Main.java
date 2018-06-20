/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.main;

import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import atos.magieMagie.service.JoueurService;
import atos.magieMagie.service.PartieService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Administrateur
 */
public class Main {
    
    private PartieService partieService = new PartieService();
    private JoueurService joueurService = new JoueurService();

    public void menuPartie(Long idPartie){
        
        System.out.println("Menu Partie");
        System.out.println("**************");
        System.out.println("Vous êtes dans la partie : " + idPartie);
        System.out.println("**************");
        System.out.println("1 : Démarrer la partie");
        
    }
    
    public void menuPrincipal(){
        
        String choix = new String();
        do{
            System.out.println("**************");
            System.out.println("Menu Principal");
            System.out.println("**************");
            System.out.println("1 : Lister les parties non démarrées");
            System.out.println("2 : Créer une partie");
            System.out.println("3 : Rejoindre une partie");
            System.out.println("Q : Quitter");
            System.out.println("**************");
            System.out.print("Votre choix: ");
            Scanner s = new Scanner(System.in);
            choix = s.nextLine();
            switch(choix){
                case "1":
                    List<Partie> partiesNonDemarrees = partieService.listerPartieNonDemarrees();
                    System.out.println("Liste des parties non démarrées : ");
                    for(Partie p : partiesNonDemarrees){
                        System.out.println(" - " + p.getNom() + " (Son ID pour la rejoindre est : " + p.getId() + ")");
                    }
                    break;
                case "2":
                    System.out.println("Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    partieService.creerNouvellePartie(nomPartie);
                    System.out.println(nomPartie + ": partie crée !");
                    break;
                case "3":
                    System.out.println("Entrer son pseudo pour la partie");
                    String pseudo = s.nextLine();
                    System.out.println("Entrer son avatar pour la partie");
                    String avatar = s.nextLine();
                    System.out.println("Donnez l'ID de la partie que vous voulez rejoindre");
                    Long idPartie = s.nextLong();
                    joueurService.rejoindrePartie(pseudo, avatar, idPartie);
                    menuPartie(idPartie);
                case "Q":
                    break;
                default:
                    System.out.println("Choix inconnu");
            }
        }while(!choix.equals("Q"));
    }
    
    public static void main(String[] args) {
        
        Main main = new Main();
        main.menuPrincipal();
        
    }
    
}
