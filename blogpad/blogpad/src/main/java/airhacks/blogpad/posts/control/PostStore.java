package airhacks.blogpad.posts.control;

import airhacks.blogpad.posts.entity.Post;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class PostStore {

    public String serialize(Post post){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }
}
