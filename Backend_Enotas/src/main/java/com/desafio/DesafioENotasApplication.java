package com.desafio;

import com.desafio.dao.NotasEntityDAO;
import com.desafio.entity.NotasEntity;
import com.desafio.resources.ENotasResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class DesafioENotasApplication extends Application<DesafioENotasConfiguration> {

    private final HibernateBundle<DesafioENotasConfiguration> hibernateBundle = new HibernateBundle<DesafioENotasConfiguration>(NotasEntity.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(DesafioENotasConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new DesafioENotasApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DesafioENotasConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(DesafioENotasConfiguration config, Environment environment) {
        final NotasEntityDAO notasEntityDAO = new NotasEntityDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new ENotasResource(notasEntityDAO));


        /*Resolve problema de CORS XMLhttpREquest*/
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    @Override
    public String getName() {
        return "DesafioENotas";
    }
}
