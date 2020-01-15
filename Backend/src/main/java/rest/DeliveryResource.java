package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.dto.DeliveryDTO;
import errorhandling.ExceptionDTO;
import facades.DeliveryFacade;
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
    @Operation(summary = "Welcome message",
            tags = {"Welcome"})
    public String demo() {
        return "{\"msg\":\"DeliveryFacade\"}";
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all deliveries",
            tags = {"Delivery"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DeliveryDTO.class)),
                        responseCode = "200", description = "Succesful operation")})
    public List<DeliveryDTO> getAll() {
        return facade.getAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get delivery by id",
            tags = {"Delivery"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DeliveryDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Delivery not found")})
    public DeliveryDTO getById(@PathParam("id") long id) throws Exception{
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.getById(id);
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add delivery",
            tags = {"Delivery"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DeliveryDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "302", description = "Delivery already exists")})
    //@RolesAllowed("admin")
    public DeliveryDTO add(DeliveryDTO deliveryDTO) throws Exception {
        return facade.add(deliveryDTO);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete delivery",
            tags = {"Delivery"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DeliveryDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Delivery not found")})
    //@RolesAllowed("admin")
    public DeliveryDTO delete(@PathParam("id") long id) throws Exception {
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.delete(id);
    }
    
    @PUT
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edit delivery",
            tags = {"Delivery"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DeliveryDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Delivery not found")})
    //@RolesAllowed("admin")
    public DeliveryDTO edit(DeliveryDTO deliveryDTO) throws Exception {
        return facade.edit(deliveryDTO);
    }
}
