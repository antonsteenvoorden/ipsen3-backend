package resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import io.dropwizard.auth.Auth;
import model.Klant;
import model.Kpi;
import service.KpiService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Edited by:
 * -Jordan
 * -Sidney
 * -Dennis
 * -Roger
 */
@Api("kpi")
@Path("/kpi")
@Produces(MediaType.APPLICATION_JSON)
public class KpiResource {
  private KpiService kpiService;

  public KpiResource(KpiService kpiService) {
    this.kpiService = kpiService;
  }

  /**
   * Haalt het kpi object op
   * @param authorisation
   * @return
   */
  @GET
  @ApiOperation("Get KPI")
  @JsonView(_View.View.Public.class)
  public Kpi get(@Auth Klant authorisation) {
    return kpiService.getKpi(authorisation);
  }
}
