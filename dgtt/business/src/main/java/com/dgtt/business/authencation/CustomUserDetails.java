package com.dgtt.business.authencation;

import com.dgtt.core.constants.StatusEnum;
import com.dgtt.core.entity.Role;
import com.dgtt.core.entity.User;
import lombok.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Nguyen_Toan
 */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Transactional
@Log4j
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;
    private String fullName;
    private Set<Role> roleOfUser;
    private String session;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        roleOfUser.stream().map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                .forEach(authorities::add);
//        if (!StringUtils.isTrue(authorities)) {
//                throw new AccessDeniedException("Tài khoản chưa được phân quyền vào hệ thống.");
//        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if (user.getCheckPassword() != null) {
            return user.getCheckPassword();
        }
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().equals(StatusEnum.LOCK.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(StatusEnum.ACTIVE.getStatus());
    }

    public CustomUserDetails(User user, String fullName, Set<Role> roleOfUser) {
        this.user = user;
        this.fullName = fullName;
        this.roleOfUser = roleOfUser;
    }
}
