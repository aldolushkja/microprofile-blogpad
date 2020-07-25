package airhacks.blogpad.health.boundary;

import airhacks.blogpad.ServerConfig;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthResourceIT {


    HealthResourceClient client;

    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:" + ServerConfig.SERVER_PORT + "/");
        this.client = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(HealthResourceClient.class);

    }

    @Test
    public void liveness() {
        Response response = this.client.liveness();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void readiness() {
        Response response = this.client.readiness();
        assertEquals(200, response.getStatus());
    }


}
