package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.dto.DriverDTO;
import entities.dto.TruckDTO;
import errorhandling.ExceptionDTO;
import facades.TruckFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Martin Frederiksen
 */
@Path("Truck")
public class TruckResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/Exam",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final TruckFacade facade =  TruckFacade.getTruckFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Welcome message",
            tags = {"Welcome"})
    public String demo() {
        return "{\"msg\":\"TruckFacade\"}";
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all trucks",
            tags = {"Truck"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = TruckDTO.class)),
                        responseCode = "200", description = "Succesful operation")})
    public List<TruckDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get truck by id",
            tags = {"Truck"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = TruckDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Truck not found")})
    public TruckDTO getById(@PathParam("id") long id) throws Exception{
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.getById(id);
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add truck",
            tags = {"Truck"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = TruckDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "302", description = "Truck nalready exists")})
    //@RolesAllowed("admin")
    public TruckDTO add(TruckDTO truckDTO) throws Exception {
        return facade.add(truckDTO);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete truck",
            tags = {"Truck"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = TruckDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Truck not found")})
    //@RolesAllowed("admin")
    public TruckDTO delete(@PathParam("id") long id) throws Exception {
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.delete(id);
    }
    
    @PUT
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edit truck",
            tags = {"Truck"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = TruckDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Truck not found")})
    //@RolesAllowed("admin")
    public TruckDTO edit(TruckDTO truckDTO) throws Exception {
        return facade.edit(truckDTO);
    }
}
