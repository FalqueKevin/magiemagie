/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.dao;

import atos.magieMagie.entity.Carte;
import atos.magieMagie.entity.Joueur;
import atos.magieMagie.entity.Partie;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieDAO {
    
    private CarteDAO carteDAO = new CarteDAO();
    private JoueurDAO joueurDAO = new JoueurDAO();
    
    public List<Partie> listerPartieNonDemarrees(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT p FROM Partie p EXCEPT SELECT p FROM Partie p JOIN p.joueurs j WHERE j.etatJoueur IN (:etat_gagnant, :etat_alamain)");
        query.setParameter("etat_gagnant", Joueur.etat.GAGNANT);
        query.setParameter("etat_alamain", Joueur.etat.A_LA_MAIN);
        return query.getResultList();
        
    }
    
    public void ajouterNouvellePartie(Partie p){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
    }

    public Partie rechercherParID(long partieID) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        return em.find(Partie.class, partieID);

    }

    public List<Joueur> rechercherJoueursParID(Long partieID) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT p.joueurs FROM Partie p WHERE p.id =:ID");
        query.setParameter("ID", partieID);
        return query.getResultList();
        
    }

    public void distribuerUneCarteAleatoire(Joueur j) {
        
        Carte c = new Carte();
        Carte.typeIngredient[] tabTypeIngredients = Carte.typeIngredient.values();
        Random r = new Random();
        int n = r.nextInt(tabTypeIngredients.length);
        c.setIngredient(tabTypeIngredients[n]);
        j.getCartes().add(c);
        c.setJoueur(j);
        carteDAO.ajouter(c);   
    
    }

    public Long rechercherTailleListeJoueursParID(Long partieID) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(p.joueurs) FROM Partie p WHERE p.id =:idPartie");
        query.setParameter("idPartie", partieID);
        return (Long)query.getSingleResult();
        
    }

    public Joueur rechercheJoueurQuiALaMainParPartieID(Long partieID) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partie p WHERE p.id =:idPartie AND j.etatJoueur =:etat");
        query.setParameter("idPartie", partieID);
        query.setParameter("etat", Joueur.etat.A_LA_MAIN);
        return (Joueur)query.getSingleResult();
        
    }
    
}
