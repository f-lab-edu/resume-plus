package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.Resume;
import com.ddoddii.resume.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findById(long id);

    List<Resume> findByUser(User user);

}
