package airhacks.blogpad.posts.boundary;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts")
public interface PostsResourceClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createNew(JsonObject post);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(JsonObject post);

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPost(@PathParam("title") String title);

}