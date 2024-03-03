package my.samples.hierarchy;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_details")
public class PostDetails {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Post post;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

}
