package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {
    private final UsersMapper usersMapper;
    private final CredentialsMapper credentialsMapper;

    public CredentialsService(UsersMapper usersMapper, CredentialsMapper credentialsMapper) {
        this.usersMapper = usersMapper;
        this.credentialsMapper = credentialsMapper;
    }

    public void addCredentials(Credentials credentials ) {
        credentialsMapper.insert(credentials);
    }

    public List<Credentials> getAllCredential(Integer userid) {
        return credentialsMapper.getUserCredentials(userid);
    }

    public Credentials getCredential(Integer credentialid) {
        return credentialsMapper.getCredentials(credentialid);
    }

    public void deleteCredential(Integer credentialid) {
        credentialsMapper.deleteCredentials(credentialid);
    }

    public void updateCredentials(Credentials credentials) {
        credentialsMapper.updateCredentials(credentials);
    }
}
