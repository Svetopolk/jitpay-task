package com.svetopolk.demo.service;

import com.svetopolk.demo.dto.UserDataRequest;
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

    public User save(UserDataRequest userDataRequest) {
        var user = new User(userDataRequest.userId(), userDataRequest.email(), userDataRequest.firstName(), userDataRequest.secondName());
        return userRepository.save(user);
    }

    public User get(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
