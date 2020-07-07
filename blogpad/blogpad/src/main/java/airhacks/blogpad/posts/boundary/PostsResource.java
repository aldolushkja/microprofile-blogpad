package airhacks.blogpad.posts.boundary;

import airhacks.blogpad.posts.control.PostStore;
import airhacks.blogpad.posts.entity.Post;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("posts")
public class PostsResource {

    @Inject
    PostStore store;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Post post) {
        this.store.save(post);
    }
}
