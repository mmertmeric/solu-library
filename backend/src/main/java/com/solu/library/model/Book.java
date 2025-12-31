package com.solu.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    // Kitap kapağı (Base64 formatında uzun metin)
    @Column(columnDefinition = "TEXT")
    private String image;

    private String owner; // Kitabın sahibinin kullanıcı adı (username)
    private String status; // "available" veya "borrowed"

    public Book() {}

    public Book(String title, String author, String owner, String status, String image) {
        this.title = title;
        this.author = author;
        this.owner = owner;
        this.status = status;
        this.image = image;
    }

    // --- GETTER & SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}