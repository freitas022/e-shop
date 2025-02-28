package com.myapp.dtos;

import com.myapp.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String phone;
    private String email;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        phone = user.getPhone();
        email = user.getEmail();
    }
}
