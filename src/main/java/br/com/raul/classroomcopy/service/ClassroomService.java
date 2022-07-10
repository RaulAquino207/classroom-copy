package br.com.raul.classroomcopy.service;

import java.util.List;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.model.Classroom;

public interface ClassroomService {

    Classroom register(ClassroomRegistrationDTO classroomRegistrationDTO, String emailLoggedUser);

    List<ClassroomDTO> findAll();

    List<ClassroomDTO> findAllByUserId();
}
