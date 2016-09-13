/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jeroen
 */
@Embeddable
public class BesteldeArtikelenPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "bestelling_idbestelling")
    private int bestellingIdbestelling;
    @Basic(optional = false)
    @NotNull
    @Column(name = "artikel_idartikel")
    private int artikelIdartikel;

    public BesteldeArtikelenPK() {
    }

    public BesteldeArtikelenPK(int bestellingIdbestelling, int artikelIdartikel) {
        this.bestellingIdbestelling = bestellingIdbestelling;
        this.artikelIdartikel = artikelIdartikel;
    }

    public int getBestellingIdbestelling() {
        return bestellingIdbestelling;
    }

    public void setBestellingIdbestelling(int bestellingIdbestelling) {
        this.bestellingIdbestelling = bestellingIdbestelling;
    }

    public int getArtikelIdartikel() {
        return artikelIdartikel;
    }

    public void setArtikelIdartikel(int artikelIdartikel) {
        this.artikelIdartikel = artikelIdartikel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) bestellingIdbestelling;
        hash += (int) artikelIdartikel;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BesteldeArtikelenPK)) {
            return false;
        }
        BesteldeArtikelenPK other = (BesteldeArtikelenPK) object;
        if (this.bestellingIdbestelling != other.bestellingIdbestelling) {
            return false;
        }
        if (this.artikelIdartikel != other.artikelIdartikel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.BesteldeArtikelenPK[ bestellingIdbestelling=" + bestellingIdbestelling + ", artikelIdartikel=" + artikelIdartikel + " ]";
    }
    
}
