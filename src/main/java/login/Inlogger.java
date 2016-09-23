/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import entities.Credentials;
import entities.Klant;
import java.security.SecureRandom;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jeroen
 */
@Stateless
@Path("/login")
public class Inlogger {

    String token = "12345";
    Credentials cred;
//    String password = "pw";
//    String username = "user";
//     String savedEncrypt = "";   
    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Inlogger() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InlogGegevens getToken(InlogGegevens inlog) {
        if (verifyPW(inlog.getUsername(), inlog.getPassword())) {
            return verwijderEnVoegTokenToe(inlog);
        } else {

            return valseGegevens(inlog);

        }
    }

    private InlogGegevens verwijderEnVoegTokenToe(InlogGegevens inlog) {
        inlog.setPassword("");
        createToken(inlog.getUsername());
        inlog.setToken(this.token);
        cred.setToken(token);
        em.merge(cred); // save token in db

        return inlog;
    }

    private InlogGegevens valseGegevens(InlogGegevens inlog) {
        inlog.setPassword("");
        inlog.setToken("invalidCredentials");
        return inlog;
    }

    public String createToken(String email) {

        byte[] randomBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomBytes);
        String basisString = Encryption.toHexString(randomBytes);
        String tokenString = basisString + " " + email;

        this.token = tokenString;
        return tokenString;
    }

    public boolean verifyPW(String usernameisemail, String password) {

        Query q = em.createNamedQuery("Credentials.findByUsername").setParameter("username", usernameisemail);
        List<Credentials> credens = (List<Credentials>) q.getResultList();
        if (!credens.isEmpty()) {
            cred = credens.get(0);

            return Encryption.verifyPassword(password, cred.getPassword());
        } else {
            return false;
        }

    }

}
