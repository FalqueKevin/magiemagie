/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.test;

import atos.magieMagie.entity.Partie;
import atos.magieMagie.service.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class PartieServiceTest {
    
    private PartieService service = new PartieService();
    
    @Test
    public void creerNouvellePartieOK(){
        
        Partie p = service.creerNouvellePartie("Partie1");
        assertNotNull(p.getId());
        
    }
    
}
