package com.finance.financecontrol.services;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticatedUserService {
    String getCurrentUserId();
}
