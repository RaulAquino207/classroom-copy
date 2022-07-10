package br.com.raul.classroomcopy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
