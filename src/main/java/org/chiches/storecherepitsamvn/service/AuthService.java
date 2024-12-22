package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsacontracs.dto.auth.LoginForm;
import org.chiches.storecherepitsacontracs.dto.user.UserForm;
import org.chiches.storecherepitsamvn.entity.UserEntity;

public interface AuthService {
    void registerUser(UserForm userForm);
}
