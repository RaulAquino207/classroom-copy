package br.com.raul.classroomcopy.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "redirect:/";
    }
}
