package server.service.user;

import server.entity.user.User;

import java.util.List;

/**
 * Interface to define functions in UserService.
 */
public interface IUserService {

    /**
     * Create a new User in DB.
     * @param user User that needs to be created
     * @return User that is created
     */
    User createUser(User user);

    /**
     * Find all User objects in DB.
     * @return List of all User objects
     */
    List<User> findAll();

    /**
     * Find a specific User in DB.
     * @param username username that refers to LocalProduce.
     * @return Optional User
     */
    User findByUsername(String username);

    /**
     * Delete an User in DB.
     * @param user User that needs to be deleted
     */
    void deleteUser(User user);

    /**
     * Update an User in DB.
     * @param user User that needs to be updated.
     */
    void updateUser(User user);
}
