package com.solu.library.controller;

import com.solu.library.model.Book;
import com.solu.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*") // Frontend erişimi için
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Tüm Kitapları Listele (Vitrin için)
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Kitap Ekle
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Kitap Sil (Kütüphanem sayfasında silmek için)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}