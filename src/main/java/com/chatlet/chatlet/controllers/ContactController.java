package com.chatlet.chatlet.controllers;

import com.chatlet.chatlet.data.dtos.ResponseDTO;
import com.chatlet.chatlet.data.projections.ContactInfo;
import com.chatlet.chatlet.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class ContactController {

    private ContactService contactService;


    @PostMapping("/accept")
    ResponseEntity<ResponseDTO> sendInvite(String username) {
        contactService.acceptInvite(username);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Invite accepted successfully");
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/reject")
    ResponseEntity<ResponseDTO> rejectInvite(String username) {
        contactService.rejectInvite(username);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Invite rejected successfully");
        return ResponseEntity.ok(responseDTO);
    }





    @PostMapping("/contact")
    ResponseEntity<ResponseDTO> createContact(String contactName) {
        contactService.createContact(contactName);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Contact created successfully");
        return ResponseEntity.ok(responseDTO);

    }



    @GetMapping("/contacts")
    ResponseEntity<Collection<ContactInfo>> getContacts() {
        return ResponseEntity.ok(contactService.getContacts());
    }

    @GetMapping("/contacts/crude")
    ResponseEntity<Long> getCrudeContacts() {
        return ResponseEntity.ok(contactService.getCrudeContacts());
    }


}
