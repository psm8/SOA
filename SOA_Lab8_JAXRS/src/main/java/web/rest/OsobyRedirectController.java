package web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("osoby")
public class OsobyRedirectController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirect() throws URISyntaxException {

        URI uri = new URI("user");
        return Response.status(308).location(uri).build();
    }
}
