package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.entities.Profile;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.chatlet.chatlet.utils.ObjectMappers.profileToDto;

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private AuthRepository authRepository;

    public ProfileDto getProfile() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Profile profile = authRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found")).getProfile();
        return profileToDto(profile);
    }

    public void updateProfile(ProfileDto profileDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Profile profile = authRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found")).getProfile();
        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());
        profile.setGender(profileDto.getGender());
        profile.setBirth(profileDto.getBirth());
        profile.setPictureLink(profileDto.getPictureLink());

        profileRepository.save(profile);



    }
}
