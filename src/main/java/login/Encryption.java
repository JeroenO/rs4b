/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.security.SecureRandom;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.abstractj.kalium.NaCl;
import org.abstractj.kalium.crypto.Password;
import org.abstractj.kalium.encoders.Encoder;
import org.abstractj.kalium.encoders.Hex;
import org.apache.commons.lang.ArrayUtils;

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
       byte[] salt = getSalt();
       byte[] saltedPassword = (byte[])ArrayUtils.addAll(salt, password.getBytes());
        long startTime = System.nanoTime();
        String encrypted = hex.encode(salt) + pw.hash(saltedPassword, hex , NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE, NaCl.Sodium.CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("encryption took ms  "+ duration/1000000);
        
        return encrypted;
        
       
       
    }
    
    public static boolean verifyPassword(String password, String inDB) {
        String ongezouten = inDB.substring(32);
        String zout = inDB.substring(0, 32);
        byte[] hashedPassword = hex.decode(ongezouten);
        
        //eerste deel dbhash is oorspronkelijk salt dus vergelijk 2e deel met getyptePW + het zout
        // om mij nog  onbekende reden doet pw.verify dit niet goed zelf
        byte[] vleugje = hex.decode(zout);
        byte[] ingevoerd = (byte[])ArrayUtils.addAll(vleugje, password.getBytes());
        return pw.verify( hashedPassword , ingevoerd);
        
        
    }
    public static String toHexString(byte[] bites) {
        
        return hex.encode(bites);
    }
    public static byte[] getSalt() {
        Random random = new SecureRandom();
        byte[] salt =  new byte[16];
        random.nextBytes(salt);
       
        return salt;
    }
    
    
}
