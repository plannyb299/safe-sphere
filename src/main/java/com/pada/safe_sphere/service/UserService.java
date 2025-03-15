package com.pada.safe_sphere.service;

import com.pada.safe_sphere.commons.AppConstants;
import com.pada.safe_sphere.entity.User;
import com.pada.safe_sphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public String registerAccount(User user) {

        if(userRepository.findByNationalId(user.getNationalId())!=null) {
            return "An account with this:"+ user.getNationalId() + "already exists";
        }else{
            userRepository.save(user);
            return AppConstants.REGISTRATION_SUCCESS;
        }
    }
}
