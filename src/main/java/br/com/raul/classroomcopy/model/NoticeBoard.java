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
@Table(name = "tb_notice_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoticeBoard {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ds_content")
    private String content;

    @Column(name = "ds_author")
    private String author;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private User user;

}
