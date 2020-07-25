package alushkja.blogpad.posts.control;

import alushkja.blogpad.posts.entity.Post;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class Initializer {

    static final String TITLE = "initial";

    @Inject
    PostStore store;

    public void installFirstPost(@Observes @Initialized(ApplicationScoped.class) Object doesnMatter) {
        if (this.postExists())
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
