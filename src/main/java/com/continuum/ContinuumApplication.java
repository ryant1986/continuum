package com.continuum;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class ContinuumApplication extends Application<ContinuumConfiguration> {
    public static void main(String[] args) throws Exception {
        new ContinuumApplication().run(args);
    }

    @Override
    public String getName() {
        return "continuum";
    }

    @Override
    public void initialize(Bootstrap<ContinuumConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ContinuumConfiguration configuration,
                    Environment environment) {
        JobInfoResource resource = new JobInfoResource();
        environment.jersey().register(resource);
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter("preflightMaxAge", "5184000"); // 2 months
        filter.setInitParameter("allowCredentials", "true");
    }
}