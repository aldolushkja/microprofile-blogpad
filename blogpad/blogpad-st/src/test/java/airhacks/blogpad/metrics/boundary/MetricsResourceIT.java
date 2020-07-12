package airhacks.blogpad.metrics.boundary;

import airhacks.blogpad.posts.boundary.PostsResourceIT;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author airhacks.com
 */
public class MetricsResourceIT {

    private MetricsResourceClient client;

    @BeforeAll
    public static void initMetricsWithBusinessCall(){
        var test = new PostsResourceIT();
        test.init();
        test.createNew();
    }

    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:9080/");
        this.client = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(MetricsResourceClient.class);

    }

    @Test
    public void metrics() {
        var metrics = this.client.metrics();
        assertNotNull(metrics);
        assertFalse(metrics.isEmpty());
    }

    @Test
    public void applicationMetrics() {
        var metrics = this.client.applicationMetrics();
        assertNotNull(metrics);
        assertFalse(metrics.isEmpty());
        System.out.println("metrics from server : " + metrics);
        int saveInvocationCounter = metrics
                .getJsonNumber("alushkja.blogpad.posts.boundary.PostsResource.createNew").intValue();

        assertTrue(saveInvocationCounter >= 0);
    }

}