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
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieService {
    
    private PartieDAO partieDAO = new PartieDAO();
    private JoueurDAO joueurDAO = new JoueurDAO();
    private CarteDAO carteDAO = new CarteDAO();
    
    public List<Partie> listerPartieNonDemarrees(){
        
        return partieDAO.listerPartieNonDemarrees();
        
    }
    
    public Partie creerNouvellePartie(String nomPartie){
        
        Partie p = new Partie();
        p.setNom(nomPartie);
        partieDAO.ajouterNouvellePartie(p);
        return p;
        
    }
    
    public void demarrerPartie(Long partieID){
                
        Long tailleListeJoueurs = partieDAO.rechercherTailleListeJoueursParID(partieID);
        if ( tailleListeJoueurs < 2L){  
            throw new RuntimeException("Il faut au moins 2 joueurs dans la partie");
        }
        List<Joueur> joueurs = partieDAO.rechercherJoueursParID(partieID);
        for(Joueur j : joueurs){
            if (j.getOrdre()==1L){
                j.setEtatJoueur(Joueur.etat.A_LA_MAIN);
                joueurDAO.modifier(j);
            }
            for(int i = 0; i < 7; i++){
                partieDAO.distribuerUneCarteAleatoire(j);
            }
        }
        
    }
    
    public void passerTour(Long partieID, Long joueurID){
        
        Joueur j = joueurDAO.rechercherParID(joueurID);
        partieDAO.distribuerUneCarteAleatoire(j);
        this.passerLaMainAuJoueurSuivant(partieID);
        
    }
    
    public void passerLaMainAuJoueurSuivant(Long partieID) {
            
        int nbDeJoueurEncoreVivants = this.chercherNombreDeJoueurVivant(partieID);
        Joueur j = partieDAO.rechercheJoueurQuiALaMainParPartieID(partieID);
        if (nbDeJoueurEncoreVivants == 1){
            j.setEtatJoueur(Joueur.etat.GAGNANT);
            joueurDAO.modifier(j);
            return;
        }
        j.setEtatJoueur(Joueur.etat.N_A_PAS_LA_MAIN);
        joueurDAO.modifier(j);
        Joueur joueurSuivant = joueurDAO.rechercherJoueurSuivant(partieID, j.getOrdre());
        while (joueurSuivant.getEtatJoueur() != Joueur.etat.N_A_PAS_LA_MAIN){
            if (joueurSuivant.getEtatJoueur() == Joueur.etat.SOMMEIL_PROFOND){
                joueurSuivant.setEtatJoueur(Joueur.etat.N_A_PAS_LA_MAIN);
                joueurDAO.modifier(joueurSuivant);
                joueurSuivant = joueurDAO.rechercherJoueurSuivant(partieID, joueurSuivant.getOrdre());
            }
            else if (joueurSuivant.getEtatJoueur() == Joueur.etat.PERDU){
                joueurSuivant = joueurDAO.rechercherJoueurSuivant(partieID, joueurSuivant.getOrdre());
            }
        }
        joueurSuivant.setEtatJoueur(Joueur.etat.A_LA_MAIN);
        joueurDAO.modifier(joueurSuivant);
    }

    public int chercherNombreDeJoueurVivant(Long partieID) {
        
        List<Joueur> joueurs = partieDAO.rechercherJoueursParID(partieID);
        int nbDeJoueurEncoreVivants = 0;
        for (Joueur joueur : joueurs){
            if (joueur.getEtatJoueur() != Joueur.etat.PERDU){
                nbDeJoueurEncoreVivants++;
            }
        }
        return nbDeJoueurEncoreVivants;
        
    }
    
    public List<Joueur> rechercherJoueursParID(Long partieID){
        
        return partieDAO.rechercherJoueursParID(partieID);
        
    }

    public Joueur aQuiLeTour(Long idPartie) {
        
        List<Joueur> joueurs = partieDAO.rechercherJoueursParID(idPartie);
        Joueur joueur = new Joueur();
        for(Joueur j : joueurs){
            if (j.getEtatJoueur() == Joueur.etat.A_LA_MAIN ){
                joueur = j;
            }   
        }
        return joueur;
        
    }

    public String rechercherParID(Long idPartie) {

        return partieDAO.rechercherParID(idPartie).getNom();

    }
    
}
