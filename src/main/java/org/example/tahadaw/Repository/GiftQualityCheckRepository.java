package org.example.tahadaw.Repository;

import org.example.tahadaw.Model.GiftQualityCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftQualityCheckRepository extends JpaRepository<GiftQualityCheck, Long> {

    Optional<GiftQualityCheck> findGiftQualityCheckById(Long id);

    List<GiftQualityCheck> findAllByRecipient_IdAndUser_Id(Long recipientId, Long userId);

    Optional<GiftQualityCheck> findGiftQualityCheckByIdAndUser_Id(Long checkId, Long userId);
}
