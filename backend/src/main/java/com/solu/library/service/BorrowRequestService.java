package com.solu.library.service;

import com.solu.library.model.*;
import com.solu.library.repository.BookRepository;
import com.solu.library.repository.BorrowRequestRepository;
import com.solu.library.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowRequestService {

    private final BorrowRequestRepository requestRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowRequestService(BorrowRequestRepository requestRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // 1. İSTEK OLUŞTURMA
    public BorrowRequest createRequest(Long bookId, Long requesterId, int days) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
        User requester = userRepository.findById(requesterId).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Kendi kitabına istek atamazsın
        if(book.getOwner().equals(requester.getUsername())) {
            throw new RuntimeException("Kendi kitabını ödünç alamazsın!");
        }
        // Kitap müsait değilse istek atamazsın
        if(!book.getStatus().equals("available")) {
            throw new RuntimeException("Bu kitap şu an müsait değil.");
        }

        BorrowRequest request = new BorrowRequest();
        request.setBook(book);
        request.setRequester(requester);
        request.setRequestedDays(days);
        request.setStatus(RequestStatus.PENDING);
        request.setRequestDate(LocalDate.now());

        return requestRepository.save(request);
    }

    // 2. İSTEĞİ ONAYLAMA (Tarih Hesabı Burada)
    public BorrowRequest approveRequest(Long requestId) {
        BorrowRequest request = requestRepository.findById(requestId).orElseThrow();

        // Durumu onayla
        request.setStatus(RequestStatus.APPROVED);

        // Teslim tarihini hesapla: BUGÜN + İSTENEN GÜN
        request.setDeliveryDate(LocalDate.now().plusDays(request.getRequestedDays()));

        // Kitabın durumunu "borrowed" (Dolu) yap
        Book book = request.getBook();
        book.setStatus("borrowed");
        bookRepository.save(book);

        return requestRepository.save(request);
    }

    // 3. İADE TALEBİ (Alan Kişi: "Ben gönderdim" der)
    public BorrowRequest requestReturn(Long requestId) {
        BorrowRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.RETURN_REQUESTED);
        return requestRepository.save(request);
    }

    // 4. İADEYİ TAMAMLAMA ve PUANLAMA (Veren Kişi: "Aldım" der)
    public BorrowRequest completeReturn(Long requestId) {
        BorrowRequest request = requestRepository.findById(requestId).orElseThrow();
        User borrower = request.getRequester();

        // Puan Mantığı
        LocalDate today = LocalDate.now();
        LocalDate deadline = request.getDeliveryDate();

        if (today.isAfter(deadline)) {
            // Gecikme Cezası: Geciken gün x 1 puan
            long daysLate = ChronoUnit.DAYS.between(deadline, today);
            borrower.setTrustScore(borrower.getTrustScore() - (int)daysLate);
        } else {
            // Zamanında Teslim Ödülü
            borrower.setTrustScore(borrower.getTrustScore() + 5);
        }
        userRepository.save(borrower);

        // İşi bitir
        request.setStatus(RequestStatus.COMPLETED);

        // Kitabı tekrar "available" (Müsait) yap
        Book book = request.getBook();
        book.setStatus("available");
        bookRepository.save(book);

        return requestRepository.save(request);
    }

    public List<BorrowRequest> getMyRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return requestRepository.findByRequester(user);
    }

    public List<BorrowRequest> getIncomingRequests(String username) {
        return requestRepository.findByBook_Owner(username);
    }
}