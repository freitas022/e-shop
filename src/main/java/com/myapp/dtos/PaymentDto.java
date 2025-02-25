package com.myapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myapp.entities.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class PaymentDto {

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant moment;

    public PaymentDto(Payment payment) {
        id = payment.getId();
        moment = payment.getMoment();
    }
}
