package resource;

import exception.ResponseException;
import model.Wijn;
import service.WijnService2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/*********************************************
 * Created by roger and KAASANTON on 10-1-2016.
 * ik ben ook een comment
 *********************************************/
@Path("/wijnen")
@Produces(MediaType.APPLICATION_JSON)
public class WijnResource2 {

    private final WijnService2 wijnService;

    public WijnResource2(WijnService2 wijnService) {
        this.wijnService = wijnService;
    }

    @GET
    public Set<Wijn> retrieveAll() {
        Set<Wijn> wijnSet = wijnService.retrieveAll();
        return wijnSet;
    }

    @GET
    @Path("/{id}")
    public Wijn retrieve(@PathParam("id") int id) {
        Wijn bestaandeWijn = wijnService.retrieve(id);
        if (bestaandeWijn == null) {
            ResponseException.formatAndThrow(Response.Status.NOT_FOUND, "Content with id " + id + " does not exist");
        }
        return bestaandeWijn;
    }
}
