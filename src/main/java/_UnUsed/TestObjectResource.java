package _UnUsed;

import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by roger on 10-1-2016.
 */
@Api("Test")
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestObjectResource {

    public TestObjectResource() {
    }

    @GET
    public Object retrieveAll() {
        return null;
    }

}
