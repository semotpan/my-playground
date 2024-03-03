package my.samples.hierarchy;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "post_tag")
public class PostTag {

    @EmbeddedId
    private PostTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    private Tag tag;

    @Column(name = "created_on")
    private Date createdOn = new Date();


    @Embeddable
    record PostTagId(UUID id) implements Serializable {}
}
