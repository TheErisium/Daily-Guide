package com.johnny.dailyguide.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.johnny.dailyguide.models.Task;
import com.johnny.dailyguide.models.User;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByUser(User user);
}
