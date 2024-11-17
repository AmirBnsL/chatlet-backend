package com.chatlet.chatlet.config;

import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Contact;
import com.chatlet.chatlet.data.entities.Profile;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ContactRepository;
import com.chatlet.chatlet.repositories.MessageRepository;
import com.chatlet.chatlet.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.chatlet.chatlet.data.entities.Message;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Component
@AllArgsConstructor
@org.springframework.context.annotation.Profile("dev")
public class DataLoader implements CommandLineRunner {
    //TODO: implement ai chat between users
    private AuthRepository authRepository;
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    private ContactRepository contactRepository;
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Auth auth = new Auth();
        auth.setUsername("amirbnsl");
        auth.setPassword(passwordEncoder.encode("0793009170"));
        auth.setEmail("amirbnsl@gmail.com");
        auth.setIsDisabled(false);
        authRepository.save(auth);
        Profile profile = new Profile();
        profile.setAuth(auth);
        profile.setFirstname("Amir");
        profile.setLastname("benslaimi");
        profile.setPictureLink("string");
        profile.setGender("MALE");
        profile.setBirth(LocalDate.now());
        profileRepository.save(profile);



        Auth auth2 = new Auth();
        auth2.setUsername("amirhydra");
        auth2.setPassword(passwordEncoder.encode("0793009170"));
        auth2.setEmail("amirhydra@gmail.com");
        auth2.setIsDisabled(false);
        authRepository.save(auth2);
        Profile profile2 = new Profile();
        profile2.setAuth(auth2);
        profile2.setFirstname("Amir");
        profile2.setLastname("hydra");
        profile2.setPictureLink("string");
        profile2.setGender("MALE");
        profile2.setBirth(LocalDate.now());
        profileRepository.save(profile2);

        Auth auth3 = new Auth();
        auth3.setUsername("mohamedjijly");
        auth3.setPassword(passwordEncoder.encode("0793009170"));
        auth3.setEmail("mohjijly@gmail.com");
        auth3.setIsDisabled(false);
        authRepository.save(auth3);
        Profile profile3 = new Profile();
        profile3.setAuth(auth3);
        profile3.setFirstname("moh");
        profile3.setLastname("bouchemella");
        profile3.setPictureLink("string");
        profile3.setGender("MALE");
        profile3.setBirth(LocalDate.now());
        profileRepository.save(profile3);

        Contact contact1 = new Contact();
        contact1.setUserId(auth);
        contact1.setContactId(auth2);
        contact1.setIsBlocked(false);
        contact1.setIsFriend(true);

        Contact contact2 = new Contact();
        contact2.setUserId(auth3);
        contact2.setContactId(auth);
        contact2.setIsBlocked(false);
        contact2.setIsFriend(true);
        contactRepository.save(contact2);

        Message message = new Message();
        message.setContact(contact1);
        message.setSender(auth.getUsername());
        message.setReceiver(auth2.getUsername());
        message.setTimestamp(Timestamp.from(Instant.now()));
        message.setMessage("goodbye");
        message.setMessageType("TEXT");
        messageRepository.save(message);

        Message message1 = new Message();
        message1.setContact(contact1);
        message1.setSender(auth.getUsername());
        message1.setReceiver(auth2.getUsername());
        message1.setTimestamp(Timestamp.from(Instant.now()));
        message1.setMessage("good morning");
        message1.setMessageType("TEXT");
        messageRepository.save(message1);

        Message message3 = new Message();
        message3.setContact(contact1);
        message3.setSender(auth.getUsername());
        message3.setReceiver(auth2.getUsername());
        message3.setTimestamp(Timestamp.from(Instant.now()));
        message3.setMessage("hello amir how are you doing?");
        message3.setMessageType("TEXT");
        messageRepository.save(message3);
        contact1.getMessages().add(message);
        contact1.getMessages().add(message1);
        contact1.getMessages().add(message3);
        contactRepository.save(contact1);

    }
}
