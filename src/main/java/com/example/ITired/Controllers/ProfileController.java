package com.example.ITired.Controllers;

import com.example.ITired.Services.UserService;
import com.example.ITired.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @PostMapping("/profile/uploadPhoto")
    public String uploadPhoto(@RequestParam("photo") MultipartFile photo, Model model) {
        if (!photo.isEmpty()) {
            try {
                // Получаем массив байтов из загруженной фотографии
                byte[] photoBytes = photo.getBytes();

                // Обновляем фотографию текущего пользователя в базе данных
                User currentUser = userService.getCurrentUser();
                currentUser.setPhoto(photoBytes);
                userService.saveUser(currentUser);


                return "redirect:/profile";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Failed to upload photo");
                // Возвращаем пользователя на страницу загрузки фото с сообщением об ошибке
                return "uploadPhotoForm";
            }
        } else {
            model.addAttribute("errorMessage", "No photo selected");
            // Возвращаем пользователя на страницу загрузки фото с сообщением о том, что фото не выбрано
            return "uploadPhotoForm";
        }
    }
}
