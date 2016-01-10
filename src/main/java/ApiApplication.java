import dao.TestDao;
import dao.WijnDAO2;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.TestObjectResource;
import resource.WijnResource;
import resource.WijnResource2;
import service.TestObjectService;
import service.WijnService;
import service.WijnService2;

/**
 * Created by Anton on 07/01/2016.
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
    }

    @Override
    public void run(ApiConfiguration configuration, Environment environment) {
        name = configuration.getApiName();

        logger.info(String.format("Set API name to %s", name));

        final DBIFactory dbiFactory = new DBIFactory();
        final DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mysql");

//        final DBI jdbi = new DBI(String.format("jdbc:mysql://localhost/test", configuration.getUser(), configuration.getPassword()));

        WijnDAO2 wijnDAO = jdbi.onDemand(WijnDAO2.class);
        WijnService2 wijnService = new WijnService2(wijnDAO);
        WijnResource2 wijnResource = new WijnResource2(wijnService);

        TestDao testDao = new TestDao();
        TestObjectService testObjectService = new TestObjectService(testDao);
        TestObjectResource testObjectResource = new TestObjectResource(testObjectService);

//        setupAuthentication(environment, userDAO);
//        configureClientFilter(environment);

        environment.jersey().register(wijnResource);
        environment.jersey().register(testObjectResource);
    }

//    private void setupAuthentication(Environment environment, UserDAO userDAO) {
//        AuthenticationService authenticationService = new AuthenticationService(userDAO);
//        ApiUnauthorizedHandler unauthorizedHandler = new ApiUnauthorizedHandler();
//
//        environment.jersey().register(new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<User>()
//                        .setAuthenticator(authenticationService)
//                        .setAuthorizer(authenticationService)
//                        .setRealm("SUPER SECRET STUFF")
//                        .setUnauthorizedHandler(unauthorizedHandler)
//                        .buildAuthFilter())
//        );
//
//        environment.jersey().register(RolesAllowedDynamicFeature.class);
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
//    }

//    private void configureClientFilter(Environment environment) {
//        environment.getApplicationContext().addFilter(
//                new FilterHolder(new ClientFilter()),
//                "/*",
//                EnumSet.allOf(DispatcherType.class)
//        );
//    }


}
