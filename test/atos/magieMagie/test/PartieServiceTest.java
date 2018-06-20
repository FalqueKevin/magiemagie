/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.test;

import atos.magieMagie.dao.PartieDAO;
import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import atos.magieMagie.service.JoueurService;
import atos.magieMagie.service.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class PartieServiceTest {

    private PartieService partieService = new PartieService();
    private JoueurService joueurService = new JoueurService();
    
    @Test
    public void passerTourOK(){
        
        partieService.passerTour(1L, 4L);

    }
    
    //@Test
    public void demarrerPartieOK() {

        long id = partieService.creerNouvellePartie("Partie 1").getId();

        joueurService.rejoindrePartie("Kevin", "image", id);
        joueurService.rejoindrePartie("Pierre", "image", id);
        joueurService.rejoindrePartie("Paul", "image", id);
        joueurService.rejoindrePartie("Jacques", "image", id);
        
        partieService.demarrerPartie(id);

    }

//    @Test
    public void creerNouvellePartieOK() {

        Partie p = partieService.creerNouvellePartie("Partie1");
        assertNotNull(p.getId());

    }

}
