package com.solu.library.service;

// DÜZELTME 1: Artık 'entity' değil 'model' paketinden çekiyoruz
import com.solu.library.model.User;
import com.solu.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // EKLEME: Profil sayfası için kullanıcıyı ID ile bulma metodu (Frontend bunu istiyor)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {

            // Gelen verileri set et
            if(userDetails.getFullName() != null) existingUser.setFullName(userDetails.getFullName());
            if(userDetails.getEmail() != null) existingUser.setEmail(userDetails.getEmail());
            if(userDetails.getAboutMe() != null) existingUser.setAboutMe(userDetails.getAboutMe());
            if(userDetails.getLocation() != null) existingUser.setLocation(userDetails.getLocation());
            if(userDetails.getPhone() != null) existingUser.setPhone(userDetails.getPhone());
            if(userDetails.getProfileImage() != null) existingUser.setProfileImage(userDetails.getProfileImage());
            if(userDetails.getCurrentStatus() != null) existingUser.setCurrentStatus(userDetails.getCurrentStatus());

            // Puan güncellemesi gerekirse diye (güvenlik için null kontrolü)
            if(userDetails.getTrustScore() != 100) existingUser.setTrustScore(userDetails.getTrustScore());

            // Kaydet ve hemen diske yaz
            return userRepository.saveAndFlush(existingUser);
        }
        return null;
    }
}