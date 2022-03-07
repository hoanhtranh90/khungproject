package com.dgtt.business.mapper;

import com.dgtt.business.model.account.UserRegisterDto;
import com.dgtt.core.entity.User;

public class MapperObject {

    private MapperObject() {

    }

    public static User userBuilder(UserRegisterDto userDto) {
        return User.builder().email(userDto.getEmail())
                .fullName(userDto.getFullName())
                .sex(userDto.getSex())
                .phoneNumber(userDto.getPhoneNumber()).avatar(userDto.getAvatar())
                .userName(userDto.getUserName())
                .dateOfBirth(userDto.getDateOfBirth())
                .address(userDto.getAddress()).build();
    }
}
