package br.com.fiap.energenius.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        var emailAlreadyExists  = userRepository.findByEmail(user.getEmail()).isPresent();

        if (emailAlreadyExists) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
