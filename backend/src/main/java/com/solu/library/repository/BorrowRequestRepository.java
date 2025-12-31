package com.solu.library.repository;

import com.solu.library.model.BorrowRequest;
import com.solu.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {
    // Benim gönderdiğim istekler
    List<BorrowRequest> findByRequester(User requester);

    // Bana gelen istekler (Kitabın sahibi ben olduğum istekler)
    List<BorrowRequest> findByBook_Owner(String ownerUsername);
}