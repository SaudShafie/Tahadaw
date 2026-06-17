package org.example.tahadaw.Service;

import lombok.RequiredArgsConstructor;
import org.example.tahadaw.Api.ApiException;
import org.example.tahadaw.Model.Recipient;
import org.example.tahadaw.Model.User;
import org.example.tahadaw.Repository.RecipientRepository;
import org.example.tahadaw.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final UserRepository userRepository;

    //Bayan CRUD
    public void addRecipient(Long userId , Recipient recipient){

        User user = userRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));


        recipient.setUser(user);
        recipient.setCreatedAt(LocalDateTime.now());
        recipient.setUpdatedAt(LocalDateTime.now());

        recipientRepository.save(recipient);
    }

    public List<Recipient> getRecipients(){
        return recipientRepository.findAll();
    }

    public void updateRecipient(Long userId, Long recipientId, Recipient recipient){

        userRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        Recipient oldRecipient = recipientRepository.findRecipientByIdAndUser_Id(recipientId, userId)
                .orElseThrow(() -> new ApiException("Recipient not found or does not belong to this user"));

        oldRecipient.setName(recipient.getName());
        oldRecipient.setRelationship(recipient.getRelationship());
        oldRecipient.setAge(recipient.getAge());
        oldRecipient.setGender(recipient.getGender());
        oldRecipient.setInterests(recipient.getInterests());
        oldRecipient.setHobbies(recipient.getHobbies());
        oldRecipient.setFavoriteColors(recipient.getFavoriteColors());
        oldRecipient.setFavoriteBrands(recipient.getFavoriteBrands());
        oldRecipient.setDislikes(recipient.getDislikes());
        oldRecipient.setPersonalityStyle(recipient.getPersonalityStyle());
        oldRecipient.setSizeInfo(recipient.getSizeInfo());
        oldRecipient.setNotes(recipient.getNotes());

        oldRecipient.setUpdatedAt(LocalDateTime.now());

        recipientRepository.save(oldRecipient);
    }


    public void deleteRecipient(Long userId , Long recipientId){

        userRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));

        Recipient recipient = recipientRepository.findRecipientByIdAndUser_Id(recipientId, userId)
                .orElseThrow(() -> new ApiException("Recipient not found or does not belong to this user"));

        recipientRepository.delete(recipient);
    }

    //Bayan
    public List<Recipient> getRecipientsByUserId(Long userId){

        User user = userRepository.findUserById(userId).orElseThrow(() -> new ApiException("User not found"));
        return recipientRepository.findAllByUser_Id(user.getId());
    }

    //Bayan
    public Recipient getRecipientByIdAndUserId(Long userId, Long recipientId){

        userRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User not found"));

        return recipientRepository.findRecipientByIdAndUser_Id(recipientId, userId)
                .orElseThrow(() -> new ApiException("Recipient not found or does not belong to this user"));
    }
}
