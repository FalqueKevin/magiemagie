/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.dao;

import atos.magieMagie.entity.Carte;
import atos.magieMagie.entity.Joueur;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class CarteDAO {

    public void ajouter(Carte c) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit(); 
        
    }

    public void supprimer(Carte c) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit(); 
        
    }

    public void modifier(Carte c) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();

    }

    public Carte rechercherUneCarteParID(Long carteID) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        return em.find(Carte.class, carteID);
        
    }
    
}
