package com.chatlet.chatlet.services;

import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.entities.Auth;
import com.chatlet.chatlet.data.entities.Profile;
import com.chatlet.chatlet.data.securityEntities.SecurityUser;
import com.chatlet.chatlet.repositories.AuthRepository;
import com.chatlet.chatlet.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.chatlet.chatlet.utils.ObjectMappers.profileToDto;

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private AuthRepository authRepository;

    public ProfileDto getProfile() {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = securityUser.getAuth().getProfile();
        ProfileDto profileDto = profileToDto(profile);
        profileDto.setUsername(securityUser.getUsername());
        return profileDto;
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
        //we dont update profile picture because it is being updated on upload rather than profile edit
        profileRepository.save(profile);



    }

    public void uploadPicture(MultipartFile picture) throws IOException {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = securityUser.getAuth().getProfile();
        String modifiedFileName = profile.getAuth().getId().toString() + picture.getOriginalFilename().substring(picture.getOriginalFilename().lastIndexOf("."));

        File file = new File("src/main/resources/static/images/" + modifiedFileName);

        picture.transferTo(file.toPath());

        profile.setPictureLink(modifiedFileName);
        profileRepository.save(profile);
    }

    public Pair<byte[],String> getPicture() throws IOException {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile profile = securityUser.getAuth().getProfile();
        Path path = Paths.get("src/main/resources/static/images/" + profile.getPictureLink());


        byte[] fileBytes = Files.readAllBytes(path);
        String contentType = Files.probeContentType(path);


        return Pair.of(fileBytes,contentType);
    }

    public Pair<byte[],String> getPicture(String pictureLink) throws IOException {

        Path path = Paths.get("src/main/resources/static/images/" + pictureLink);
        if (!Files.exists(path)) {
            throw new IOException("File not found");
        }


        byte[] fileBytes = Files.readAllBytes(path);
        String contentType = Files.probeContentType(path);


        return Pair.of(fileBytes,contentType);
    }

}
