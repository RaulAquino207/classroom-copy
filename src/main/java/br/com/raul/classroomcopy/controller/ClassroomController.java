package br.com.raul.classroomcopy.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.dto.CreateCommetInNoticeBoardDTO;
import br.com.raul.classroomcopy.dto.CreateNoticeBoardDTO;
import br.com.raul.classroomcopy.dto.JoinClassDTO;
import br.com.raul.classroomcopy.dto.NoticeBoardDTO;
import br.com.raul.classroomcopy.service.ClassroomService;

@Controller
@RequestMapping("/classroom-copy")
public class ClassroomController {

    private ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        super();
        this.classroomService = classroomService;
    }

    @ModelAttribute("classroom")
    public ClassroomRegistrationDTO classroomRegistrationDTO() {
        return new ClassroomRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "classroom-registration";
    }

    @GetMapping("/classroom")
    public String showClassroomInformations() {
        return "classroom";
    }

    @PostMapping
    public String registerClassroom(@ModelAttribute("classroom") ClassroomRegistrationDTO registrationDto) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        classroomService.register(registrationDto, username);

        return "redirect:/classroom-copy/all-classrooms";
    }

    @GetMapping("/all-classrooms")
    public ModelAndView classroomsByUser() {
        ModelAndView mv = new ModelAndView("index.html");
        List<ClassroomDTO> classroomDTOs = classroomService.findAllByUserId();
        mv.addObject("classrooms", classroomDTOs);
        return mv;
    }

    @GetMapping("/classroom-info/{id}")
    public ModelAndView classroomInfo(
            @PathVariable(required = false, name = "id") String id) {
        ClassroomDTO classroomDTO = classroomService.findById(Long.parseLong(id));
        List<NoticeBoardDTO> noticeBoardDTOs = classroomService.findNoticeBoardByClassroomId(Long.parseLong(id));
        ModelAndView mv = new ModelAndView("classroom-info.html");
        mv.addObject("classroom", classroomDTO);
        mv.addObject("notices", noticeBoardDTOs);
        return mv;
    }

    @PostMapping("/join-class")
    public String joinClass(@ModelAttribute("joinClassDTO") JoinClassDTO joinClassDTO) {
        String status = classroomService.joinClass(joinClassDTO.getId());
        return "redirect:/classroom-copy/all-classrooms?" + status;
    }

    @PostMapping("/create-notice-board/{id}")
    public String createNoticeBoard(@PathVariable(required = false, name = "id") String id,
            @ModelAttribute("createNoticeBoardDTO") CreateNoticeBoardDTO createNoticeBoardDTO) {
        classroomService.createNoticeBoard(createNoticeBoardDTO.getContent(), Long.parseLong(id));
        return "redirect:/classroom-copy/classroom-info/" + id;
    }

    @PostMapping("/create-comment-in-notice-board/{noticeBoardId}/{classroomId}")
    public String createCommentInNoticeBoard(
            @PathVariable(required = false, name = "noticeBoardId") String noticeBoardId,
            @PathVariable(required = false, name = "classroomId") String classroomId,
            @ModelAttribute("createCommetInNoticeBoardDTO") CreateCommetInNoticeBoardDTO createCommetInNoticeBoardDTO) {
        classroomService.createCommentInNoticeBoard(createCommetInNoticeBoardDTO.getComment(),
                Long.parseLong(noticeBoardId));
        return "redirect:/classroom-copy/classroom-info/" + classroomId;
    }
}
