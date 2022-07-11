package br.com.raul.classroomcopy.service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.dto.NoticeBoardDTO;
import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.CommentNoticeBoard;
import br.com.raul.classroomcopy.model.NoticeBoard;
import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.model.UserToClassroom;
import br.com.raul.classroomcopy.repository.ClassroomRepository;
import br.com.raul.classroomcopy.repository.CommentNoticeBoardRepository;
import br.com.raul.classroomcopy.repository.NoticeBoardRepository;
import br.com.raul.classroomcopy.repository.UserRepository;
import br.com.raul.classroomcopy.repository.UserToClassroomRepository;
import br.com.raul.classroomcopy.service.ClassroomService;
import br.com.raul.classroomcopy.utils.UserInformationUtils;

@Service
public class ClassroomServiceImpls implements ClassroomService {

    private ClassroomRepository classroomRepository;
    private UserRepository userRepository;
    private UserToClassroomRepository userToClassroomRepository;
    private NoticeBoardRepository noticeBoardRepository;
    private CommentNoticeBoardRepository commentNoticeBoardRepository;
    private ModelMapper mapper;

    public ClassroomServiceImpls(ClassroomRepository classroomRepository, UserRepository userRepository,
            UserToClassroomRepository userToClassroomRepository, NoticeBoardRepository noticeBoardRepository,
            CommentNoticeBoardRepository commentNoticeBoardRepository,
            ModelMapper mapper) {
        super();
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.userToClassroomRepository = userToClassroomRepository;
        this.noticeBoardRepository = noticeBoardRepository;
        this.commentNoticeBoardRepository = commentNoticeBoardRepository;
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

    @Override
    public void createNoticeBoard(String content, Long classroomId) {

        User user = UserInformationUtils.getAuthenticatedUserInfo(userRepository);
        Optional<Classroom> dbClassroom = classroomRepository.findById(classroomId);

        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setContent(content);
        noticeBoard.setClassroom(dbClassroom.get());
        noticeBoard.setUser(user);
        noticeBoard.setAuthor(user.getName());

        noticeBoardRepository.save(noticeBoard);
    }

    @Override
    public List<NoticeBoardDTO> findNoticeBoardByClassroomId(Long id) {

        List<NoticeBoard> noticeBoards = noticeBoardRepository.findNoticeBoardByClassroomId(id);

        List<NoticeBoardDTO> noticeBoardDTOs = new ArrayList<>();
        noticeBoards.forEach(
                noticeBoard -> noticeBoardDTOs.add(mapper.map(noticeBoard, NoticeBoardDTO.class)));

        noticeBoardDTOs.forEach(noticeBoard -> {
            List<CommentNoticeBoard> comments = commentNoticeBoardRepository
                    .findAllByNoticeBoardId(noticeBoard.getId());

            noticeBoard.setComments(comments);
        });

        return noticeBoardDTOs;
    }

    @Override
    public void createCommentInNoticeBoard(String comment, Long noticeBoardId) {

        User user = UserInformationUtils.getAuthenticatedUserInfo(userRepository);
        Optional<NoticeBoard> dbNoticeBoard = noticeBoardRepository.findById(noticeBoardId);

        CommentNoticeBoard commentNoticeBoard = new CommentNoticeBoard();
        commentNoticeBoard.setComment(comment);
        commentNoticeBoard.setUser(user);
        commentNoticeBoard.setNoticeBoard(dbNoticeBoard.get());
        commentNoticeBoard.setAuthor(user.getName());

        commentNoticeBoardRepository.save(commentNoticeBoard);
    }

}
