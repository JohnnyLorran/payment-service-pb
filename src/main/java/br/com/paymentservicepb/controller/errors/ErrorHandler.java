package br.com.paymentservicepb.controller.errors;

import br.com.paymentservicepb.controller.dto.ErrorHandlerDto;
import br.com.paymentservicepb.controller.dto.ExceptionHandlerDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<ErrorHandlerDto> handle( MethodArgumentNotValidException exception){
            List<ErrorHandlerDto> dto = new ArrayList<>();
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

            fieldErrors.forEach(e -> {
                String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale() );
                ErrorHandlerDto erro = new ErrorHandlerDto(e.getField(),mensagem);
                dto.add(erro);
            });

            return dto;
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ErrorHandlerDto handleInvalid(InvalidFormatException e) {
        String campo = e.getTargetType().toString().substring(42);

        String mensagem = switch (campo) {
            case "Brand" -> "Bandeiras válidas: [ELO, VISA, MASTERCARD, AMERICAN_EXPRESS]";
            case "CurrencyType" -> "O tipo de moeda válido é 'BRL' ";
            case "PaymentType" -> "O tipo de pagamento aceito é 'CREDIT_CARD' ";
            default -> e.getMessage();
        };

        return new ErrorHandlerDto(campo,mensagem);
    }


    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    public ExceptionHandlerDto handleInternal(HttpClientErrorException.Forbidden e) {
            return new ExceptionHandlerDto("Token", "Token expirado");
        }


}