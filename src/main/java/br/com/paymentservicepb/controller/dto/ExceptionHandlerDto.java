package br.com.paymentservicepb.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionHandlerDto {

        private String erro;
        private String mensagem;

}