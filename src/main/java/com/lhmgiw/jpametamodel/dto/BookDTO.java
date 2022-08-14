package com.lhmgiw.jpametamodel.dto;

import com.lhmgiw.jpametamodel.enums.StatusEnum;
import com.lhmgiw.jpametamodel.validator.BlankValidator;
import com.lhmgiw.jpametamodel.validator.StatusValidator;
import com.lhmgiw.jpametamodel.validator.group.ValidatorOne;
import com.lhmgiw.jpametamodel.validator.group.ValidatorTwo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    @BlankValidator(message = "field.not.edit", groups = {ValidatorOne.class, ValidatorTwo.class})
    private Long id;

    @NotEmpty(message = "field.not.empty", groups = ValidatorOne.class)
    @BlankValidator(message = "field.not.edit", groups = ValidatorTwo.class)
    @Length(max = 20, message = "field.length.not.valid", groups = {ValidatorOne.class})
    private String code;

    @NotEmpty(message = "field.not.empty", groups = {ValidatorOne.class, ValidatorTwo.class})
    @Length(max = 100, message = "field.length.not.valid", groups = {ValidatorOne.class, ValidatorTwo.class})
    private String name;

    @NotEmpty(message = "field.not.empty", groups = {ValidatorOne.class, ValidatorTwo.class})
    @Length(max = 150, message = "field.length.not.valid", groups = {ValidatorOne.class, ValidatorTwo.class})
    private String author;

    @NotNull(message = "field.not.empty", groups = {ValidatorOne.class, ValidatorTwo.class})
    @DecimalMin(value = "0.00", inclusive = false, message = "field.not.zero", groups = {ValidatorOne.class, ValidatorTwo.class})
    @Digits(integer = 8, fraction = 2, message = "field.length.not.valid", groups = {ValidatorOne.class, ValidatorTwo.class})
    private BigDecimal price;

    @NotNull(message = "field.not.empty", groups = {ValidatorOne.class, ValidatorTwo.class})
    @StatusValidator(anyOf = {StatusEnum.ACTIVE, StatusEnum.INACTIVE}, message = "status.field.not.valid", groups = {ValidatorOne.class, ValidatorTwo.class})
    private String status;
}
