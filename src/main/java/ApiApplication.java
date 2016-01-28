import dao.*;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import model.Klant;
import org.eclipse.jetty.servlet.FilterHolder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.*;
import service.*;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Edited by:
 * - Anton
 * - Roger
 * Main class, voegt alle resources en services toe.
 */
public class ApiApplication extends Application<ApiConfiguration> {
  private final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

  private String name;

  /**
   * Entry point.
   *
   * @param args from command line
   * @throws Exception if the server cannot start
   */
  public static void main(String[] args) throws Exception {
    new ApiApplication().run(args);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
    bootstrap.addBundle((ConfiguredBundle) new ConfiguredAssetsBundle("/assets/", "/client", "index.html"));

    bootstrap.addBundle(new SwaggerBundle<ApiConfiguration>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApiConfiguration configuration) {
        return configuration.swaggerBundleConfiguration;
      }
    });
  }

  /**
   * Begin van de API. Maakt gebruik van de in de run-parameter meegegeven .YML bestand.
   * @param configuration
   * @param environment
     */
  @Override
  public void run(ApiConfiguration configuration, Environment environment) {
    name = configuration.getApiName();
    logger.info(String.format("Set API name to %s", name));

    final DBIFactory dbiFactory = new DBIFactory();
    final DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mysql");

    WijnDAO wijnDao = jdbi.onDemand(WijnDAO.class);
    WijnService wijnService = new WijnService(wijnDao);
    WijnResource wijnResource = new WijnResource(wijnService);

    InschrijvingDAO inschrijvingDAO = jdbi.onDemand(InschrijvingDAO.class);

    ActieDAO actieDAO = jdbi.onDemand(ActieDAO.class);
    ActieService actieService = new ActieService(actieDAO, inschrijvingDAO);
    ActieResource actieResource = new ActieResource(actieService);

    OrderRegelDAO orderRegelDAO = jdbi.onDemand(OrderRegelDAO.class);
    OrderRegelService orderRegelService = new OrderRegelService(orderRegelDAO, wijnService);

    OrderDAO orderDAO = jdbi.onDemand(OrderDAO.class);
    OrderService orderService = new OrderService(orderDAO, orderRegelService, actieService);
    OrderResource orderResource = new OrderResource(orderService);

    KlantDAO klantDAO = jdbi.onDemand(KlantDAO.class);
    KlantService klantService = new KlantService(klantDAO, orderService);

    LionsService lionsService = new LionsService(configuration.getMailUser(), configuration.getMailPassword(), klantDAO);
    LionsResource lionsResource = new LionsResource(lionsService);
    KlantResource klantResource = new KlantResource(klantService,lionsService);

    setupAuthentication(environment, klantDAO);
    configureClientFilter(environment);
    //register resources
    environment.jersey().register(klantResource);
    environment.jersey().register(wijnResource);
    environment.jersey().register(actieResource);
    environment.jersey().register(lionsResource);
    environment.jersey().register(orderResource);
  }

  /**
   * Voegt hier de authenticatie toe aan de API, ontvangt de KlantDAO om klanten op te halen uit de DB zodra iemand
   * zich authentiseert. Dit is voor de vergelijking van de opgevraagde en de meegegeven klant.
   * @param environment
   * @param klantDAO
     */
  private void setupAuthentication(Environment environment, KlantDAO klantDAO) {
    AuthenticationService authenticationService = new AuthenticationService(klantDAO);
    ApiUnauthorizedHandler unauthorizedHandler = new ApiUnauthorizedHandler();

    environment.jersey().register(new AuthDynamicFeature(
                    new BasicCredentialAuthFilter.Builder<Klant>()
                            .setAuthenticator(authenticationService)
                            .setAuthorizer(authenticationService)
                            .setRealm("Niet zichtbaar voor allen")
                            .setUnauthorizedHandler(unauthorizedHandler)
                            .buildAuthFilter())
    );

    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Klant.class));
  }

  /**
   * Zorgt ervoor dat er geen onzin requests worden geplaatst op de API,
   * Geeft een mooie HTTP foutmelding.
   * @param environment
     */
  private void configureClientFilter(Environment environment) {
    environment.getApplicationContext().addFilter(
            new FilterHolder(new ClientFilter()),
            "/*",
            EnumSet.allOf(DispatcherType.class)
    );
  }


}
