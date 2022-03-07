package com.dgtt.business.authencation;

import com.dgtt.core.config.ApplicationConfig.MessageSourceVi;
import com.dgtt.core.constants.StatusEnum;
import com.dgtt.core.entity.Role;
import com.dgtt.core.entity.User;
import com.dgtt.core.entity.UserRole;
import com.dgtt.core.exception.ResourceNotFoundException;
import com.dgtt.core.repository.UserRepository;
import com.dgtt.core.utils.Constants;
import com.dgtt.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 *
 * @author Nguyen_Toan
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSourceVi messageSourceVi;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CustomUserDetails loadUserById(Long id) throws ResourceNotFoundException {
//        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(
//                messageSourceVi.getMessageVi("USERNAME_NOT_FOUND_MSG") + id));
        User user = userRepository.findByUserIdAndIsDelete(id, Constants.DELETE.NORMAL);

        if (null == user) {
            throw new UsernameNotFoundException(
                    messageSourceVi.getMessageVi("USERNAME_NOT_FOUND_MSG"));
        }
        HashSet<Role> rolesOfUser = new HashSet<>();
        user.getUserRoleList().stream().forEach((UserRole udr) -> {
            rolesOfUser.add(udr.getRole());
        });
        return new CustomUserDetails(user, user.getFullName(), rolesOfUser);
    }

    @Transactional
    public CustomUserDetails loadCustomUserByUsername(String username, String roleCode) {
        User user = userRepository.findByUserName(username, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);

        if (null == user) {
            throw new UsernameNotFoundException(
                    messageSourceVi.getMessageVi("USERNAME_NOT_FOUND_MSG"));
        }

        HashSet<Role> rolesOfUser = new HashSet<>();
//        user.getUserRoleList().stream().map(UserDeptRole::getRole).forEach((Role r) -> {
//            if (r.getRoleCode().equalsIgnoreCase(roleCode)) {
//                rolesOfUser.add(r);
//            }
//        });

        user.getUserRoleList().stream().forEach((UserRole udr) -> {
            if (udr.getRole().getRoleCode().equalsIgnoreCase(roleCode)) {
                rolesOfUser.add(udr.getRole());
            }
        });
        return new CustomUserDetails(user, user.getFullName(), rolesOfUser);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
        if (null == user) {
            throw new UsernameNotFoundException(
                    messageSourceVi.getMessageVi("USERNAME_NOT_FOUND_MSG"));
        }
        if (!StringUtils.isTrue(user.getPassword())) {
            user.setCheckPassword(passwordEncoder.encode(username));
        }

        HashSet<Role> rolesOfUser = new HashSet<>();
        user.getUserRoleList().stream().map(UserRole::getRole).forEach(rolesOfUser::add);

        return new CustomUserDetails(user, user.getFullName(), rolesOfUser);
    }

}
