package com.chatlet.chatlet.repositories;

import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Contact;
import com.chatlet.chatlet.data.projections.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

    @Query(value = "SELECT a.id,picture_link,username FROM contact c join auth a on a.id = c.user_id OR a.id=c.contact_id join profile p on a.id = p.id WHERE (contact_id=:id OR user_id=:id) AND (a.id!=:id)",nativeQuery = true)
    Collection<ContactInfo> findAllContactsInfoByUserId(Long id);



    Collection<Contact> findAllByUserId(Auth auth);

    Long countAllByUserIdOrContactId(Auth auth,Auth contact);


    @Query("SELECT c FROM Contact c WHERE (c.userId=:user1 AND c.contactId=:user2 AND c.isFriend=:isFriend) OR (c.userId=:user2 AND c.contactId=:user1 AND c.isFriend=:isFriend)")
    Optional<Contact> findByUserIdAndContactIdAndIsFriend(Auth user1, Auth user2, Boolean isFriend);

}
