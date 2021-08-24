package nguyenhuuvu.entity;

import javax.persistence.*;

@Entity
public class Message extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @OneToOne
    private User userSend;
}
