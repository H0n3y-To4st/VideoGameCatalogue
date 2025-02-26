package fish.payara.hello.restapi;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/app")
public class RestApplication extends Application {
    // this is for the base URI
}