package fish.payara.hello;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("ping")
public class HelloWorldResource {

    @GET
    public Response ping() {
        return Response
                .ok("pong")
                .build();
    }
}