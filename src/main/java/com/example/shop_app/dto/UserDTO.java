package com.example.shop_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
        @JsonProperty("fullname")
        private String fullName;

        @JsonProperty("phone_number")
        @NotBlank(message = "Phone number is required")
         private String phoneNumber;

        private String address;

        @NotBlank(message = "Password cannot be null")
        private String password;

    private String retypePassword;

        @JsonProperty("date_of_birth")
         private String dateOfBirth;

        @JsonProperty("facebook_account_id")
         private String facebookAccountId;

        @JsonProperty("google_account_id")
            private String googleAccountId;

        @JsonProperty("role_id")
        @NotNull(message = "Role ID is required")
        private Long roleId;

}