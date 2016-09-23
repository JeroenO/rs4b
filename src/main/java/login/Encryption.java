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
import org.abstractj.kalium.NaCl;
import org.abstractj.kalium.crypto.Password;
import org.abstractj.kalium.encoders.Encoder;
import org.abstractj.kalium.encoders.Hex;

/**
 *
 * @author jeroen
 */
public class Encryption {
    static Encoder hex = new Hex();
    static Password pw = new Password();
    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private static EntityManager em;
    
    public static void savePassword(String password, String usernameemail) {
        
        String encrypted = encryptPassword(password);
        
        
        String qlString = "insert into klant ('password') value (':pw')";
        Query q = em.createQuery(qlString).setParameter("pw", encrypted);
        q.executeUpdate();

    
        
    }
    
    
    public static String encryptPassword( String password) {
       System.out.println("password to encrypt =   "+ password);  
        long startTime = System.nanoTime();
        String encrypted = pw.hash(password.getBytes(), hex ,NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE, NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("encryption took ms  "+ duration/1000000);
        
        return encrypted;
        
       
       
    }
    
    public static boolean verifyPassword(String password, String inDB) {
        byte[] hashedPassword = hex.decode(inDB);
        
        
        return pw.verify(hashedPassword, password.getBytes());
        
        
    }
    public static String toHexString(byte[] bites) {
        
        return hex.encode(bites);
    }
    
    
    
}
