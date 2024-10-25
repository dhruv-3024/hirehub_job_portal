package com.hirehub.api;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.dto.UserDTO;
import com.hirehub.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserAPI{
    @Autowired //no need to create object, whenever UserService obj is needed it'll be called directly
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws Exception { //for JSON obj, web req body
        userDTO = userService.registerUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws Exception { //for JSON obj, web req body

        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
    }

//    Send OTP to mail
    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOTP(@PathVariable @Email(message="{user.email.invalid}") String email) throws Exception { //for JSON obj, web req body
        userService.sendOTP(email);
        return new ResponseEntity<>(new ResponseDTO("OTP sent successfully!"), HttpStatus.OK);
    }

//    Verify OTP
    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOTP(@PathVariable  @Email(message="{user.email.invalid}") String email,@PathVariable @Pattern(regexp = "^[0-9]{6}$",message = "{opt.invalid}") String otp) throws Exception { //for JSON obj, web req body
        userService.verifyOTP(email,otp);
        return new ResponseEntity<>(new ResponseDTO("OTP has been verified!"), HttpStatus.OK);
    }

//    Change Password
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid LoginDTO loginDTO) throws Exception { //for JSON obj, web req body

        return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
    }
}
