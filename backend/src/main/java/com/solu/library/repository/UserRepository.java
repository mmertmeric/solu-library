package com.solu.library.repository;

// DÜZELTME BURADA: 'entity' yerine 'model' olmalı
import com.solu.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}