package br.com.raul.classroomcopy.service;

import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.model.Classroom;

public interface ClassroomService {

    Classroom register(ClassroomRegistrationDTO classroomRegistrationDTO, String emailLoggedUser);
}
