package tdd.duo.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdd.duo.domain.User;
import tdd.duo.domain.auth.Authentication;
import tdd.duo.exception.AlreadyExistException;
import tdd.duo.exception.PasswordMismatchException;
import tdd.duo.repository.UserRepository;

/**
 * Created by yoon on 15. 4. 20..
 */
@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public void login(Authentication authentication) throws NotFoundException, PasswordMismatchException, IllegalArgumentException {
        if (!authentication.isValid())
            throw new IllegalArgumentException();

        User user = userRepository.findByEmail(authentication.getId());

        if (!authentication.isMathchId(user))
            throw new NotFoundException("Authentication Id mismatch or does not exist");

        if (!authentication.isMatchPassword(user))
            throw new PasswordMismatchException();
    }

    public User create(User user) throws AlreadyExistException, IllegalArgumentException {

        if (!user.canRegistable()) {
            throw new IllegalArgumentException();
        }

        User selectedUser = userRepository.findByEmail(user.getEmail());

        if (selectedUser != null)
            throw new AlreadyExistException();

        return userRepository.save(user);
    }
}
