package br.com.raul.classroomcopy.service.impls;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.model.UserToClassroom;
import br.com.raul.classroomcopy.repository.ClassroomRepository;
import br.com.raul.classroomcopy.repository.UserRepository;
import br.com.raul.classroomcopy.repository.UserToClassroomRepository;
import br.com.raul.classroomcopy.service.ClassroomService;

@Service
public class ClassroomServiceImpls implements ClassroomService {

    private ClassroomRepository classroomRepository;
    private UserRepository userRepository;
    private UserToClassroomRepository userToClassroomRepository;

    public ClassroomServiceImpls(ClassroomRepository classroomRepository, UserRepository userRepository,
            UserToClassroomRepository userToClassroomRepository) {
        super();
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.userToClassroomRepository = userToClassroomRepository;
    }

    @Override
    public Classroom register(ClassroomRegistrationDTO classroomRegistrationDTO, String emailLoggedUser) {

        Optional<User> user = userRepository.findByEmail(emailLoggedUser);

        Classroom classroom = new Classroom();
        classroom.setLabel(classroomRegistrationDTO.getLabel());

        UserToClassroom userToClassroom = new UserToClassroom();
        userToClassroom.setUser(user.get());
        userToClassroom.setRole("TEACHER");
        classroomRepository.save(classroom);

        Optional<Classroom> dbClassroom = classroomRepository.findByLabel(classroomRegistrationDTO.getLabel());
        userToClassroom.setClassroom(dbClassroom.get());
        userToClassroomRepository.save(userToClassroom);

        return classroom;

    }

}
