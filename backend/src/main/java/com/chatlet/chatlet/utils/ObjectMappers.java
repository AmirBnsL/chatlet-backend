package com.chatlet.chatlet.utils;

import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.entities.Profile;

public class ObjectMappers {

    public static ProfileDto profileToDto(Profile profile) {
        return ProfileDto.builder()
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .birth(profile.getBirth())
                .gender(profile.getGender())
                .pictureLink(profile.getPictureLink())
                .birth(profile.getBirth())
                .build();
    }
}
