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
public class JoueurDAO {
    
    /**
     * Renvoie le joueur dont le pseudo existe en BD ou null si pas trouvé.
     * @param pseudo
     * @return 
     */
    public Joueur rechercherParPseudo(String pseudo){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT j "
                + "                     FROM Joueur j "
                + "                     WHERE j.pseudo =:lePseudo");
        query.setParameter("lePseudo", pseudo);
        
        List<Joueur> joueurTrouve = query.getResultList();
        
        if (joueurTrouve.isEmpty())
            return null;
        return joueurTrouve.get(0);
        
    }
    
    public Long rechercherOrdreNouveauJoueur(long PartieID){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT MAX(j.ordre) "
                + "                     FROM Joueur j "
                + "                         JOIN j.partie p"
                + "                     WHERE p.id =:IDP");
        query.setParameter("IDP", PartieID);
        if (query.getSingleResult()==null)
            return 1L;
        return (Long)query.getSingleResult()+1;
    }

    public void ajouter(Joueur j) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(j);
        em.getTransaction().commit();
        
    }

    public void modifier(Joueur j) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(j);
        em.getTransaction().commit();
        
    }
    
}
