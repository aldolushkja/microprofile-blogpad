package alushkja.blogpad.posts.boundary;

import alushkja.blogpad.posts.control.PostStore;
import alushkja.blogpad.posts.entity.Post;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("posts")
public class PostsResource {

    @Inject
    PostStore store;

    @Counted
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNew(@Context UriInfo uriInfo, Post post) {
        Post postWithFilename = this.store.createNew(post);
        URI uri = uriInfo.getAbsolutePathBuilder().path(postWithFilename.fileName).build();
        return Response.created(uri).build();
    }

    @Counted
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo uriInfo, Post post) {
        this.store.update(post);
        return Response.ok().build();
    }

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@PathParam("title") String title) {
        return this.store.read(title);
    }
}
