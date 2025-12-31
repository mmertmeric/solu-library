package com.solu.library.model;

public enum RequestStatus {
    PENDING,            // İstek gönderildi, onay bekliyor
    APPROVED,           // Onaylandı, kitap şu an ödünçte
    REJECTED,           // Reddedildi
    RETURN_REQUESTED,   // Alan kişi "İade ettim" dedi, onay bekliyor
    COMPLETED           // Sahibi teslim aldı, işlem bitti (Puanlar verildi)
}