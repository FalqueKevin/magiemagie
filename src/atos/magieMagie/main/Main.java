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
    
    private void menuIngame(Long idPartie) {
        
            String choix = new String();
            System.out.println("**************");
            String nom = partieService.rechercherParID(idPartie);
            System.out.println(nom);
            System.out.println("**************");
            Joueur j = partieService.aQuiLeTour(idPartie);
            System.out.println("Vous êtes le joueur : " + j.getPseudo());
            System.out.println("**************");
            System.out.println("1 : Lancer un sort");
            System.out.println("2 : Passer ton tour");
            System.out.println("**************");
            Scanner s = new Scanner(System.in);
            choix = s.nextLine();
            switch(choix){
                case "1":
                    //joueurService.lancerSort(idPartie, idPartie, idPartie, idPartie, idPartie, idPartie);
                    menuIngame(idPartie);
                    break;
                case "2":
                    partieService.passerTour(idPartie, j.getId());
                    menuIngame(idPartie);
                    break;
            }
            
    }
    
    public void menuLobby(Long idPartie){
        
        String choix = new String();
        do {
            System.out.println("**************");
            System.out.println("Menu Partie");
            System.out.println("**************");
            String nom = partieService.rechercherParID(idPartie);
            System.out.println("Vous êtes dans le menu du lancement de la partie : " + nom);
            System.out.println("**************");
            System.out.println("Voici les joueurs de la partie : ");
            List<Joueur> joueurs = partieService.rechercherJoueursParID(idPartie);
            for(Joueur j : joueurs){
                System.out.println(" - " + j.getPseudo());
            }
            System.out.println("**************");
            System.out.println("1 : Démarrer la partie");
            System.out.println("**************");
            Scanner s = new Scanner(System.in);
            choix = s.nextLine();
            if (choix.equals("1")){
                partieService.demarrerPartie(idPartie);
                menuIngame(idPartie);
            }
        } while (!choix.equals("1"));
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
                    System.out.println("**************");
                    System.out.println("Liste des parties non démarrées : ");
                    if (partiesNonDemarrees.isEmpty()){
                        System.out.println("La liste est vide mon frère ! Créer une partie !");
                        System.out.println("**************");
                    }else {
                        for(Partie p : partiesNonDemarrees){
                            System.out.println(" - " + p.getNom() + " (Son ID pour la rejoindre est : " + p.getId() + ")");
                        }
                        System.out.println("**************");
                    }    
                    break;
                case "2":
                    System.out.println("Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    Long i = partieService.creerNouvellePartie(nomPartie).getId();
                    System.out.println("Partie " + nomPartie + " crée ! (Son ID pour la rejoindre est : " + i + ")");
                    break;
                case "3":
                    System.out.println("Entrer son pseudo pour la partie");
                    String pseudo = s.nextLine();
                    System.out.println("Entrer son avatar pour la partie");
                    String avatar = s.nextLine();
                    System.out.println("Donnez l'ID de la partie que vous voulez rejoindre");
                    Long idPartie = Long.valueOf(s.nextLong());
                    joueurService.rejoindrePartie(pseudo, avatar, idPartie);
                    menuLobby(idPartie);
                    break;
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
