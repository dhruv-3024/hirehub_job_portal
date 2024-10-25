package com.hirehub.service;

import com.hirehub.dto.LoginDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.dto.UserDTO;
import com.hirehub.entity.OTP;
import com.hirehub.entity.User;
import com.hirehub.exception.JobPortalException;
import com.hirehub.repository.OTPRepository;
import com.hirehub.repository.UserRepository;
import com.hirehub.utility.Data;
import com.hirehub.utility.Utilities;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//all business logic is written here
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //used for hashing the password

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfileService profileService;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if(optionalUser.isPresent()) throw new JobPortalException("USER_FOUND");
        userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws Exception {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
        if(!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
            throw new JobPortalException("INVALID_CREDENTIALS");
        }
        return user.toDTO();
    }

    @Override
    public Boolean sendOTP(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mm,true);
        msg.setTo(email);
        msg.setSubject("Your OTP code");
        String genOtp = Utilities.generateOTP();
//      store otp in db
        OTP otp = new OTP(email,genOtp, LocalDateTime.now());
// `    resend otp
        otpRepository.save(otp);
//        msg.setText("Your code is : "+genOtp,false);
        msg.setText(Data.getOtpMsgBody(genOtp,user.getName()),true);

        mailSender.send(mm);
        return true;
    }

    @Override
    public Boolean verifyOTP(String email,String otp) throws Exception {
        OTP otpEntity = otpRepository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));
        if(!otpEntity.getOtpCode().equals(otp)){
            throw new JobPortalException("OTP_INCORRECT");
        }
        return true;
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) throws Exception {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("Password changed successfully!");
    }

//    delete opt after expiration
    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs(){
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOTPs = otpRepository.findByCreationTimeBefore(expiry);
        if(!expiredOTPs.isEmpty()){
            otpRepository.deleteAll(expiredOTPs);
            System.out.println("Removed "+expiredOTPs.size()+" expired OTPs");
        }
//        System.out.println("Hello World");
    }

}
