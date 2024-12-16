package com.chatlet.chatlet.controllers;


import com.chatlet.chatlet.data.dtos.ProfileDto;
import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.services.ProfileService;
import com.sun.net.httpserver.Headers;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@AllArgsConstructor
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {

        return ResponseEntity.ok(profileService.getProfile());

    }


    @PostMapping(value = "/profile")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody ProfileDto profileDto) {

        profileService.updateProfile(profileDto);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Profile updated successfully");
        return ResponseEntity.ok(responseDTO);

    }


    @PostMapping(value = "/profile/picture",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> uploadPicture(@RequestParam("file") MultipartFile picture) throws IOException {

        profileService.uploadPicture(picture);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Picture uploaded successfully");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/profile/picture")
    public ResponseEntity<byte[]> getPicture() throws IOException {

        Pair<byte[],String> picture = profileService.getPicture();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(picture.getRight()));



        return new  ResponseEntity<>(picture.getLeft(),headers, HttpStatus.OK);

    }




    //TODO: use username instead of picturelink
    @GetMapping("/profile/picture/{pictureLink}")
    public ResponseEntity<byte[]> getPicture(@PathVariable String pictureLink) throws IOException {
        Pair<byte[],String> picture = profileService.getPicture(pictureLink);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(picture.getRight()));
        headers.setContentLength(picture.getLeft().length);
        return new ResponseEntity<>(picture.getLeft(), headers, HttpStatus.OK);
    }


}
