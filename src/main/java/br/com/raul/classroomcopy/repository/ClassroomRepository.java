package br.com.raul.classroomcopy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.raul.classroomcopy.model.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByLabel(String label);

}
