/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import entities.Klant;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jeroen
 */
public class Validation {
    
//    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
//    private EntityManager em;
//
//    public boolean validate(String token) {
//        List<Klant> metToken = findByToken(token);
//        String emailToken = getEmail(token);
//        if (!metToken.isEmpty() && emailToken.equals(metToken.get(0).getEmail())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public List<Klant> findByToken(String token) {
//        String qlString = "select a FROM Klant a where a.token = :to ";
//        Query q = em.createQuery(qlString).setParameter("to", token);
//        return (List<Klant>) q.getResultList();
//
//    }
//    public String getEmail(String token){
//        
//        String email = token.split(" ")[-1]; // email is toegevoegd aan eind token
//        return email;
//    }
//    
    
}
