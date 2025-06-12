package org.fireballs.alfaballs.extern.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class TypeDto extends RepresentationModel<TypeDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Long id;

    @NotBlank(groups = {PostPutGroup.class, Default.class})
    @Size(max = 20)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean common;
}
