package com.myapp.dtos;

import com.myapp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private String name;

    public ClientDto(User client) {
        id = client.getId();
        name = client.getName();
    }
}
