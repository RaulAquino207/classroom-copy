package br.com.raul.classroomcopy.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.raul.classroomcopy.dto.UserRegistrationDTO;
import br.com.raul.classroomcopy.model.User;

public interface UserService extends UserDetailsService {

    User register(UserRegistrationDTO userRegistrationDTO);
}
