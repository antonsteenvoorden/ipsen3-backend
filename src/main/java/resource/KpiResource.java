package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import model.Kpi;
import service.KpiService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by roger on 31-1-2016.
 */
@Api("kpi")
@Path("/kpi")
@Produces(MediaType.APPLICATION_JSON)
public class KpiResource {
  private KpiService kpiService;

  public KpiResource(KpiService kpiService) {
    this.kpiService = kpiService;
  }

  @GET
  @ApiOperation("Get KPI")
  @JsonView(_View.View.Public.class)
  public Kpi getREKT() {
    return kpiService.getKpi();
  }
}
