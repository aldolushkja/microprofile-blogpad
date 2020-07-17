package alushkja.blogpad.posts.entity;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Post {

    public String fileName;

    @Size(min = 3, max = 255)
    public String title;

    @Size(min = 3)
    public String content;

    public LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post() {
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateModifiedAt(){
        this.modifiedAt = LocalDateTime.now();
    }
}