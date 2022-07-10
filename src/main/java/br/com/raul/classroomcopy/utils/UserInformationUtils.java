package br.com.raul.classroomcopy.utils;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.repository.UserRepository;

public class UserInformationUtils {

    public static User getAuthenticatedUserInfo(UserRepository userRepository) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user = userRepository.findByEmail(username);

        return user.get();
    }
}
