package com.svetopolk.demo.service;

import com.svetopolk.demo.dto.UserInfoRequest;
import com.svetopolk.demo.entity.User;
import com.svetopolk.demo.exception.UserNotFoundException;
import com.svetopolk.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserInfoRequest userInfoRequest) {
        var user = new User(userInfoRequest.userId(), userInfoRequest.email(), userInfoRequest.firstName(), userInfoRequest.secondName());
        return userRepository.save(user);
    }

    public User get(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
