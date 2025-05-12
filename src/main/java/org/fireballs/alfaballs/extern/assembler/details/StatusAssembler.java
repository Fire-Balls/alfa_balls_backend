package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.Status;
import org.fireballs.alfaballs.extern.controller.StatusController;
import org.fireballs.alfaballs.extern.dto.newdtos.StatusDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class StatusAssembler extends RepresentationModelAssemblerSupport<Status, StatusDto> {
    public StatusAssembler() {
        super(StatusController.class, StatusDto.class);
    }

    @Override
    public StatusDto toModel(Status entity) {
        StatusDto statusDto = instantiateModel(entity);

        statusDto.setId(entity.getId());
        statusDto.setName(entity.getName());
        statusDto.setCommon(entity.isDefault());

        return statusDto;
    }

    public Status toEntity(StatusDto dto) {
        return Status.builder()
                .id(dto.getId())
                .name(dto.getName())
                .isDefault(dto.isCommon())
                .build();
    }
}
