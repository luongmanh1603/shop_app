package com.example.shop_app.service;

import com.example.shop_app.dto.UserDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Role;
import com.example.shop_app.model.User;
import com.example.shop_app.repo.RoleRepo;
import com.example.shop_app.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        // Check if the user already exists
        if (userRepo.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("User already exists");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role existingRole = roleRepo.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRole(existingRole);
        //ma hoa password

        return userRepo.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        //phan quyen
        return null;
    }
}
