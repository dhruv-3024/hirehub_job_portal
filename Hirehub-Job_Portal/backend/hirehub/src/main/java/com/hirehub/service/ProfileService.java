package com.hirehub.service;

import com.hirehub.dto.ProfileDTO;

public interface ProfileService {
    Long createProfile(String email) throws Exception;
    ProfileDTO getProfile(Long id) throws Exception;
    ProfileDTO updateProfile(ProfileDTO profileDTO) throws Exception;
}
