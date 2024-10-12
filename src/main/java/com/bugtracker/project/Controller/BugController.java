package com.bugtracker.project.Controller;

import com.bugtracker.project.Service.BugService;
import com.bugtracker.project.entity.Bug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bugs")
@CrossOrigin(origins = "*")
public class BugController {

    @Autowired
    private final BugService bugService;

    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    //get all bugs
    @GetMapping
    public List<Bug> getAllBugs(){
        return bugService.getAllBugs();
    }
    //get bug by id
    @GetMapping("/{id}")
    public Bug getBugById(@PathVariable int id){
        Optional<Bug> thisBug = bugService.getBugById(id);
        return thisBug.orElse(null);
    }
    // create a new bug
    @PostMapping
    public ResponseEntity<Bug> createBug(@RequestBody Bug bug){
        Bug newBug = bugService.addBug(bug);
        return ResponseEntity.status(201).body(newBug);
    }

    // Delete bug
    @DeleteMapping("/{id}")
    public ResponseEntity<Bug> deleteBug(@RequestBody int id){
        Optional<Bug> thisBug = bugService.getBugById(id);
        if(thisBug.isPresent()){
            bugService.deleteBugById(id);
        }
        return ResponseEntity.status(200).body(thisBug.get());
    }

    //update bug
    @PutMapping("/{id}") // Define the endpoint for updating
    public ResponseEntity<Bug> updateBug(@PathVariable int id, @RequestBody Bug bugDetails) {
        Optional<Bug> updatedBug = bugService.updateBug(id, bugDetails);
        return updatedBug.map(bug -> new ResponseEntity<>(bug, HttpStatus.OK)) // Return 200 OK with the updated bug
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return 404 if not found
    }

}
