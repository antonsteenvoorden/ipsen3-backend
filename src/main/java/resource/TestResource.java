package resource;

import com.wordnik.swagger.annotations.Api;
import model.Nieuwsbrief;
import service.LionsService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by roger on 21-1-2016.
 */
@Api("Test")
@Path("/test")
//@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

  public TestResource() {
  }

  @GET
//  @Path("/verstuurnieuwsbrief")
//  @Consumes(MediaType.APPLICATION_JSON)
  public void create(Nieuwsbrief nieuwsbrief) {
//    lionsService.send(nieuwsbrief);
  }

}