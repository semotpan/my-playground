package my.samples.hierarchy;

import jakarta.persistence.*;

@Entity
@Table(name = "user_vote")
public class UserVote {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostComment comment;

    private int score;

}
