package nguyenhuuvu.entity;

import nguyenhuuvu.enums.InboxStatus;

import javax.persistence.*;

@Entity
public class Inbox extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User userCreate;
    @OneToOne
    private User userReply;
    @Enumerated(EnumType.STRING)
    InboxStatus inboxStatus;
}
