package com.hirehub.api;

import com.hirehub.dto.ProfileDTO;
import com.hirehub.dto.ResponseDTO;
import com.hirehub.service.ProfileService;
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
@RequestMapping("/profiles")
public class ProfileAPI {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/getProfile/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws Exception { //for JSON obj, web req body
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws Exception { //for JSON obj, web req body
        return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);

    }
}
