package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
