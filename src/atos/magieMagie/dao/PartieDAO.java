/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.dao;

import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieDAO {
    
    public List<Partie> listerPartieNonDemarrees(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT p FROM Partie p "
                + "             EXCEPT"
                + "             SELECT p "
                + "             FROM Partie p "
                + "                 JOIN p.joueurs j "
                + "             WHERE j.etat IN (etat_gagnant, etat_alamain)");
        query.setParameter("etat_gagnant", Joueur.etat.GAGNANT);
        query.setParameter("etat_alamain", Joueur.etat.A_LA_MAIN);
        return query.getResultList();
        
    }
    
//    public Partie creerNouvellePartie(JoueurID,nomPartie){
//        
//        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Partie p = new Partie();
//        em.persist(p);
//    }
    
}