package alushkja.blogpad.posts.entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Post {

    @Schema(readOnly = true)
    public String fileName;

    @Schema(required = true)
    @Size(min = 3, max = 255)
    public String title;

    @Schema(required = true)
    @Size(min = 3)
    public String content;

    @Schema(readOnly = true)
    public LocalDateTime createdAt;

    @Schema(readOnly = true)
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