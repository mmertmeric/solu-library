package com.solu.library.controller;

import com.solu.library.model.User;
import com.solu.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List; // List importu ekledim

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Tüm kullanıcıları getir
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Kayıt Ol
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Giriş Yap
    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    // Profil Güncelle
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // ID ile Kullanıcı Bilgisi Çekme (DÜZELTİLEN KISIM)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        // Eski yöntem yerine direkt servisteki metodu çağırıyoruz:
        return userService.getUserById(id);
    }
}