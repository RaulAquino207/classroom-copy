package br.com.raul.classroomcopy.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.raul.classroomcopy.dto.ClassroomDTO;
import br.com.raul.classroomcopy.dto.ClassroomRegistrationDTO;
import br.com.raul.classroomcopy.service.ClassroomService;

@Controller
@RequestMapping("/classroom-registration")
public class ClassroomRegistrationController {

    private ClassroomService classroomService;

    public ClassroomRegistrationController(ClassroomService classroomService) {
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

        return "redirect:/classroom-registration/all-classrooms";
    }

    @GetMapping("/all-classrooms")
    public ModelAndView classroomsByUser() {
        ModelAndView mv = new ModelAndView("index.html");
        List<ClassroomDTO> classroomDTOs = classroomService.findAllByUserId();
        mv.addObject("classrooms", classroomDTOs);
        return mv;
    }

    @GetMapping("/classroom-info")
    public ModelAndView classroomInfo() {
        ModelAndView mv = new ModelAndView("index.html");
        List<ClassroomDTO> classroomDTOs = classroomService.findAll();
        mv.addObject("classrooms", classroomDTOs);
        return mv;
    }
}
