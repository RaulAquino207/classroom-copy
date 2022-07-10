package br.com.raul.classroomcopy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.model.UserToClassroom;

public interface UserToClassroomRepository extends JpaRepository<UserToClassroom, Long> {

    List<UserToClassroom> findByUserId(Long id);

}
