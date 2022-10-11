package br.com.paymentservicepb.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class Utils {

    public Integer getTimeNow(){
        int horaAtual = LocalDateTime.now().getHour();
        int minutoAtual = LocalDateTime.now().getMinute();
        int segundoAtual = LocalDateTime.now().getSecond();
        return  (horaAtual * 60 * 60) + (minutoAtual *60) + segundoAtual;
    }


    public Boolean compareTime(Integer timeExpired){
        int timeDif = getTimeNow() - timeExpired;

        if(timeDif > 250 || timeDif < - 250){
            return false;
        }

        return timeExpired > getTimeNow();
    }

    @Bean
    public ModelMapper obterModelMapper(){
        return new ModelMapper();
    }
}
