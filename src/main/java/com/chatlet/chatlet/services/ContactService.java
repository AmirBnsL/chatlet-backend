
package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.ContactDTO;
import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Contact;
import com.chatlet.chatlet.data.projections.ContactInfo;
import com.chatlet.chatlet.data.securityEntities.SecurityUser;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

import static com.chatlet.chatlet.utils.ObjectMappers.contactToDTO;


@Service
@AllArgsConstructor
public class ContactService {

    private AuthRepository authRepository;
    private ContactRepository contactRepository;


    @Transactional
    public Collection<ContactInfo> getContacts() {

        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);


        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Auth auth = securityUser.getAuth();

        return contactRepository.findAllContactsInfoByUserId(auth.getId());
        
    }




    @Transactional
    public Long getCrudeContacts() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Auth auth = securityUser.getAuth();

        return contactRepository.countAllByUserIdOrContactId(auth,auth);

    }


    @Transactional
    public void createContact(String contactName) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Auth auth = securityUser.getAuth();

        Auth contactAuth = authRepository.findByUsername(contactName).orElseThrow(()-> new UsernameNotFoundException("Contact Not Found"));
        if (auth.getId().equals(contactAuth.getId())) {
            throw new RuntimeException("You can't add yourself as a contact");
        }
        Contact contact = new Contact();
        contact.setCreatedAt(Timestamp.from(Instant.now()));
        contact.setContactId(auth);
        contact.setUserId(contactAuth);
        contact.setIsFriend(false);
        contact.setIsBlocked(false);
        contact.setUpdatedAt(Timestamp.from(Instant.now()));

        contactRepository.save(contact);

    }



    public void acceptInvite(String username) {

        Contact contact = getContact(username);
        contact.setIsFriend(true);
        contact.setUpdatedAt(Timestamp.from(Instant.now()));
        contactRepository.save(contact);

    }


    private Contact getContact(String username) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Auth auth = securityUser.getAuth();

        Auth contactAuth = authRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Contact Not Found"));

        return contactRepository.findByUserIdAndContactIdAndIsFriend(auth, contactAuth, false).orElseThrow(()-> new UsernameNotFoundException("Contact Not Found"));
    }

    private ContactDTO getContactDTO(String username) {
        return  contactToDTO(getContact(username));
    }


    public void rejectInvite(String username) {


        Contact contact = getContact(username);

        contactRepository.delete(contact);

    }
}
