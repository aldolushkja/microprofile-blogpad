package airhacks.service.ping.boundary;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts")
public interface PostsResourceClient {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response save(JsonObject post);

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPost(@PathParam("title") String title);

}