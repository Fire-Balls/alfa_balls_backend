package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.controller.UserController;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {
    public UserAssembler() {
        super(UserController.class, UserDto.class);
    }

    //todo RoleAssembler и разобраться с ролями
    @Override
    public UserDto toModel(User entity) {
        UserDto userDto = instantiateModel(entity);

        userDto.setId(entity.getId());
        userDto.setFullName(entity.getName());
        userDto.setEmail(entity.getEmail());
        //userDto.setRoles(entity.getRole());
        userDto.setAvatar(Base64.getEncoder().encodeToString(entity.getAvatar()));

        return userDto;
    }
}
