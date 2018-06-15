/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.service;

import atos.magieMagie.dao.JoueurDAO;
import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class JoueurService {
    
    private JoueurDAO dao = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    
    public void rejoindrePartie(String nomJoueur, String avatar, long PartieID){
        
        // Recherche si le joueur existe déjà
        Joueur j = dao.rechercherParPseudo(nomJoueur);
        
        if (j==null){
            //Le joueur n'existe pas encore
            j = new Joueur();
            j.setPseudo(nomJoueur);
            j.setNbPartiesGagnees(0L);
            j.setNbPartiesJouees(0L);
        }
        j.setAvatar(avatar);
        j.setEtatJoueur(Joueur.etat.N_A_PAS_LA_MAIN);
        j.setOrdre(dao.rechercherOrdreNouveauJoueur(PartieID));
        Partie p = partieDAO.rechercherParID(PartieID);
        p.getJoueurs().add(j);
        j.setPartie(p);
        
        if (j.getId()==null){ //Le joueur n'existe pas encore
            dao.ajouter(j);
        }else { //Le joueur existe déja
            dao.modifier(j);
        }
        
    }
    
}
