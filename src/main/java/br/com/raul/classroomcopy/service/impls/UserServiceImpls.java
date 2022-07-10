package br.com.raul.classroomcopy.service.impls;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.raul.classroomcopy.dto.UserRegistrationDTO;
import br.com.raul.classroomcopy.model.Classroom;
import br.com.raul.classroomcopy.model.Role;
import br.com.raul.classroomcopy.model.User;
import br.com.raul.classroomcopy.model.UserToClassroom;
import br.com.raul.classroomcopy.repository.UserRepository;
import br.com.raul.classroomcopy.service.UserService;

@Service
public class UserServiceImpls implements UserService {

    private UserRepository userRepository;

    public UserServiceImpls(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserRegistrationDTO userRegistrationDTO) {

        User user = new User();
        user.setName(userRegistrationDTO.getName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()));
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Arrays.asList(role));

        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

    }
}
