package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.Membership;
import org.fireballs.alfaballs.extern.assembler.shortcut.ProjectShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.UserController;
import org.fireballs.alfaballs.extern.dto.MembershipDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MembershipAssembler extends RepresentationModelAssemblerSupport<Membership, MembershipDto> {

    public MembershipAssembler(ProjectShortcutAssembler projectShortcutAssembler) {
        super(UserController.class, MembershipDto.class);
    }

    @Override
    public MembershipDto toModel(Membership entity) {
        var dto = new MembershipDto();

        dto.setId(entity.getUser().getId());
        dto.setFullName(entity.getUser().getName());
        dto.setEmail(entity.getUser().getEmail());
        dto.setAvatar(entity.getUser().getAvatar() == null ? null :
                Base64.getEncoder().encodeToString(entity.getUser().getAvatar()));
        dto.setRole(entity.getRole().name());

        return dto;
    }
}
