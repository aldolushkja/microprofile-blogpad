package blogpad.reactor.posts.boundary;

import blogpad.reactor.posts.control.PostsResourceClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("posts")
public class PostsResource {

    @Inject
    @RestClient
    PostsResourceClient client;

    @GET
    @Path("{title}")
    @Produces(MediaType.TEXT_HTML)
    public String findPost(@PathParam("title") String title) {
        var response = this.client.findPost("initial");
        return "<h1>hello<h1>" + title + response.readEntity(JsonObject.class);
    }

    ;

}
