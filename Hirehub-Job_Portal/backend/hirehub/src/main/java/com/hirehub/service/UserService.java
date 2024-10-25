package com.hirehub.service;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.dto.UserDTO;


public interface UserService {
    public UserDTO registerUser(UserDTO userDTO) throws Exception;

    public UserDTO loginUser(LoginDTO loginDTO) throws Exception;

    public Boolean sendOTP(String email) throws Exception;

    public Boolean verifyOTP(String email,String otp) throws Exception;

    ResponseDTO changePassword(LoginDTO loginDTO) throws Exception;
}
