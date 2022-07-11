package br.com.raul.classroomcopy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_comment_notice_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentNoticeBoard {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ds_comment")
    private String comment;

    @Column(name = "ds_author")
    private String author;

    @ManyToOne
    private NoticeBoard noticeBoard;

    @ManyToOne
    private User user;

}
