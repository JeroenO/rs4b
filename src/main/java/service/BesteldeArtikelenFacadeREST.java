/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.BesteldeArtikelen;
import entities.BesteldeArtikelenPK;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author jeroen
 */
@Stateless
@Path("entities.besteldeartikelen")
public class BesteldeArtikelenFacadeREST extends AbstractFacade<BesteldeArtikelen> {

    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private BesteldeArtikelenPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;bestellingIdbestelling=bestellingIdbestellingValue;artikelIdartikel=artikelIdartikelValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.BesteldeArtikelenPK key = new entities.BesteldeArtikelenPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> bestellingIdbestelling = map.get("bestellingIdbestelling");
        if (bestellingIdbestelling != null && !bestellingIdbestelling.isEmpty()) {
            key.setBestellingIdbestelling(new java.lang.Integer(bestellingIdbestelling.get(0)));
        }
        java.util.List<String> artikelIdartikel = map.get("artikelIdartikel");
        if (artikelIdartikel != null && !artikelIdartikel.isEmpty()) {
            key.setArtikelIdartikel(new java.lang.Integer(artikelIdartikel.get(0)));
        }
        return key;
    }

    public BesteldeArtikelenFacadeREST() {
        super(BesteldeArtikelen.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BesteldeArtikelen create(BesteldeArtikelen entity) {
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, BesteldeArtikelen entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.BesteldeArtikelenPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BesteldeArtikelen find(@PathParam("id") PathSegment id) {
        entities.BesteldeArtikelenPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BesteldeArtikelen> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<BesteldeArtikelen> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
