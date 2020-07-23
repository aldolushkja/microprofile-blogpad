package alushkja.blogpad.posts.control;

import alushkja.blogpad.posts.entity.Post;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@Singleton
@Startup
public class Initializer {

    static final String TITLE = "initial";

    @Inject
    PostStore store;

    @PostConstruct
    public void installFirstPost() {
        if(this.postExists())
            return;
        var initialPost = this.createInitialPost();
        this.store.createNew(initialPost);
    }

    Post createInitialPost() {
        return new Post(TITLE, "Welcome to blogpad");
    }

    Post fetchPost() {
        return this.store.read(TITLE);
    }

    boolean postExists() {
        var post = this.fetchPost();
        if (post == null)
            return false;
        return TITLE.equalsIgnoreCase(post.title);
    }

    @Produces
    @Liveness
    public HealthCheck initialExists() {
        return () -> HealthCheckResponse.named("initial-post-exists").state(this.postExists()).build();
    }
}
