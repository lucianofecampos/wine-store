package com.htecweb.winestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Wine {

    @ApiModelProperty(hidden = true)
    @Id
    @SequenceGenerator(name = "wine_seq", sequenceName = "wine_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_seq")
    @Include
    private Long id;

    @NotBlank(message = "wines-1")
    private String name;

    @NotNull(message = "wines-2")
    private WineType type;

    @NotNull(message = "wines-3")
    @DecimalMin(value = "0", message = "wines-4")
    private BigDecimal volume;

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }
}
