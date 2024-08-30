package fish.payara.hello;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/*
   The use of this class is to test the connection between the client and the server
 */
@Path("ping")
public class HelloWorldResource {

    @GET
    public Response ping() {
        return Response
                .ok("pong")
                .build();
    }
}