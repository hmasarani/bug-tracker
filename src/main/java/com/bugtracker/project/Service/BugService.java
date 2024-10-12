package com.bugtracker.project.Service;
import com.bugtracker.project.DAO.BugRepository;
import com.bugtracker.project.entity.Bug;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BugService {
    // This service class is used to implement bugRepository and then called by controller.
    @Autowired
    private BugRepository bugRepository;

    // get All bugs.
    public List<Bug> getAllBugs(){
        return bugRepository.findAll();
    }
    // get bug by id
    public Optional<Bug> getBugById(int id){
        return bugRepository.findById(id);
    }

    // new bug -- create/add
    @Transactional
    public Bug addBug(Bug bug) {
        return bugRepository.save(bug);
    }

    //update bug using id and bug details.

    @Transactional
    public Optional<Bug> updateBug(int id, Bug bugDetails) {
        Optional<Bug> existingBug = getBugById(id);

        if (existingBug.isPresent()) {
            Bug updatedBug = existingBug.get();
            // Update all fields that need to be changed
            updatedBug.setTitle(bugDetails.getTitle());
            updatedBug.setDescription(bugDetails.getDescription());
            updatedBug.setPriority(bugDetails.getPriority());
            updatedBug.setStatus(bugDetails.getStatus());
            updatedBug.setUser(bugDetails.getUser());

            return Optional.of(bugRepository.save(updatedBug));
        }

        // Optionally log an error or throw an exception
        return Optional.empty();
    }


    //delete bug by id
    @Transactional
    public String deleteBugById(int id) {
        Optional<Bug> thisBug = getBugById(id);
        if(thisBug.isPresent()) {
            bugRepository.delete(thisBug.get());
            return "Bug with id " + id + " is deleted successfully";
        }
       return "Bug with id " + id + " is not found";
    }

}
