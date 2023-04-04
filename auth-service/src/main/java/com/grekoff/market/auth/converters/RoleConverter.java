package com.grekoff.market.auth.converters;

import com.grekoff.market.api.auth.RoleDto;
import com.grekoff.market.auth.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public Role dtoToEntity(RoleDto roleDto) {
        return new Role(roleDto.getId(), roleDto.getName());
    }

    public RoleDto entityToDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}
