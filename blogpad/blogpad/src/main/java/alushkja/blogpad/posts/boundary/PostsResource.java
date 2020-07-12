package alushkja.blogpad.posts.boundary;

import alushkja.blogpad.posts.control.PostStore;
import alushkja.blogpad.posts.entity.Post;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("posts")
public class PostsResource {

    @Inject
    PostStore store;

    @Counted
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Post post) {
        this.store.save(post);
    }

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@PathParam("title") String title) {
        return this.store.read(title);
    }
}
