package com.eregister.SecurityService.Token;

import com.eregister.SecurityService.Model.JwtAuthority;
import com.eregister.SecurityService.Model.JwtUserDetails;
import com.eregister.UserService.Entity.EregUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Szymon on 07.04.2017.
 */
@Component
public class TokenUtils {

    //@Value("${jwt.secret}")
    static private String secret = "eregpass";

    //@Value("${jwt.expiration}")
    static private Long expiration = 604800L;

    public static String generateToken(EregUser eregUser) throws UnsupportedEncodingException {
        String id = Integer.toString(eregUser.getId());
        String login = eregUser.getLogin();
        String roles = eregUser.getRoles();

        String jwt = Jwts.builder()
                .setExpiration(generateExpirationDate())
                .claim("login", login)
                .claim("roles", roles)
                .claim("id",id)
                .signWith(
                        SignatureAlgorithm.HS256,
                        secret
                )
                .compact();

        return jwt;
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public static Claims verifyToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public static UserDetails userDetailsFromToken(String token){
        Claims claims = verifyToken(token);
        UserDetails userDetails = new JwtUserDetails(claims);
        return userDetails;
    }

    public static String getLoginFromToken(String token){
        UserDetails userDetails = userDetailsFromToken(token);
        String login = userDetails.getUsername();
        return login;
    }

}
