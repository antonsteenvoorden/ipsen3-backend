import dao.TestDao;
import dao.WijnDAO;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlet.FilterHolder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.TestObjectResource;
import resource.WijnResource;
import service.TestObjectService;
import service.WijnService;

/**
 * Created by Anton on 07/01/2016.
 */
public class ApiApplication extends Application<ApiConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    private String name;

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

        WijnDAO wijnDAO = new WijnDAO();
        WijnService wijnService = new WijnService(wijnDAO);
        WijnResource wijnResource = new WijnResource(wijnService);

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

    public static void main(String[] args) throws Exception {
        new ApiApplication().run(args);
    }
}
