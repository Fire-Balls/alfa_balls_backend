package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.extern.controller.TypeController;
import org.fireballs.alfaballs.extern.dto.newdtos.TypeDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TypeAssembler extends RepresentationModelAssemblerSupport<Type, TypeDto> {
    public TypeAssembler() {
        super(TypeController.class, TypeDto.class);
    }

    @Override
    public TypeDto toModel(Type entity) {
        TypeDto typeDto = instantiateModel(entity);

        typeDto.setId(entity.getId());
        typeDto.setName(entity.getName());
        typeDto.setCommon(entity.isDefault());

        return typeDto;
    }

    public Type toEntity(TypeDto dto) {
        return Type.builder()
                .id(dto.getId())
                .name(dto.getName())
                .isDefault(dto.isCommon())
                .build();
    }
}
