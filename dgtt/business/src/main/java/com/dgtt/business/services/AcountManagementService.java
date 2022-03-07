package com.dgtt.business.services;

import com.dgtt.business.authencation.CustomUserDetails;
import com.dgtt.business.authencation.JwtAuthenticationResponse;
import com.dgtt.business.model.account.*;
import com.dgtt.core.entity.User;
import com.dgtt.core.exception.BadRequestException;
import com.dgtt.core.exception.UnauthorizedException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 
 * @author sadfsafbhsaid
 */
public interface AcountManagementService {

    List<User> checkAlreadyUsernameExist(String username);

    List<User> checkAlreadyEmailExist(String email);

    List<User> checkAlreadyPhoneExist(String phoneNumber);

    UserBasicDto createNewAccount(UserRegisterDto userRegisterDto) throws BadRequestException;

    UserBasicDto updateAccount(UserUpdateDto userUpdateDto)
            throws BadRequestException;

    User deleteAccount(Long id);

    Page<UserBasicDto> searchAccount(String keyword,int pageNumber, int size, String sortByProperties, String sortBy,
            UserSearchDto userSearchDto);

    UserBasicDto getUserById(Long userId);

    User findUserById(Long userId);

    User findUserByUsername(String username);

    User save(User user);

    JwtAuthenticationResponse refreshToken(CustomUserDetails customUserDetails);

    JwtAuthenticationResponse login(UserLoginDto userLoginDto) throws UnauthorizedException;

}
