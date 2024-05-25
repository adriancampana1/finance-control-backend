package com.finance.financecontrol.services;

import com.finance.financecontrol.domain.user.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServiceImplements implements AuthenticatedUserService {
    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            User userId = (User) authentication.getPrincipal();
            return (userId.getId());
        }
        return null;
    }
}
