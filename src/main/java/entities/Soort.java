/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "soort")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Soort.findAll", query = "SELECT s FROM Soort s")})
public class Soort implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsoort")
    private Integer idsoort;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "beschrijving")
    private String beschrijving;
    @ManyToMany(mappedBy = "soortCollection")
    private Collection<Artikel> artikelCollection;

    public Soort() {
    }

    public Soort(Integer idsoort) {
        this.idsoort = idsoort;
    }

    public Soort(Integer idsoort, String beschrijving) {
        this.idsoort = idsoort;
        this.beschrijving = beschrijving;
    }

    public Integer getIdsoort() {
        return idsoort;
    }

    public void setIdsoort(Integer idsoort) {
        this.idsoort = idsoort;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    @XmlTransient
    public Collection<Artikel> getArtikelCollection() {
        return artikelCollection;
    }

    public void setArtikelCollection(Collection<Artikel> artikelCollection) {
        this.artikelCollection = artikelCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsoort != null ? idsoort.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Soort)) {
            return false;
        }
        Soort other = (Soort) object;
        if ((this.idsoort == null && other.idsoort != null) || (this.idsoort != null && !this.idsoort.equals(other.idsoort))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.me.ws4.Soort[ idsoort=" + idsoort + " ]";
    }
    
}
