package my.samples.hierarchy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    private Long id;
    private String title;

}
