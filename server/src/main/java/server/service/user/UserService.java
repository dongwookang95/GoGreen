package server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.user.User;
import server.exception.DataBaseException;
import server.exception.user.UserNotFoundException;
import server.repository.user.UserRepository;

import java.util.List;

/**
 * Service for all operations concerning User objects.
 */
@Service
public class UserService implements IUserService {

    /**
     * Autowired UserRepository to use its functions.
     */
    private final UserRepository userRepository;

    /**
     * Injecting repositories into service.
     * @param userRepository UserRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new User in DB.
     * @param user User that needs to be created
     * @return User that is created
     */
    @Override
    public User createUser(final User user) {
        if (userRepository.findById(user.getUsername()).isPresent()) {
            throw new DataBaseException("User already in database!");
        }
        return userRepository.save(user);
    }

    /**
     * Find all User objects in DB.
     * @return List of all User objects
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find a specific User in DB.
     * @param username username that refers to LocalProduce.
     * @return Optional User
     */
    @Override
    public User findByUsername(final String username) {
        return userRepository.findById(username).orElseThrow(() ->
                new UserNotFoundException(username));
    }

    /**
     * Delete an User in DB.
     * @param user User that needs to be deleted
     */
    @Override
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }

    /**
     * Update an User in DB.
     * @param user User that needs to be updated.
     */
    @Override
    public void updateUser(final User user) {
        userRepository.save(user);
    }
}
