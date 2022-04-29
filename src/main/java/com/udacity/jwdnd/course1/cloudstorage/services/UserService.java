package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UsersMapper usersMapper;
    private final HashService hashService;

    public UserService(UsersMapper usersMapper, HashService hashService) {
        this.usersMapper = usersMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return usersMapper.getUser(username) == null;
    }

    public int createUser(Users users) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(users.getPassword(), encodedSalt);
        return usersMapper.insert(new Users(null, users.getUsername(), encodedSalt, hashedPassword, users.getFirstname(), users.getLastname()));
    }

    public Users getUser(String username) {
        return usersMapper.getUser(username);
    }
}
