/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import entities.Klant;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import service.KlantFacadeREST;

@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private Klant authenticatedUser;
    
    @Inject
    private KlantFacadeREST klantFacade;
    
    public AuthenticatedUserProducer(){
        this.authenticatedUser = new Klant();//????
    }

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        System.out.println("handling event authenticateduserproducer");
        this.authenticatedUser = findUser(username);
    }

    private Klant findUser(String username) {
        // Hit the the database or a service to find a user by its username and return it
        // Return the User instance
        return klantFacade.findByEmail(username);
        
    }
}
