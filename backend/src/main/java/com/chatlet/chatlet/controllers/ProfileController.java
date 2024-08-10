package com.chatlet.chatlet.controllers;


import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.services.ProfileService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {

        return ResponseEntity.ok(profileService.getProfile());

    }


    @PostMapping("/profile")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody ProfileDto profileDto) {

        profileService.updateProfile(profileDto);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Profile updated successfully");
        return ResponseEntity.ok(responseDTO);

    }


}
