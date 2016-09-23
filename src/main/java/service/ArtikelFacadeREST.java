/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Artikel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import login.Secured;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;


/**
 *
 * @author jeroen
 */
@Stateless
@Path("/artikel")
public class ArtikelFacadeREST extends AbstractFacade<Artikel> {

    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ArtikelFacadeREST() {
        super(Artikel.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Artikel create(Artikel entity) {
        return super.create(entity);
    }
    
    @POST
    @Path("/image")
    
    @Consumes({ MediaType.MULTIPART_FORM_DATA})
    public void saveImage(@FormDataParam("artikelnaam") String naam,   @FormDataParam("upload_file") InputStream upload_file) throws IOException {
        System.out.println(" probeer image te kopieren " );
        String PICLOCATION = "/home/jeroen/NetBeansProjects/ws4/target/ws4-1.0-SNAPSHOT/pics/";
        File targetFile = new File(PICLOCATION + naam + ".jpg");
     //   saveFile(upload_file, file);
        
        java.nio.file.Files.copy(
      upload_file, 
      targetFile.toPath(), 
      StandardCopyOption.REPLACE_EXISTING);
 
    IOUtils.closeQuietly(upload_file);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Artikel entity) {
        super.edit(entity);
    }

    @DELETE
    @Secured
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Artikel find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
  //  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Artikel> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Artikel> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
