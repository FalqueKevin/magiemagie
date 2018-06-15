/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.test;

import atos.magieMagie.entity.Carte;
import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class testMapping {
    
    @Test
    public void testMapping() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        
        Partie p1 = new Partie();
        p1.setNom("Partie1");
        em.persist(p1);
        
        Joueur j1 = new Joueur();
        j1.setPartie(p1);
        j1.setPseudo("Pierre");
        j1.setNbPartiesGagnees(0L);
        j1.setNbPartiesJouees(0L);
        em.persist(j1);
        Joueur j2 = new Joueur();
        j2.setPartie(p1);
        j2.setPseudo("Jean");
        j2.setNbPartiesGagnees(0L);
        j2.setNbPartiesJouees(0L);
        em.persist(j2);
        
        Carte c1 = new Carte();
        c1.setJoueur(j1);
        em.persist(c1);
        Carte c2 = new Carte();
        c2.setJoueur(j1);
        em.persist(c2);
        Carte c3 = new Carte();
        c3.setJoueur(j2);
        em.persist(c3);
        Carte c4 = new Carte();
        c4.setJoueur(j2);
        em.persist(c4);
        
        em.getTransaction().commit();
        
    }
    
}
