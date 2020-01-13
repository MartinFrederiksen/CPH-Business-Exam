package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.conf.PropertyKey;
import entities.Cargo;
import entities.dto.CargoDTO;
import facades.CargoFacade;
import java.util.List;
import javax.persistence.EntityManager;
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
@Path("Cargo")
public class CargoResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Exam",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final CargoFacade facade = CargoFacade.getCargoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"CargoFacade\"}";
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CargoDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CargoDTO getById(@PathParam("id") long id) throws Exception{
        return facade.getById(id);
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public CargoDTO add(CargoDTO cargoDTO) throws Exception {
        return facade.add(cargoDTO);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public CargoDTO delete(@PathParam("id") long id) throws Exception {
        return facade.delete(id);
    }
    
    @POST
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("admin")
    public CargoDTO edit(CargoDTO cargoDTO) throws Exception {
        return facade.edit(cargoDTO);
    }
}
