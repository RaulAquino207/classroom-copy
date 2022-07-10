package br.com.raul.classroomcopy.service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.model.UserToClassroom;
import br.com.raul.classroomcopy.repository.ClassroomRepository;
import br.com.raul.classroomcopy.repository.UserRepository;
import br.com.raul.classroomcopy.repository.UserToClassroomRepository;
import br.com.raul.classroomcopy.service.ClassroomService;
import br.com.raul.classroomcopy.utils.UserInformationUtils;

@Service
public class ClassroomServiceImpls implements ClassroomService {

    private ClassroomRepository classroomRepository;
    private UserRepository userRepository;
    private UserToClassroomRepository userToClassroomRepository;
    private ModelMapper mapper;

    public ClassroomServiceImpls(ClassroomRepository classroomRepository, UserRepository userRepository,
            UserToClassroomRepository userToClassroomRepository, ModelMapper mapper) {
        super();
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.userToClassroomRepository = userToClassroomRepository;
        this.mapper = mapper;
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

    @Override
    public List<ClassroomDTO> findAll() {
        List<Classroom> dbClassrooms = classroomRepository.findAll();

        List<ClassroomDTO> classroomDTOs = new ArrayList<>();

        dbClassrooms.forEach(classroom -> classroomDTOs.add(mapper.map(classroom, ClassroomDTO.class)));
        return classroomDTOs;
    }

    @Override
    public List<ClassroomDTO> findAllByUserId() {

        User user = UserInformationUtils.getAuthenticatedUserInfo(userRepository);

        List<UserToClassroom> userToClassrooms = userToClassroomRepository.findByUserId(user.getId());

        List<ClassroomDTO> classroomDTOs = new ArrayList<>();
        userToClassrooms.forEach(
                userToClassroom -> classroomDTOs.add(mapper.map(userToClassroom.getClassroom(), ClassroomDTO.class)));

        return classroomDTOs;
    }

    @Override
    public ClassroomDTO findById(Long id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO = mapper.map(classroom.get(), ClassroomDTO.class);
        return classroomDTO;
    }

    @Override
    public String joinClass(Long classroomId) {

        User user = UserInformationUtils.getAuthenticatedUserInfo(userRepository);
        Optional<Classroom> dbClassroom = classroomRepository.findById(classroomId);

        UserToClassroom userToClassroom = new UserToClassroom();

        if (dbClassroom.isPresent()) {
            userToClassroom.setUser(user);
            userToClassroom.setRole("STUDENT");
            userToClassroom.setClassroom(dbClassroom.get());
            userToClassroomRepository.save(userToClassroom);

            return "success";
        } else {
            return "error";
        }

    }

}
