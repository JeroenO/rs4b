/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrappers;

import entities.Adres;
import entities.Klant;
import java.io.Serializable;
import java.util.Date;

/**
 *helper class om klant en adres tegelijk te kunnen posten met json
 * @author jeroen
 */

public class Bewoner implements Serializable{

    Klant klant = new Klant();
    Adres adres = new Adres();

    private String straat;
    private String huisnr;
    private String postcode;
    private String plaatsnaam;
    private String voornaam;
    private String achternaam;
    private String tussenvoegsel;
    private String email;
    private String geslacht;
    private Date geboortedatum;
    
    private String password;

    

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Klant extractKlant(){
        klant.setAchternaam(achternaam);
        klant.setEmail(email);
        klant.setVoornaam(voornaam);
        
        return klant;
    }
    public Adres extractAdres() {
        
        adres.setHuisnr(huisnr);
        adres.setPlaatsnaam(plaatsnaam);
        adres.setPostcode(postcode);
        adres.setStraat(straat);
        return adres;
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

}
