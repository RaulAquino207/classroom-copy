package br.com.raul.classroomcopy.dto;

import br.com.raul.classroomcopy.model.NoticeBoard;
import br.com.raul.classroomcopy.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNoticeBoardDTO {

    private Long id;

    private String comment;

    private String author;

    private NoticeBoard noticeBoard;

    private User user;
}
