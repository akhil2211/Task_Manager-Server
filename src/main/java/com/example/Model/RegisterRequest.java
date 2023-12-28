package com.example.Model;

import com.example.CustomValidator.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    @StringValid
    @NotBlank(message = "Empty field!")
    private String firstname;

    @StringValid
    private String lastname;

//    @NotNull
//    @NotEmpty
//    @Size(min=4,max = 10,message = "Username must be atleast 4 characters and atmax 10 characters")
    @FieldLengthValid
    private String username;

//    @NotNull
//    @NotEmpty
//    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}",message="Password must contain minimum 8 and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    @PasswordValid
    private String password;

    //    @NotNull
//    @NotEmpty
//    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid email")
    @EmailValid
    private String email;

    @Numeric
    private String orgId;

    @Numeric
    private String roleId;

    private Integer reporting_officer_id;

}
