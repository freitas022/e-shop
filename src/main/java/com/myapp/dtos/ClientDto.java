package com.myapp.dtos;

import com.myapp.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDto {

    private String name;

    public ClientDto(User client) {
        name = client.getName();
    }
}
