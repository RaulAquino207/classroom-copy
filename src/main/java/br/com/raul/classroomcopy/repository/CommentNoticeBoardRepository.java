package br.com.raul.classroomcopy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.CommentNoticeBoard;

public interface CommentNoticeBoardRepository extends JpaRepository<CommentNoticeBoard, Long> {

    List<CommentNoticeBoard> findAllByNoticeBoardId(Long id);
}
