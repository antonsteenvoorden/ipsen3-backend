package resource;

import model.TestObject;
import model.Wijn;
import service.TestObjectService;
import service.WijnService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by roger on 10-1-2016.
 */
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestObjectResource {

    private final TestObjectService service;

    public TestObjectResource(TestObjectService service) {
        this.service = service;
    }

    @GET
    public Collection<TestObject> retrieveAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public TestObject retrieve(@PathParam("id") int id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@PathParam("id") int id, TestObject testObject)
    {
        service.add(id, testObject);
    }
}
