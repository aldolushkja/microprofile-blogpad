package alushkja.blogpad.posts.control;

import alushkja.blogpad.posts.entity.Post;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PostStore {

    @Inject
    @ConfigProperty(name = "root.storage.dir")
    String storageDir;

    @Inject
    TitleNormalizer normalizer;

    Path storageDirectoryPath;

    @PostConstruct
    public void init() {
        this.storageDirectoryPath = Path.of(this.storageDir);
    }

    public Post createNew(Post post) {
        var fileName = this.normalizer.normalize(post.title);
        if(this.fileExists(fileName)){
            throw new StorageException("Post with name: " + fileName + " already exists");
        }
        var stringified = serialize(post);
        try {
            post.fileName = fileName;
            write(fileName, stringified);
            return post;
        } catch (IOException ex) {
            throw new StorageException("Cannot save post: " + fileName, ex);
        }
    }

    boolean fileExists(String fileName){
        var fqn = this.storageDirectoryPath.resolve(fileName);
        return Files.exists(fqn);
    }

    public void update(Post post) {
        var fileName = this.normalizer.normalize(post.title);
        var stringified = serialize(post);
        try {
            write(fileName, stringified);
        } catch (IOException ex) {
            throw new StorageException("Cannot save post: " + fileName, ex);
        }
    }


    String serialize(Post post) {
        var jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }

    void write(String fileName, String content) throws IOException {
        var path = this.storageDirectoryPath.resolve(fileName);
        Files.writeString(path, content);
    }

    public Post read(String title) {
        var fileName = this.normalizer.normalize(title);
        try {
            var stringified = this.readString(fileName);
            return deserialize(stringified);
        } catch (IOException ex) {
            throw new StorageException("Cannot fetch post: " + fileName, ex);
        }
    }

    Post deserialize(String stringified) {
        var jsonb = JsonbBuilder.create();
        return jsonb.fromJson(stringified, Post.class);
    }

    String readString(String fileName) throws IOException {
        var path = this.storageDirectoryPath.resolve(fileName);
        return Files.readString(path);
    }
}
