/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.test;

import atos.magieMagie.service.JoueurService;
import atos.magieMagie.service.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class JoueurServiceTest {
    
    private JoueurService service = new JoueurService();
    private PartieService partieService = new PartieService();
    
    @Test
    public void rejoindrePartieOK(){
        
        service.rejoindrePartie("keke", "image", partieService.creerNouvellePartie("Partie 1").getId());
        service.rejoindrePartie("keke2", "image", 1);
        service.rejoindrePartie("keke3", "image", 1);
        
    }
    
}
