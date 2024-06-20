package com.example.ITired.Controllers;

import com.example.ITired.Services.UserService;
import com.example.ITired.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotoController {

    @Autowired
    private UserService userService;

    @GetMapping("/getPhoto")
    public ResponseEntity<byte[]> getPhoto() {
        User currentUser = userService.getCurrentUser();
        byte[] photoBytes = currentUser.getPhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(photoBytes.length);
        return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
    }
}