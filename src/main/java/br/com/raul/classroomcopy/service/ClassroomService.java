package br.com.raul.classroomcopy.service;

import java.util.List;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.dto.NoticeBoardDTO;
import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.CommentNoticeBoard;
import br.com.raul.classroomcopy.model.NoticeBoard;

public interface ClassroomService {

    Classroom register(ClassroomRegistrationDTO classroomRegistrationDTO, String emailLoggedUser);

    List<ClassroomDTO> findAll();

    List<ClassroomDTO> findAllByUserId();

    ClassroomDTO findById(Long id);

    String joinClass(Long classroomId);

    void createNoticeBoard(String content, Long classroomId);

    List<NoticeBoardDTO> findNoticeBoardByClassroomId(Long id);

    void createCommentInNoticeBoard(String comment, Long noticeBoardId);
}
