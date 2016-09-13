/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jeroen
 */
@Entity
@Table(name = "bestelde_artikelen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BesteldeArtikelen.findAll", query = "SELECT b FROM BesteldeArtikelen b")})
public class BesteldeArtikelen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BesteldeArtikelenPK besteldeArtikelenPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aantal")
    private int aantal;
    @JoinColumn(name = "artikel_idartikel", referencedColumnName = "idartikel", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikel artikel;
    @JoinColumn(name = "bestelling_idbestelling", referencedColumnName = "idbestelling", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bestelling bestelling;

    public BesteldeArtikelen() {
    }

    public BesteldeArtikelen(BesteldeArtikelenPK besteldeArtikelenPK) {
        this.besteldeArtikelenPK = besteldeArtikelenPK;
    }

    public BesteldeArtikelen(BesteldeArtikelenPK besteldeArtikelenPK, int aantal) {
        this.besteldeArtikelenPK = besteldeArtikelenPK;
        this.aantal = aantal;
    }

    public BesteldeArtikelen(int bestellingIdbestelling, int artikelIdartikel) {
        this.besteldeArtikelenPK = new BesteldeArtikelenPK(bestellingIdbestelling, artikelIdartikel);
    }

    public BesteldeArtikelenPK getBesteldeArtikelenPK() {
        return besteldeArtikelenPK;
    }

    public void setBesteldeArtikelenPK(BesteldeArtikelenPK besteldeArtikelenPK) {
        this.besteldeArtikelenPK = besteldeArtikelenPK;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public Bestelling getBestelling() {
        return bestelling;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (besteldeArtikelenPK != null ? besteldeArtikelenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BesteldeArtikelen)) {
            return false;
        }
        BesteldeArtikelen other = (BesteldeArtikelen) object;
        if ((this.besteldeArtikelenPK == null && other.besteldeArtikelenPK != null) || (this.besteldeArtikelenPK != null && !this.besteldeArtikelenPK.equals(other.besteldeArtikelenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.BesteldeArtikelen[ besteldeArtikelenPK=" + besteldeArtikelenPK + " ]";
    }
    
}
