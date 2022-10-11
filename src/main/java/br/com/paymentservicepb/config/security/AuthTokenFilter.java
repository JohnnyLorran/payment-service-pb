package br.com.paymentservicepb.config.security;

import br.com.paymentservicepb.utils.Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private Integer timeExpired = 0;
    private String tokem = "";
    private final Utils utils = new Utils();

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        if( token != null){
            if(!token.equals(tokem)){
                tokem = token;
                timeExpired = 180 + utils.getTimeNow();
            }
            if( utils.compareTime(timeExpired)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null,null,null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token =  request.getHeader("Authorization");
        if(token == null  || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7,token.length());
    }
}