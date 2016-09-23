/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author jeroen
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // Add your resources.
   //     resources.add(UploadFileService.class);

        // Add additional features such as support for Multipart.
        resources.add(MultiPartFeature.class);
        
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(login.AuthenticationFilter.class);
        resources.add(login.Inlogger.class);
        resources.add(service.AdresFacadeREST.class);
        resources.add(service.ArtikelFacadeREST.class);
        resources.add(service.BesteldeArtikelenFacadeREST.class);
        resources.add(service.BestellingFacadeREST.class);
        resources.add(service.KlantFacadeREST.class);
        resources.add(service.SoortFacadeREST.class);
    //     resources.add(service.CredentialsDAO.class);
       
    }
    
}
