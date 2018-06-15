/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.service;

import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Partie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Administrateur
 */
public class PartieService {
    
    private PartieDAO dao = new PartieDAO();
    
    public List<Partie> listerPartieNonDemarrees(){
        
        return dao.listerPartieNonDemarrees();
        
    }
    
    public Partie creerNouvellePartie(String nomPartie){
        
        Partie p = new Partie();
        p.setNom(nomPartie);
        dao.ajouterNouvellePartie(p);
        return p;
        
    }
    
    public void demarrerPartie(Long PartieID, Long joueursID){
        
        
        
    }
    
}
