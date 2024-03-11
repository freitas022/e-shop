package com.meuportifolio.curso.dto;

import java.time.Instant;

import com.meuportifolio.curso.entities.Payment;

public record PaymentDto(Long id, Instant moment) {

    public PaymentDto(Payment entity) {
        this(entity.getId(), entity.getMoment());
    }
}
