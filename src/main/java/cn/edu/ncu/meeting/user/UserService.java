package cn.edu.ncu.meeting.user;

import cn.edu.ncu.meeting.user.model.User;
import cn.edu.ncu.meeting.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Service
 * @author lorry
 * @author lin864464995@163.com
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Value("${Manage.salt}")
    private String salt;

    private static BCryptPasswordEncoder encode = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Add user service.
     * @param username the username.
     * @param name the name.
     * @param password the password.
     * @return if add user successful return true else return false.
     */
    boolean addUser(String username, String name, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return false;
        }
        String hash = encode.encode(salt + password + salt);
        user = new User();

        user.setUsername(username);
        user.setName(name);
        user.setPassword(hash);

        userRepository.save(user);

        return true;
    }

    /**
     * Check User password.
     * @param user be checked user.
     * @param password be checked password.
     * @return if password correct return true else return false.
     */
    boolean checkPassword(User user, String password) {
        return encode.matches(salt + password + salt, user.getPassword());
    }

    /**
     * Load a user by username.
     * @param username the username.
     * @return the user.
     * @throws UsernameNotFoundException if user is not exits throw UsernameNotFoundException.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exits.");
        }
        return user;
    }
}
