package alushkja.blogpad.posts.control;

import alushkja.blogpad.posts.entity.Post;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Initializer {

    @Inject
    PostStore store;

    @PostConstruct
    public void installFirstPost(){
        var initialPost = this.createInitialPost();
        this.store.createNew(initialPost);
    }

    Post createInitialPost(){
        return new Post("initial", "Welcome to blogpad");
    }
}
