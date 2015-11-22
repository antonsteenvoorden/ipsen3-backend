package edu.groep6.ipsen3.HelloWorldExample;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by Anton pc on 22-11-2015.
 */
public class TemplateHealthCheck extends HealthCheck{
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }
    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if(!saying.contains("TEST")) {
            return Result.unhealthy("Template heeft geen naam");
        }
        return Result.healthy();
    }
}
