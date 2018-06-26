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
    
    
    public void detruireCartesUtiliseesPourSort(Long idPartie, Long ingredient1, Long ingredient2){
        
        Carte carteIngredient1 = carteDAO.rechercherUneCarteParID(ingredient1);
        Carte carteIngredient2 = carteDAO.rechercherUneCarteParID(ingredient2);
        carteDAO.supprimer(carteIngredient1);
        carteDAO.supprimer(carteIngredient2);
        List<Joueur> joueurs = partieDAO.rechercherJoueursParID(idPartie);
        for(Joueur j : joueurs){
            if (j.getCartes().size()==0){
                j.setEtatJoueur(Joueur.etat.PERDU);
                j.setNbPartiesJouees(j.getNbPartiesJouees()+1);
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
        List<Carte> cartesDuJoueurVictime = joueurVictime.getCartes();
        if (cartesDuJoueurVictime.size()==1){
            joueurVictime.setEtatJoueur(Joueur.etat.PERDU);
            joueurVictime.setNbPartiesJouees(joueurVictime.getNbPartiesJouees()+1);
            joueurDAO.modifier(joueurVictime);
            carteDAO.supprimer(cartesDuJoueurVictime.get(0));
            return;
        }
        if(joueurVictime.getCartes().size()>0){
            int nbCartesVolees = cartesDuJoueurVictime.size() / 2;
            for(int i = 0; i < nbCartesVolees; i++){
                this.volerUneCarteAuHasard(joueurLanceur.getId(), joueurVictime.getId());
            }
        }
        
    }

    public void sortInvisibilité(Long idPartie, Long idJoueurLanceur) {
        
        Joueur joueurLanceur = joueurDAO.rechercherParID(idJoueurLanceur);
        List<Joueur> joueurs = partieDAO.rechercherParID(idPartie).getJoueurs();
        for(Joueur joueurVictime : joueurs){
            if (joueurVictime.getCartes().size()>0){
                this.volerUneCarteAuHasard(joueurLanceur.getId(), joueurVictime.getId());
            }
        }
        
    }

    public void sortHypnose(Long idPartie, Long IDcarteDonnee, Long IDjoueurLanceur, Long IDjoueurVictime) {

        Joueur joueurLanceur = joueurDAO.rechercherParID(IDjoueurLanceur);
        Joueur joueurVictime = joueurDAO.rechercherParID(IDjoueurVictime);
        List<Carte> cartesDuJoueurVictime = joueurVictime.getCartes();
        for(int i = 0; i < 3; i++){
            if (cartesDuJoueurVictime.size()>0){
                this.volerUneCarteAuHasard(joueurLanceur.getId(), joueurVictime.getId());
            }
        }
        this.donnerUneCarteDeSonChoix(IDcarteDonnee, IDjoueurVictime);

    }

    public void volerUneCarteAuHasard(Long IDjoueurLanceur, Long IDjoueurVictime) {
        
        Joueur joueurLanceur = joueurDAO.rechercherParID(IDjoueurLanceur);
        Joueur joueurVictime = joueurDAO.rechercherParID(IDjoueurVictime);     
        Carte c = new Carte();
        Random r = new Random();
        int n = r.nextInt(joueurVictime.getCartes().size());
        c = (joueurVictime.getCartes().get(n));
        c.setJoueur(joueurLanceur);
        joueurVictime.getCartes().add(c);
        carteDAO.modifier(c);
        
    }

    public void donnerUneCarteDeSonChoix(Long IDcarteDonnee, Long IDjoueurVictime) {
        
        Carte carteDonnee = carteDAO.rechercherUneCarteParID(IDcarteDonnee);
        Joueur joueurVictime = joueurDAO.rechercherParID(IDjoueurVictime);
        carteDonnee.setJoueur(joueurVictime);
        joueurVictime.getCartes().add(carteDonnee);
        carteDAO.modifier(carteDonnee);

    }
    
}
