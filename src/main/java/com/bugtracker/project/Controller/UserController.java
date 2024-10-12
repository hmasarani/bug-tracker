package com.bugtracker.project.Controller;

import com.bugtracker.project.Service.UserService;
import com.bugtracker.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get all user
    @GetMapping
    public List<User> getAllBugs(){
        return userService.getAllUsers();
    }
    //get bug by user
    @GetMapping("/{id}")
    public User getBugById(@PathVariable int id){
        Optional<User> thisUser = userService.getUserById(id);
        return thisUser.orElse(null);
    }
    // create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@RequestBody int id){
        Optional<User> thisUser = userService.getUserById(id);
        if(thisUser.isPresent()){
            userService.deleteUserById(id);
        }
        return ResponseEntity.status(200).body(thisUser.get());
    }

    //update user
    @PutMapping("/{id}") // Define the endpoint for updating
    public ResponseEntity<User> User(@PathVariable int id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)) // Return 200 OK with the updated bug
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return 404 if not found
    }

}

