package com.bugtracker.project.DAO;

import com.bugtracker.project.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Integer> {
}
