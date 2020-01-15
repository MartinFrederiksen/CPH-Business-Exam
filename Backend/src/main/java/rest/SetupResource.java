package rest;

import io.swagger.v3.oas.annotations.Operation;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.SetupDummyData;
import utils.SetupTestUsers;

/**
 * REST Web Service
 *
 * @author Martin Frederiksen
 */
@Path("setup")
public class SetupResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Setup dummy data and users",
            tags = {"Setup"})
    public String demo() {
        new SetupDummyData().setup();
        new SetupTestUsers().setup();
        return "{\"msg\":\"complete\"}";
    }
}
