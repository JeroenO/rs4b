/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jeroen
 */
@Entity
@Table(name = "bestelling")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bestelling.findAll", query = "SELECT b FROM Bestelling b")})
public class Bestelling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbestelling")
    private Integer idbestelling;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "bedrag")
    private BigDecimal bedrag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "besteldatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date besteldatum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bevestigingsnummer")
    private int bevestigingsnummer;
    @JoinColumn(name = "klant_idklant", referencedColumnName = "idklant")
    @ManyToOne(optional = false)
    private Klant klantIdklant;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bestelling")
    private Collection<BesteldeArtikelen> besteldeArtikelenCollection;

    public Bestelling() {
    }

    public Bestelling(Integer idbestelling) {
        this.idbestelling = idbestelling;
    }

    public Bestelling(Integer idbestelling, BigDecimal bedrag, Date besteldatum, int bevestigingsnummer) {
        this.idbestelling = idbestelling;
        this.bedrag = bedrag;
        this.besteldatum = besteldatum;
        this.bevestigingsnummer = bevestigingsnummer;
    }

    public Integer getIdbestelling() {
        return idbestelling;
    }

    public void setIdbestelling(Integer idbestelling) {
        this.idbestelling = idbestelling;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public Date getBesteldatum() {
        return besteldatum;
    }

    public void setBesteldatum(Date besteldatum) {
        this.besteldatum = besteldatum;
    }

    public int getBevestigingsnummer() {
        return bevestigingsnummer;
    }

    public void setBevestigingsnummer(int bevestigingsnummer) {
        this.bevestigingsnummer = bevestigingsnummer;
    }

    public Klant getKlantIdklant() {
        return klantIdklant;
    }

    public void setKlantIdklant(Klant klantIdklant) {
        this.klantIdklant = klantIdklant;
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
        hash += (idbestelling != null ? idbestelling.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestelling)) {
            return false;
        }
        Bestelling other = (Bestelling) object;
        if ((this.idbestelling == null && other.idbestelling != null) || (this.idbestelling != null && !this.idbestelling.equals(other.idbestelling))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.Bestelling[ idbestelling=" + idbestelling + " ]";
    }
    
}
