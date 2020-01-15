package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.dto.DriverDTO;
import errorhandling.ExceptionDTO;
import facades.DriverFacade;
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
@Path("Driver")
public class DriverResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Exam",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final DriverFacade facade = DriverFacade.getDriverFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Welcome message",
            tags = {"Welcome"})
    public String demo() {
        return "{\"msg\":\"DriverFacade\"}";
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all drivers",
            tags = {"Driver"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DriverDTO.class)),
                        responseCode = "200", description = "Succesful operation")})
    public List<DriverDTO> getAll() {
        return facade.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get driver by id",
            tags = {"Driver"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DriverDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Driver not found")})
    public DriverDTO getById(@PathParam("id") long id) throws Exception {
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.getById(id);
    }

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add driver",
            tags = {"Driver"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DriverDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "302", description = "Driver already exists")})
    //@RolesAllowed("admin")
    public DriverDTO add(DriverDTO driverDTO) throws Exception {
        return facade.add(driverDTO);
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete driver",
            tags = {"Driver"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DriverDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "400", description = "Invalid Id supplied"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Driver not found")})
    //@RolesAllowed("admin")
    public DriverDTO delete(@PathParam("id") long id) throws Exception {
        if (id <= 0) {
            throw new WebApplicationException("Invalid Id supplied", 400);
        }
        return facade.delete(id);
    }

    @PUT
    @Path("edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Edit driver",
            tags = {"Driver"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = DriverDTO.class)),
                        responseCode = "200", description = "Succesful operation"),
                @ApiResponse(content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionDTO.class)),
                        responseCode = "404", description = "Driver not found")})
    //@RolesAllowed("admin")
    public DriverDTO edit(DriverDTO driverDTO) throws Exception {
        return facade.edit(driverDTO);
    }
}
