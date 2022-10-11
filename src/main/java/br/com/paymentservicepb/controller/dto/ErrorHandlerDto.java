package br.com.paymentservicepb.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorHandlerDto {

        private String campo;
        private String mensagem;

}