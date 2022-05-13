package com.example.mynimalist.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with username %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String register(User user){
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (userExists) {
            throw new IllegalStateException("El usuario ya existe");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "se ha registrado correctamente";
    }

    public String login(User loginUser){

        Optional<User> user = userRepository.findByUsername(loginUser.getUsername());

        if(user.isPresent() && bCryptPasswordEncoder.matches(loginUser.getPassword(),  user.get().getPassword()) ) {
            return "se ha logueado correctamente";
        }

        return "error en el usuario o contraseña";
    }

    public User getUserByUsername(String username){ //TODO: controlar que exista aunque solo deberia ser llamado a partir de metodos que usen token
        return userRepository.findByUsername(username).get();
    }
}
