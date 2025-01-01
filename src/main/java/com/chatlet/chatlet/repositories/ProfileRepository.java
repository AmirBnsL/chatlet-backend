package com.chatlet.chatlet.repositories;

import com.chatlet.chatlet.data.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository  extends JpaRepository<Profile, Long> {




}
