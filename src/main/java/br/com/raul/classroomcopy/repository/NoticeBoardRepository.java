package br.com.raul.classroomcopy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    List<NoticeBoard> findNoticeBoardByClassroomId(Long id);
}
