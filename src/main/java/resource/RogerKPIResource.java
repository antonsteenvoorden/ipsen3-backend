package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import model.Kpi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by roger on 30-1-2016.
 */
@Api("RogerKOP")
@Path("/rogerkpi")
@Produces(MediaType.APPLICATION_JSON)
public class RogerKPIResource {
  @GET
  @ApiOperation("Get rogerKPI")
  @JsonView(_View.View.Public.class)
  public Kpi getREKT() {
    Kpi Kpi = new Kpi();
    Kpi.setAantalKlanten(1337);
    return Kpi;
  }
}
