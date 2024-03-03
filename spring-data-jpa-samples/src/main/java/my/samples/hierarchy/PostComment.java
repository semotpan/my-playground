package my.samples.hierarchy;

import jakarta.persistence.*;

@Entity
@Table(name = "post_comment")
public class PostComment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String review;

}
