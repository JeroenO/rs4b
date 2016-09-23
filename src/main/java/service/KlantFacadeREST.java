/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Artikel;
import entities.BesteldeArtikelen;
import entities.Bestelling;
import entities.Klant;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;


import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import login.AuthenticatedUser;
import login.Secured;

/**
 *
 * @author jeroen
 */
@Stateless
@Path("/Klant")
public class KlantFacadeREST extends AbstractFacade<Klant> {

    @PersistenceContext(unitName = "com.me_ws4_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Inject
    @AuthenticatedUser
    private Klant ingelogdeKlant;

    public KlantFacadeREST() {
        super(Klant.class);
    }
    
    @Secured
    @POST
    @Path("/Buy")
    @Consumes({ MediaType.APPLICATION_JSON})
    public void buy(String inkomend) {
        Type type = new TypeToken<Map<Integer, Integer>>(){}.getType();
Map<Integer, Integer> myMap =  new Gson().fromJson(inkomend, type);
        System.out.println(" komt aan "  + myMap);
       
        Bestelling bestelling = new Bestelling();
        Klant bestaandeKlant = findByEmail(ingelogdeKlant.getEmail()); //stom maar anders nullpointer wanneer em.flush hieronder
          System.out.println(" klant is  "  + bestaandeKlant.getEmail());
          System.out.println(" inglegodeklant is  "  + ingelogdeKlant.getEmail());
//        myMap.keySet().forEach((key) -> {
//            System.out.println(myMap.get(key));
//            bestelling.getBesteldeArtikelenCollection().add(maakKlaar(bestelling, key, myMap.get(key)));
//            
//        }); 

        bestelling.setKlantIdklant(bestaandeKlant);
       // bestelling = bestel(bestelling);
       em.persist(bestelling);
        em.flush();
        Collection<BesteldeArtikelen> col = bestelling.getBesteldeArtikelenCollection();
        myMap.keySet().forEach((key) -> {
            System.out.println(myMap.get(key));
            col.add(maakKlaar1( key, myMap.get(key)));
            
        }); 
         System.out.println(" komt uit nu al bestelling "  + bestelling.getIdbestelling());
         System.out.println(" aantal in bestelling "  + col.size());
        for (BesteldeArtikelen best: col) {
            best.setBestelling(bestelling);
        System.out.println("  bestellingid " +    bestelling.getBesteldeArtikelenCollection().iterator().next().getBestelling().getIdbestelling());
        }
         em.merge(bestelling);

         System.out.println(" komt uit bestelling "  + bestelling.getIdbestelling());
        
    }
    private long  generateBestellingId(Klant bestaandeKlant) {
        String alsString =  "" + new Date().getTime()/60000 + bestaandeKlant.getIdklant();
        return  Long.parseLong(alsString, 10);
    }
    private Bestelling bestel(Bestelling best){
         em.persist(best);
         return best;
    }
    private BesteldeArtikelen maakKlaar1(Integer artikelId, Integer aantal) {
        BesteldeArtikelen deelBesteld = new BesteldeArtikelen();
        System.out.println(" artikel zoeken " + artikelId);
        Artikel artikel = em.find(Artikel.class, artikelId);
        System.out.println(" artikel gevonden:  " + artikel.getNaam());
        deelBesteld.setAantal(aantal);
        deelBesteld.setArtikel(artikel);
        
        
        return deelBesteld;        
    }
    
    
    public Klant findByEmail(String email) {
        String qlString = "select a FROM Klant a where a.email = :em";
        List klantlist = em.createQuery(qlString).setParameter("em", email).getResultList();
        if (!klantlist.isEmpty()) {
            return (Klant)klantlist.get(0);
        }
        else return new Klant();

    }
    
    

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Klant create(Klant entity) {
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Klant entity) {
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
    public Klant find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Klant> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Klant> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
