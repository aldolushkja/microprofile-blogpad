package airhacks.blogpad.posts.boundary;

import airhacks.blogpad.metrics.boundary.MetricsResourceClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PostsResourceTortureIT {
    private PostsResourceClient client;
    private String title;
    private ExecutorService threadPool;

    private MetricsResourceClient metricsClient;


    @BeforeEach
    public void init() {
        URI uri = URI.create("http://localhost:8282/blogpad/resources/");
        this.client = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(PostsResourceClient.class);

        this.title = "torture" + System.currentTimeMillis();
        JsonObject post = Json.createObjectBuilder()
                .add("title", title)
                .add("content", "for torture")
                .build();
        Response response = this.client.createNew(post);
        int status = response.getStatus();
        assertEquals(201, status);

        this.threadPool = Executors.newFixedThreadPool(20);
        this.initMetricsEndpoint();

    }

    void initMetricsEndpoint() {
        URI uri = URI.create("http://localhost:8282/");
        this.metricsClient = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(MetricsResourceClient.class);
    }


    @Test
    public void startTorture() {
        List<CompletableFuture<Void>> tasks = Stream.
                generate(this::runScenario).
                limit(500).
                collect(Collectors.toList());
        tasks.forEach(CompletableFuture::join);
        verifyPerformance();
    }

    CompletableFuture<Void> runScenario() {
        return CompletableFuture.runAsync(this::findPost, this.threadPool).
                thenRunAsync(this::findNonExistingPost, this.threadPool);
    }

    void findNonExistingPost() {
        Response response = null;
        try {
            response = this.client.findPost("not-existing" + System.nanoTime());
            fail("Shold not exists");
        } catch (WebApplicationException e) {
        }
    }

    public void findPost() {
        Response response = this.client.findPost(this.title);
        JsonObject post = response.readEntity(JsonObject.class);
        assertNotNull(post);
    }

    void verifyPerformance() {
        JsonObject findOperationResult = this.metricsClient.applicationMetrics().getJsonObject("alushkja.blogpad.posts.boundary.PostsResource.findPost");
        double oneMinRate = findOperationResult.getJsonNumber("oneMinRate").doubleValue();
        System.out.println("--------- oneMinRate " + oneMinRate);
        assertTrue(oneMinRate > 5);
    }


}
