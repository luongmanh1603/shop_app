package com.example.shop_app.service;

import com.example.shop_app.components.JwtTokenUtil;
import com.example.shop_app.dto.UserDTO;
import com.example.shop_app.exception.DataNotFoundException;
import com.example.shop_app.model.Role;
import com.example.shop_app.model.User;
import com.example.shop_app.repo.RoleRepo;
import com.example.shop_app.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private  final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

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
                .address(userDTO.getAddress())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role existingRole = roleRepo.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRole(existingRole);
        //kiem tra neu co account facebook hoac google thi khong can password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() ==0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        //ma hoa password

        return userRepo.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> user = userRepo.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number/password");
        }
        //phan quyen
        User existingUser = user.get();
        //check password
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException(("Wrong password"));

            }
        }

            //authenticate with spring security
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    phoneNumber,
                    password,
                    existingUser.getAuthorities()
            );
            authenticationManager.authenticate(authenticationToken);
            return jwtTokenUtil.generateToken(existingUser);
        }
    }
