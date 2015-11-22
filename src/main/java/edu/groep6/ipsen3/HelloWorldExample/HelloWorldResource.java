package edu.groep6.ipsen3.HelloWorldExample;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Anton pc on 22-11-2015.
 */

//Om te zorgen dat die reageert op URI /hello-world
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        //safe way to create unique ID's
        this.counter = new AtomicLong();
    }

    @GET
    // zorgt dat dropwizard de tijd en duratie opslaat als metrics timer w/e that is
    @Timed
    //@QueryParam("name") zorgt ervoor dat als je in je link /hello-world?name= linkt aan dit ding
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name);
        return new Saying(counter.incrementAndGet(), value);
    }
}