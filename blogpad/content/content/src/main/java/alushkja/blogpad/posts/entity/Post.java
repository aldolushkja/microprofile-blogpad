package alushkja.blogpad.posts.entity;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Post {

    @Schema(readOnly = true)
    public String fileName;

    @Schema(required = true, example = "openapi intro")
    @Size(min = 3, max = 255, message = "Titolo deve essere compreso tra min 3 caratteri e max 255 caratteri")
    public String title;

    @Schema(required = true, example = "how to use...")
    @Size(min = 3)
    public String content;

    @Schema(readOnly = true, type = SchemaType.STRING, format = "date-time")
    public LocalDateTime createdAt;

    @Schema(readOnly = true, type = SchemaType.STRING, format = "date-time")
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

    public void updateModifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }
}