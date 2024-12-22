package org.chiches.storecherepitsamvn.service;

import org.chiches.storecherepitsamvn.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO findById(Long id);
}
