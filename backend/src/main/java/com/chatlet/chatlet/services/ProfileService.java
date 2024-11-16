package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Profile;
import com.chatlet.chatlet.data.securityEntities.SecurityUser;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chatlet.chatlet.utils.ObjectMappers.profileToDto;

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private AuthRepository authRepository;

    public ProfileDto getProfile() {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = securityUser.getAuth().getProfile();
        return profileToDto(profile);
    }

    @Transactional
    public void updateProfile(ProfileDto profileDto) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Auth auth = securityUser.getAuth();
        Profile profile;

        if (auth.getProfile() == null) {
            profile = new Profile();
            profile.setAuth(auth);
        } else  {
            profile = auth.getProfile();
        }

        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());
        profile.setGender(profileDto.getGender());
        profile.setBirth(profileDto.getBirth());
        profile.setPictureLink(profileDto.getPictureLink());

        profileRepository.save(profile);



    }
}
