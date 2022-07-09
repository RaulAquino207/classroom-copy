package br.com.raul.classroomcopy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.UserToClassroom;

public interface UserToClassroomRepository extends JpaRepository<UserToClassroom, Long> {

}
