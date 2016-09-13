/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jeroen
 */
@Entity
@Table(name = "artikel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idartikel")
    private Integer idartikel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naam")
    private String naam;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "prijs")
    private BigDecimal prijs;
    @Size(max = 45)
    @Column(name = "omschrijving")
    private String omschrijving;
    @JoinTable(name = "artikel_has_soort", joinColumns = {
        @JoinColumn(name = "artikel_idartikel", referencedColumnName = "idartikel")}, inverseJoinColumns = {
        @JoinColumn(name = "soort_idsoort", referencedColumnName = "idsoort")})
    @ManyToMany
    private Collection<Soort> soortCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikel")
    private Collection<BesteldeArtikelen> besteldeArtikelenCollection;

    public Artikel() {
    }

    public Artikel(Integer idartikel) {
        this.idartikel = idartikel;
    }

    public Artikel(Integer idartikel, String naam, BigDecimal prijs) {
        this.idartikel = idartikel;
        this.naam = naam;
        this.prijs = prijs;
    }

    public Integer getIdartikel() {
        return idartikel;
    }

    public void setIdartikel(Integer idartikel) {
        this.idartikel = idartikel;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @XmlTransient
    public Collection<Soort> getSoortCollection() {
        return soortCollection;
    }

    public void setSoortCollection(Collection<Soort> soortCollection) {
        this.soortCollection = soortCollection;
    }

    @XmlTransient
    public Collection<BesteldeArtikelen> getBesteldeArtikelenCollection() {
        return besteldeArtikelenCollection;
    }

    public void setBesteldeArtikelenCollection(Collection<BesteldeArtikelen> besteldeArtikelenCollection) {
        this.besteldeArtikelenCollection = besteldeArtikelenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idartikel != null ? idartikel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.idartikel == null && other.idartikel != null) || (this.idartikel != null && !this.idartikel.equals(other.idartikel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.Artikel[ idartikel=" + idartikel + " ]";
    }
    
}
