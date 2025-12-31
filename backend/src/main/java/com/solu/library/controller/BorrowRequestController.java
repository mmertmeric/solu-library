package com.solu.library.controller;

import com.solu.library.model.BorrowRequest;
import com.solu.library.service.BorrowRequestService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*") // Frontend erişimi için
public class BorrowRequestController {

    private final BorrowRequestService service;

    public BorrowRequestController(BorrowRequestService service) {
        this.service = service;
    }

    // İstek Oluştur: POST /api/borrow/create
    @PostMapping("/create")
    public BorrowRequest create(@RequestBody Map<String, Object> payload) {
        Long bookId = Long.valueOf(payload.get("bookId").toString());
        Long requesterId = Long.valueOf(payload.get("requesterId").toString());
        int days = Integer.parseInt(payload.get("days").toString());
        return service.createRequest(bookId, requesterId, days);
    }

    // Onayla: PUT /api/borrow/{id}/approve
    @PutMapping("/{id}/approve")
    public BorrowRequest approve(@PathVariable Long id) {
        return service.approveRequest(id);
    }

    // İade İste: PUT /api/borrow/{id}/return-request
    @PutMapping("/{id}/return-request")
    public BorrowRequest returnRequest(@PathVariable Long id) {
        return service.requestReturn(id);
    }

    // Tamamla (Puan Ver): PUT /api/borrow/{id}/complete
    @PutMapping("/{id}/complete")
    public BorrowRequest complete(@PathVariable Long id) {
        return service.completeReturn(id);
    }

    // Benim İsteklerim: GET /api/borrow/my-requests?userId=1
    @GetMapping("/my-requests")
    public List<BorrowRequest> getMyRequests(@RequestParam Long userId) {
        return service.getMyRequests(userId);
    }

    // Bana Gelenler: GET /api/borrow/incoming?username=mert
    @GetMapping("/incoming")
    public List<BorrowRequest> getIncoming(@RequestParam String username) {
        return service.getIncomingRequests(username);
    }
}