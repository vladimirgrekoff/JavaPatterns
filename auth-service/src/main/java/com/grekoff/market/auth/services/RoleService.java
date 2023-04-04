package com.grekoff.market.auth.services;

import com.grekoff.market.auth.repositories.RoleRepository;
import com.grekoff.market.auth.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}