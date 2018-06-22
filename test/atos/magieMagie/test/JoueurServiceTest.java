/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.test;

import atos.magieMagie.entity.Joueur;
import atos.magieMagie.service.JoueurService;
import atos.magieMagie.service.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class JoueurServiceTest {
    
    private JoueurService joueurService = new JoueurService();
    private PartieService partieService = new PartieService();
    
    //@Test
    public void ordreJoueurOK() {
        
        joueurService.rejoindrePartie("keke", "image", partieService.creerNouvellePartie("Partie 1").getId());
        //joueurService.rejoindrePartie("keke2", "image", 2);
        //Joueur j = joueurService.rejoindrePartie("keke3", "image", 2);
        //assertEquals(3L, (long)j.getOrdre());
        
    }
    
    //@Test
    public void rejoindrePartieOK(){
        
        joueurService.rejoindrePartie("Kevin", "image", partieService.creerNouvellePartie("Partie").getId());
        
    }
    
}
