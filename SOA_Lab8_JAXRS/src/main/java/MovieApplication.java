import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api")
public class MovieApplication extends Application {
    public MovieApplication(@Context ServletConfig servletConfig) {
        super();

        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Movie App API");
        beanConfig.setBasePath("/SOA_Lab8_JAXRS/api");
        beanConfig.setResourcePackage("web.rest");
        beanConfig.setScan(true);
    }
}
