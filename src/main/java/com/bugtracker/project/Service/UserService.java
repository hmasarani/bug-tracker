package com.bugtracker.project.Service;
import com.bugtracker.project.DAO.UserRepository;
import com.bugtracker.project.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    // This service class is used to implement bugRepository and then called by controller.
    @Autowired
    private UserRepository userRepository;

    // get All bugs.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // get bug by id
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }

    // new bug -- create/add
    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //update bug using id and bug details.

    @Transactional
    // Update user by ID
    public Optional<User> updateUser(int userId, User userDetails) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            // Update user details
            updatedUser.setFirstName(userDetails.getFirstName());
            updatedUser.setLastName(userDetails.getLastName());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setRole(userDetails.getRole()); // Update role if necessary

            return Optional.of(userRepository.save(updatedUser));
        }
        return Optional.empty();
    }

    //delete bug by id
    @Transactional
    public String deleteUserById(int id) {
        Optional<User> thisUser = getUserById(id);
        if(thisUser.isPresent()) {
            userRepository.delete(thisUser.get());
            return "User with id " + id + " is deleted successfully";
        }
        return "User with id " + id + " is not found";
    }

}
