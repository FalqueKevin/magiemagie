/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.main;

import atos.magieMagie.dao.CarteDAO;
import atos.magieMagie.dao.JoueurDAO;
import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Carte;
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
    private CarteDAO carteDAO = new CarteDAO();
    private JoueurDAO joueurDAO = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    
    private void menuInGame(Long idPartie, Long idJoueur) throws InterruptedException {
        
        while (true){
            if (partieDAO.rechercheJoueurQuiALaMainParPartieID(idPartie) == null){
                System.out.println("Fin de la partie, le joueur " + partieDAO.rechercheJoueurGagnant(idPartie) + " à gagné !");
                menuPrincipal();
            }
            Joueur joueurActuel = partieDAO.rechercheJoueurQuiALaMainParPartieID(idPartie);
            if (idJoueur == joueurActuel.getId()){
                String choix = new String();
                System.out.println("********************************************");
                System.out.println("*************** Menu Partie ****************");
                System.out.println("********************************************");
                String nom = partieService.rechercherNomParID(idPartie);
                System.out.println("*** Partie : " + nom);
                System.out.println("********************************************");
                System.out.println("*** Vous êtes le joueur : " + joueurActuel.getPseudo());
                System.out.println("*** Vos cartes :                         ***");
                List<Carte> cartesDuJoueur = carteDAO.rechercherCartesParID(joueurActuel.getId());
                for(Carte c : cartesDuJoueur){
                    System.out.println(c.getIngredient() + " ( ID : " + c.getId() + " )");
                }
                System.out.println("********************************************");
                System.out.println("*** Les adversaires sont :               ***");
                List<Joueur> adversaires = partieService.rechercherJoueursParID(idPartie);
                for(Joueur ad : adversaires){
                    if (ad.getId() != joueurActuel.getId()){
                        System.out.println(" - (" + ad.getEtatJoueur() + ") " + ad.getPseudo() + " : " + ad.getCartes().size() + " cartes");
                    }
                }
                System.out.println("********************************************");
                System.out.println("*** 1 : Lancer un sort                   ***");
                System.out.println("*** 2 : Passer ton tour                  ***");
                System.out.println("********************************************");
                System.out.println("********************************************");
                System.out.println("*** Votre choix:                         ***");
                System.out.println("********************************************");
                System.out.println(" ");
                Scanner s = new Scanner(System.in);
                choix = s.nextLine();
                switch(choix){
                    case "1":
                        System.out.println("Entrer l'ID de votre première carte utilisée pour le sort");
                        Long id1 = Long.valueOf(s.nextLong());
                        System.out.println("Entrer l'ID de votre deuxième carte utilisée pour le sort");
                        Long id2 = Long.valueOf(s.nextLong());
                        Carte carteIngredient1 = carteDAO.rechercherUneCarteParID(id1);
                        Carte carteIngredient2 = carteDAO.rechercherUneCarteParID(id2);
                        String combinaisonDesCartes = (carteIngredient1.getIngredient().toString() + carteIngredient2.getIngredient().toString());
                        switch (combinaisonDesCartes){
                            case "LAPIS_LAZULIAILE_CHAUVE_SOURIS":
                                //List<Joueur> joueursSoumis = joueurService.sortDivination(idPartie);
                                System.out.println("Là je suis censé afficher tout les adversaires et toutes leurs cartes mais j'ai la flegme en interface console");
                                break;
                            case "MANDRAGOREAILE_CHAUVE_SOURIS":
                                System.out.println("Entrer le pseudo de votre victime pour le sort");
                                Scanner s1 = new Scanner(System.in);
                                String pseudoJoueurVictimeSommeilProfond = s1.nextLine();
                                joueurService.sortSommeilProfond(joueurDAO.rechercherParPseudo(pseudoJoueurVictimeSommeilProfond).getId());
                                break;
                            case "CORNE_LICORNEMANDRAGORE":
                                System.out.println("Entrer le pseudo de votre victime pour le sort");
                                Scanner s2 = new Scanner(System.in);
                                String pseudoJoueurVictimeFiltreAmour = s2.nextLine();
                                joueurService.sortFiltreAmour(idPartie, joueurActuel.getId(), joueurDAO.rechercherParPseudo(pseudoJoueurVictimeFiltreAmour).getId());
                                break;
                            case "CORNE_LICORNEBAVE_CRAPAUD":
                                joueurService.sortInvisibilité(idPartie, joueurActuel.getId());
                                break;
                            case "BAVE_CRAPAUDLAPIS_LAZULI":
                                System.out.println("Entrer le pseudo de votre victime pour le sort");
                                Scanner s3 = new Scanner(System.in);
                                String pseudoJoueurVictimeHypnose = s3.nextLine();
                                System.out.println("Entrer l'ID d'une de vos carte pour la donnée à la victime");
                                Scanner s4 = new Scanner(System.in);
                                Long idCarteDefossee = Long.valueOf(s4.nextLong());
                                joueurService.sortHypnose(idPartie, idCarteDefossee, joueurActuel.getId(), joueurDAO.rechercherParPseudo(pseudoJoueurVictimeHypnose).getId());
                                break;
                            default:
                                break;
                        }
                        joueurService.detruireCartesUtiliseesPourSort(idPartie, carteIngredient1.getId(), carteIngredient2.getId());
                        if (partieService.passerLaMainAuJoueurSuivant(idPartie, joueurActuel.getId()) != null){
                            joueurActuel.setEtatJoueur(Joueur.etat.GAGNANT);
                            joueurActuel.setNbPartiesJouees(joueurActuel.getNbPartiesJouees()+1);
                            joueurActuel.setNbPartiesGagnees(joueurActuel.getNbPartiesGagnees()+1);
                            joueurDAO.modifier(joueurActuel);
                            System.out.println("Fin de la partie, le joueur " +joueurActuel.getPseudo() + " à gagné !");
                            menuPrincipal();
                        }
                        break;
                    case "2":
                        partieService.passerTour(idPartie, joueurActuel.getId());
                        break;
                }
            }else{
                System.out.println("********************************************");
                System.out.println("****************** ATTENTE *****************");
                System.out.println("********************************************");
                Thread.sleep(500);
            }
        }
        
    }
    
    public void menuLobby(Long idPartie, Long idJoueur) throws InterruptedException{
        
        while (true){
            System.out.println("********************************************");
            System.out.println("************** Menu Lancement **************");
            System.out.println("********************************************");
            String nom = partieService.rechercherNomParID(idPartie);
            System.out.println("*** Partie : " + nom);
            System.out.println("********************************************");
            System.out.println("*** Voici les joueurs de la partie :     ***");
            List<Joueur> joueurs = partieService.rechercherJoueursParID(idPartie);
            for(Joueur j : joueurs){
                System.out.println(" - " + j.getPseudo());
            }
            System.out.println("********************************************");
            System.out.println("*** 1 : Démarrer la partie               ***");
            System.out.println("********************************************");
            System.out.println("********************************************");
            System.out.println("*** Votre choix:                         ***");
            System.out.println("********************************************");
            System.out.println(" ");
            Scanner s = new Scanner(System.in);
            String choix = s.nextLine();
            if (choix.equals("1")){
                partieService.demarrerPartie(idPartie);
                menuInGame(idPartie, idJoueur);
            }else if (partieDAO.rechercheJoueurQuiALaMainParPartieID(idPartie) != null){
                menuInGame(idPartie, idJoueur);
            }else{
                Thread.sleep(500);
            }
        }
        
    }
    
    public void menuPrincipal() throws InterruptedException{
        
        String choix = new String();
        do{
            System.out.println("********************************************");
            System.out.println("************** Menu Principal **************");
            System.out.println("********************************************");
            List<Partie> partiesNonDemarrees = partieService.listerPartieNonDemarrees();
            System.out.println("********************************************");
            System.out.println("**** Liste des parties non démarrées :  ****");
            System.out.println("********************************************");
            if (partiesNonDemarrees.isEmpty()){
                System.out.println("** La liste est vide ! Créer une partie ! **");
                System.out.println("********************************************");
                System.out.println(" ");
            }else {
                for(Partie p : partiesNonDemarrees){
                    System.out.println(" - " + p.getNom() + " (Son ID pour la rejoindre est : " + p.getId() + ")");
                }
                System.out.println("********************************************");
                System.out.println(" ");
            }
            System.out.println("********************************************");
            System.out.println("*** 1 : Créer une partie                 ***");
            System.out.println("*** 2 : Rejoindre une partie             ***");
            System.out.println("*** Q : Quitter                          ***");
            System.out.println("********************************************");
            System.out.println("*** Votre choix:                         ***");
            System.out.println("********************************************");
            System.out.println(" ");
            Scanner s = new Scanner(System.in);
            choix = s.nextLine();
            switch(choix){
                case "1":
                    System.out.println("Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    Long id = partieService.creerNouvellePartie(nomPartie).getId();
                    System.out.println("Partie : " + nomPartie + " crée !");
                    System.out.println("Entrer votre pseudo pour la partie");
                    String pseudo = s.nextLine();
                    System.out.println("Entrer votre avatar pour la partie");
                    String avatar = s.nextLine();
                    menuLobby(id, joueurService.rejoindrePartie(pseudo, avatar, id).getId());
                    break;
                case "2":
                    System.out.println("Entrer votre pseudo pour la partie");
                    String pseudo2 = s.nextLine();
                    System.out.println("Entrer votre avatar pour la partie");
                    String avatar2 = s.nextLine();
                    System.out.println("Donnez l'ID de la partie que vous voulez rejoindre");
                    Long idPartie = Long.valueOf(s.nextLong());
                    menuLobby(idPartie, joueurService.rejoindrePartie(pseudo2, avatar2, idPartie).getId());
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Choix inconnu");
            }
        }while(!choix.equals("Q"));
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        Main main = new Main();
        main.menuPrincipal();
        
    }

}
