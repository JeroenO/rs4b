/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.ejb.Stateless;
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
    String password = "pw";
    String username = "user";
            
    public Inlogger() {
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InlogGegevens getToken(InlogGegevens inlog) {
        if (inlog.getUsername().equals(this.username) && inlog.getPassword().equals(this.password)) {
            return verwijderEnVoegTokenToe(inlog);
        }
        else return valseGegevens(inlog);
    }
    
    private InlogGegevens verwijderEnVoegTokenToe(InlogGegevens inlog) {
        inlog.setPassword("");
        inlog.setToken(this.token);
        return inlog;
    }
    private InlogGegevens valseGegevens(InlogGegevens inlog){
        inlog.setPassword("");
        inlog.setToken("invalidToken");
        return inlog;
    }
}
