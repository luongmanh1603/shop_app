package com.example.shop_app.service;

import com.example.shop_app.dto.UserDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
