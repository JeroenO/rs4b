/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Credentials;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import login.Encryption;

/**
 *
 * @author jeroen
 */

public class CredentialsDAO extends AbstractFacade<Credentials>{
    
    
    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public CredentialsDAO() {
        super(Credentials.class);
    }
    

    public void createNewCredentials(String emailusername, String password) {
        Credentials cred = new Credentials();
        cred.setUsername(emailusername);
        System.out.println("email = "  + emailusername);
        System.out.println("password "  + password);
        cred.setPassword(Encryption.encryptPassword(password));
        create(cred);
        
    }
    @Override
    public Credentials create(Credentials entity) {
        return super.create(entity);

    }
    
    public Credentials find(String emailusername) {
        return super.find(emailusername);
    }

    @Override
    protected EntityManager getEntityManager() {
        System.out.println("niets hier abstract  credentialsdao" + em);
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.me_ws4_war_1.0-SNAPSHOTPU");
    EntityManager ecm = emf.createEntityManager(); 
   // em = ecm;
    System.out.println("niets hier abstract credentialsdao " + ecm);
    return ecm;
    }
}
