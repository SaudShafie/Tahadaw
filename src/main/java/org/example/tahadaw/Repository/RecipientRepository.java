package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    Optional<Recipient> findRecipientById(Long recipientId);

    List<Recipient> findAllByUser_Id(Long userId);

    Optional<Recipient> findRecipientByIdAndUser_Id(Long recipientId, Long userId);
}
