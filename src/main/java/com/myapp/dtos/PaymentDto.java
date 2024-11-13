package com.myapp.dtos;

import com.myapp.entities.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class PaymentDto {

    private Integer id;
    private Instant moment;

    public PaymentDto(Payment payment) {
        id = payment.getId();
        moment = payment.getMoment();
    }
}
