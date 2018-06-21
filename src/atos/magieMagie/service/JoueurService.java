/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.service;

import atos.magieMagie.dao.CarteDAO;
import atos.magieMagie.dao.JoueurDAO;
import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Carte;
import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrateur
 */
public class JoueurService {
    
    private JoueurDAO joueurDAO = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    private CarteDAO carteDAO = new CarteDAO();
    
    public Joueur rejoindrePartie(String nomJoueur, String avatar, Long partieID){
        
        // Recherche si le joueur existe déjà
        Joueur j = joueurDAO.rechercherParPseudo(nomJoueur);
        
        if (j == null){
            //Le joueur n'existe pas encore
            j = new Joueur();
            j.setPseudo(nomJoueur);
            j.setNbPartiesGagnees(0L);
            j.setNbPartiesJouees(0L);
        }
        j.setAvatar(avatar);
        j.setEtatJoueur(Joueur.etat.N_A_PAS_LA_MAIN);
        j.setOrdre(joueurDAO.rechercherOrdreNouveauJoueur(partieID));
        Partie p = partieDAO.rechercherParID(partieID);
        j.setPartie(p);
        p.getJoueurs().add(j);
        
        if (j.getId()==null){ //Le joueur n'existe pas encore
            joueurDAO.ajouter(j);
        }else { //Le joueur existe déja
            joueurDAO.modifier(j);
        }
        
        return j;
        
    }
    
    
    public void lancerSort(Long idPartie, Long idJoueurLanceur, Long carteDonnee, Long idJoueurVictime, Long ingredient1, Long ingredient2){
        
        Carte carteIngredient1 = carteDAO.rechercherUneCarteParID(ingredient1);
        Carte carteIngredient2 = carteDAO.rechercherUneCarteParID(ingredient2);
        String combinaisonDesCartes = (carteIngredient1.getIngredient().toString() + carteIngredient2.getIngredient().toString());
        switch (combinaisonDesCartes){
            case "LAPIS_LAZULIAILE_CHAUVE_SOURIS":
                this.sortDivination(idPartie);
                break;
            case "MANDRAGOREAILE_CHAUVE_SOURIS":
                this.sortSommeilProfond(idJoueurVictime);
                break;
            case "CORNE_LICORNEMANDRAGORE":
                this.sortFiltreAmour(idPartie, idJoueurLanceur, idJoueurVictime);
                break;
            case "CORNE_LICORNEBAVE_CRAPAUD":
                this.sortInvisibilité(idPartie, idJoueurLanceur);
                break;
            case "BAVE_CRAPAUDLAPIS_LAZULI":
                this.sortHypnose(idPartie, carteDonnee, idJoueurLanceur, idJoueurVictime);
                break;
            default:
                break;
        }
        carteDAO.supprimer(carteIngredient1);
        carteDAO.supprimer(carteIngredient2);
        List<Joueur> joueurs = partieDAO.rechercherParID(idPartie).getJoueurs();
        for(Joueur j : joueurs){
            if (j.getCartes().size()==0){
                j.setEtatJoueur(Joueur.etat.PERDU);
                joueurDAO.modifier(j);
            }
        }
       
    }
    
    public List<Joueur> sortDivination(Long idPartie) {

        List<Joueur> joueurs = partieDAO.rechercherJoueursParID(idPartie);
        return joueurs;

    }

    public void sortSommeilProfond(Long idJoueurVictime) {

        Joueur j = joueurDAO.rechercherParID(idJoueurVictime);
        j.setEtatJoueur(Joueur.etat.SOMMEIL_PROFOND);
        joueurDAO.modifier(j);

    }

    public void sortFiltreAmour(Long idPartie, Long idJoueurLanceur, Long idJoueurVictime) {
        
        Joueur joueurLanceur = joueurDAO.rechercherParID(idJoueurLanceur);
        Joueur joueurVictime = joueurDAO.rechercherParID(idJoueurVictime);
        List<Carte> cartesDuJoueurVictime = joueurDAO.listerCartesJoueurs(idJoueurVictime);
        if (cartesDuJoueurVictime.size()==1){
            joueurVictime.getCartes().clear();
            joueurDAO.modifier(joueurVictime);
            carteDAO.supprimer(cartesDuJoueurVictime.get(0));
            return;
        }
        int nbCartesVolees = cartesDuJoueurVictime.size() / 2;
        for(int i = 0; i < nbCartesVolees; i++){
            this.volerUneCarteAuHasard(joueurLanceur, joueurVictime);
        }
        
    }

    public void sortInvisibilité(Long idPartie, Long idJoueurLanceur) {
        
        Joueur joueurLanceur = joueurDAO.rechercherParID(idJoueurLanceur);
        List<Joueur> joueurs = partieDAO.rechercherParID(idPartie).getJoueurs();
        for(Joueur joueurVictime : joueurs){
            this.volerUneCarteAuHasard(joueurLanceur, joueurVictime);
        }
        
    }

    public void sortHypnose(Long idPartie, Long IDcarteDonnee, Long idJoueurLanceur, Long idJoueurVictime) {

        Joueur joueurLanceur = joueurDAO.rechercherParID(idJoueurLanceur);
        Joueur JoueurVictime = joueurDAO.rechercherParID(idJoueurVictime);
        List<Carte> cartesDuJoueurVictime = joueurDAO.listerCartesJoueurs(idJoueurVictime);
        for(int i = 0; i < 3; i++){
            this.volerUneCarteAuHasard(joueurLanceur, JoueurVictime);
            if (cartesDuJoueurVictime.size()==0){
                return;
            }
        }
        Carte carteDonnee = carteDAO.rechercherUneCarteParID(IDcarteDonnee);
        this.donnerUneCarteDeSonChoix(carteDonnee, JoueurVictime);

    }

    private void volerUneCarteAuHasard(Joueur joueurLanceur, Joueur joueurVictime) {

        Carte c = new Carte();
        Random r = new Random();
        int n = r.nextInt(joueurVictime.getCartes().size());
        c = (joueurVictime.getCartes().get(n));
        c.setJoueur(joueurLanceur);
        joueurLanceur.getCartes().add(c);
        carteDAO.modifier(c);
        
    }

    private void donnerUneCarteDeSonChoix(Carte carteDonnee, Joueur JoueurVictime) {

        carteDonnee.setJoueur(JoueurVictime);
        JoueurVictime.getCartes().add(carteDonnee);
        carteDAO.modifier(carteDonnee);

    }
    
}
