/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.service;

import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Partie;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class PartieService {
    
    private PartieDAO dao = new PartieDAO();
    
    public List<Partie> listerPartieNonDemarrees(){
        
        return dao.listerPartieNonDemarrees();
        
    }
    
//    public Partie creerNouvellePartie(JoueurID,nomPartie){
//        
//        return dao.creerNouvellePartie(JoueurID,nomPartie);
//        
//    }
    
}
