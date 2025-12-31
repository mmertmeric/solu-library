package com.solu.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BorrowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // İsteyen Kişi (User tablosuna bağlanır)
    @ManyToOne
    private User requester;

    // Kitabın Kendisi (Book tablosuna bağlanır)
    @ManyToOne
    private Book book;

    // Durum (Bekliyor, Onaylandı vs.)
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private int requestedDays;      // Kaç günlüğüne istedi?
    private LocalDate requestDate;  // İstek atılan tarih
    private LocalDate deliveryDate; // Teslim etmesi gereken tarih (Onaylanınca hesaplanacak)

    // --- CONSTRUCTOR & GETTER-SETTER ---
    public BorrowRequest() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public int getRequestedDays() { return requestedDays; }
    public void setRequestedDays(int requestedDays) { this.requestedDays = requestedDays; }
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
}