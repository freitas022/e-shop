package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.Payment;

import java.time.Instant;

public record PaymentDto(Long id, Instant moment) {

    public PaymentDto(Payment entity) {
        this(entity.getId(), entity.getMoment());
    }
}
