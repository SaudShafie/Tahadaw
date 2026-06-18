package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Optional<Reminder> findReminderById(Long id);

    List<Reminder> findAllByUser_IdOrderByReminderDateAsc(Long userId);

    @Query("select r from Reminder r where r.status=?1 and r.reminderDate>=?2 and r.reminderDate < ?3")
    List<Reminder> findTodayPendingReminders(String status , LocalDateTime startOfDay , LocalDateTime endOfDay);
}
