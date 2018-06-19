/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magieMagie.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Administrateur
 */
@Entity
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String pseudo;
    private String avatar;
    public enum etat {
        N_A_PAS_LA_MAIN,
        A_LA_MAIN,
        SOMMEIL_PROFOND,
        PERDU,
        GAGNANT
    };
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private etat etatJoueur;
    @Column(nullable = false)
    private Long nbPartiesGagnees;
    @Column(nullable = false)
    private Long nbPartiesJouees;
    @JoinColumn
    @ManyToOne
    private Partie partie;
    @OneToMany(mappedBy = "joueur")
    private List<Carte> cartes = new ArrayList<>();
    @Column(nullable = false)
    private Long ordre;

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
    
    public Partie getPartie() {
        return partie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public etat getEtatJoueur() {
        return etatJoueur;
    }

    public void setEtatJoueur(etat etatJoueur) {
        this.etatJoueur = etatJoueur;
    }

    public Long getNbPartiesGagnees() {
        return nbPartiesGagnees;
    }

    public void setNbPartiesGagnees(Long nbPartiesGagnees) {
        this.nbPartiesGagnees = nbPartiesGagnees;
    }

    public Long getNbPartiesJouees() {
        return nbPartiesJouees;
    }

    public void setNbPartiesJouees(Long nbPartiesJouees) {
        this.nbPartiesJouees = nbPartiesJouees;
    }

    public List<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(List<Carte> cartes) {
        this.cartes = cartes;
    }

    public Long getOrdre() {
        return ordre;
    }

    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atos.magieMagie.entity.Joueur[ id=" + id + " ]";
    }
    
}
