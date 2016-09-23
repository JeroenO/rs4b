/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Adres;
import entities.Klant;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import wrappers.Bewoner;

/**
 *
 * @author jeroen  deze klasse moet nog uitgesplitst worden
 */
@Stateful
@Path("/adres")
public class AdresFacadeREST extends AbstractFacade<Adres> {

    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    //@PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    
    private CredentialsDAO cdao = new CredentialsDAO();

    public AdresFacadeREST() {
        super(Adres.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void saveKlantAdres(Bewoner nieuweInschrijving) {
        Klant nieuweKlant = nieuweInschrijving.extractKlant();
        Adres adres = nieuweInschrijving.extractAdres();
        List<Adres> bestaandeAdressen = findByPostcodeEnHuisnr(adres);
        if (!bestaandeAdressen.isEmpty()) {
            
            adres = bestaandeAdressen.get(0);
         //   nieuweKlant.getAdresCollection().add(adres);
            adres.getKlantCollection().add(nieuweKlant);
            super.edit(adres);

        } else {
          //  nieuweKlant.getAdresCollection().add(adres);
            adres.getKlantCollection().add(nieuweKlant);
            create(adres);
        }
        System.out.println("QWERTY SSSSSSSSSSSSS" + nieuweInschrijving.getPassword());
        cdao.createNewCredentials(nieuweInschrijving.getEmail(), nieuweInschrijving.getPassword() );
    }

    public List<Adres> findByPostcodeEnHuisnr(Adres adres) {
        String qlString = "select a FROM Adres a where a.postcode = :pc and a.huisnr = :hn";
        Query q = em.createQuery(qlString).setParameter("pc", adres.getPostcode()).setParameter("hn", adres.getHuisnr());
        return (List<Adres>) q.getResultList();

    }
    @Override
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Adres create(Adres entity) {
        return super.create(entity);

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Adres entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Adres find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adres> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Adres> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        System.out.println("ook niets hier abstract?   " + em);
        return em;
    }

}
