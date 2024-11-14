package com.myapp.dtos;

import com.myapp.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String name;

    public ClientDto(User client) {
        id = client.getId();
        name = client.getName();
    }
}
