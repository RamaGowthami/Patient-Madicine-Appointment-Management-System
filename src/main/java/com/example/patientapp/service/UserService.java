package com.example.patientapp.service;

import com.example.patientapp.model.User;
import com.example.patientapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
/*
package com.example.patientapp.service;

import com.example.patientapp.model.User;
import com.example.patientapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Ensure the class is annotated as @Service so that Spring can inject it
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
        //userRepository.save(user);  // Save the user to the database
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Find user by username
    }
}*/
