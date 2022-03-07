package com.dgtt.business.config.impl;

import com.dgtt.business.authencation.CustomUserDetails;
import com.dgtt.core.constants.ApplicationConstant;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 *
 * @author DELL
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        if (null != SecurityContextHolder.getContext().getAuthentication() && getUsername()) {
            return Optional.ofNullable(((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername());
        }
        return Optional.ofNullable(System.getProperties().get("user.name").toString());

    }

    private boolean getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !username.equalsIgnoreCase(ApplicationConstant.ANONYMOUS);
    }

}
