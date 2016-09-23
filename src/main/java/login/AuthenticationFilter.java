/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import entities.Credentials;
import java.io.IOException;
import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import service.CredentialsDAO;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
  //  @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
  //  private EntityManager em;
   
    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent ;
@Inject
     private  CredentialsDAO cdao ;//= new CredentialsDAO();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = 
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            validateToken(token);
            userAuthenticatedEvent.fire(getEmail(token));

        } catch (Exception e) {
                       
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
            
        }
    }

    private void validateToken(String token) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
         
        String emailToken = getEmail(token);
        Credentials cred = findByEmail(emailToken);
        if (cred != null && cred.getToken().equals(token)) {
             // alles gaat goed doe niets
        }
        else{
            throw new Exception();
        }
            
    }
    public Credentials findByEmail(String email) {
          return cdao.find(email);
    }
    
    public String getEmail(String token){
        String email = "notsetproperly";
        String[] tokenwoorden = token.split(" "); 
        if (tokenwoorden.length > 1) {
            email = tokenwoorden[tokenwoorden.length-1];
        }
        return email;
    }
}