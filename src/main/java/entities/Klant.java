/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jeroen
 */
@Entity
@Table(name = "klant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klant.findAll", query = "SELECT k FROM Klant k")})
public class Klant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idklant")
    private Integer idklant;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "voornaam")
    private String voornaam;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "achternaam")
    private String achternaam;
    @Size(max = 5)
    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 1)
    @Column(name = "geslacht")
    private String geslacht;
    @Column(name = "geboortedatum")
    @Temporal(TemporalType.DATE)
    private Date geboortedatum;
    @ManyToMany(mappedBy = "klantCollection")
    private Collection<Adres> adresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantIdklant")
    private Collection<Bestelling> bestellingCollection;

    public Klant() {
    }

    public Klant(Integer idklant) {
        this.idklant = idklant;
    }

    public Klant(Integer idklant, String voornaam, String achternaam, String email) {
        this.idklant = idklant;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }

    public Integer getIdklant() {
        return idklant;
    }

    public void setIdklant(Integer idklant) {
        this.idklant = idklant;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @XmlTransient
    public Collection<Adres> getAdresCollection() {
        return adresCollection;
    }

    public void setAdresCollection(Collection<Adres> adresCollection) {
        this.adresCollection = adresCollection;
    }

    @XmlTransient
    public Collection<Bestelling> getBestellingCollection() {
        return bestellingCollection;
    }

    public void setBestellingCollection(Collection<Bestelling> bestellingCollection) {
        this.bestellingCollection = bestellingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idklant != null ? idklant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klant)) {
            return false;
        }
        Klant other = (Klant) object;
        if ((this.idklant == null && other.idklant != null) || (this.idklant != null && !this.idklant.equals(other.idklant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.Klant[ idklant=" + idklant + " ]";
    }
    
}
