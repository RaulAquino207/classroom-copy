package br.com.raul.classroomcopy.dto;

import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoardDTO {

    private Long id;

    private String content;

    private String author;

    private Classroom classroom;

    private User user;
}
