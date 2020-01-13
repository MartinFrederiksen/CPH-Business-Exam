/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.dto.DeliveryDTO;
import facades.DeliveryFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Martin Frederiksen
 */
@Path("Delivery")
public class DeliveryResource {
   private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Exam",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final DeliveryFacade facade =  DeliveryFacade.getDeliveryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"DeliveryFacade\"}";
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeliveryDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeliveryDTO getById(@PathParam("id") long id) throws Exception{
        return facade.getById(id);
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public DeliveryDTO add(DeliveryDTO deliveryDTO) throws Exception {
        return facade.add(deliveryDTO);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public DeliveryDTO delete(@PathParam("id") long id) throws Exception {
        return facade.delete(id);
    }
    
    @POST
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public DeliveryDTO edit(@PathParam("id") long id, DeliveryDTO deliveryDTO) throws Exception {
        return facade.edit(deliveryDTO);
    }
}
