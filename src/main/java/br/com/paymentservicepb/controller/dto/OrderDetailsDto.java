package br.com.paymentservicepb.controller.dto;

import br.com.paymentservicepb.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long order_id;

    private BigDecimal total;

    private String payment_id;

    private PaymentStatus payment_status;

    private String message;

}
