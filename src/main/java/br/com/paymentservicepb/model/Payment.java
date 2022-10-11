package br.com.paymentservicepb.model;

import br.com.paymentservicepb.model.enums.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Length(min = 14, max = 16)
    private String card_number;

    @NotBlank
    private String cardholder_name;

    @Length(min = 3, max = 3)
    private String security_code;

    @Positive
    @Max(value = 12)
    private Integer expiration_month;

    @Positive
    @Min(value = 22)
    @Max(value = 99)
    private Integer expiration_year;

    @Enumerated(EnumType.STRING)
    private Brand brand;

}
