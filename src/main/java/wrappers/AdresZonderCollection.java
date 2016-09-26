/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrappers;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author jeroen
 */
public class AdresZonderCollection implements Serializable {
       private static final long serialVersionUID = 1L;
    
    private Integer idadres;
    
    private String straat;
    
    private String huisnr;
    
    private String postcode;
    
    private String plaatsnaam;
    
    public Integer getIdadres() {
        return idadres;
    }

    public void setIdadres(Integer idadres) {
        this.idadres = idadres;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnr() {
        return huisnr;
    }

    public void setHuisnr(String huisnr) {
        this.huisnr = huisnr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaatsnaam() {
        return plaatsnaam;
    }

    public void setPlaatsnaam(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.huisnr);
        hash = 37 * hash + Objects.hashCode(this.postcode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdresZonderCollection other = (AdresZonderCollection) obj;
        return true;
    }

}

