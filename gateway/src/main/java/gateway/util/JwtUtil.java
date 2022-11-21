package gateway.util;

import gateway.exception.JwtTokenMalformedException;
import gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final String JWT_TOKEN_SECRET = "aphrinixjwtsecret";


    public Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(JWT_TOKEN_SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String email) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 120);
        return Jwts
                .builder()
                .setSubject(email)
                .setExpiration(calendar.getTime())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, JWT_TOKEN_SECRET)
                .compact();
    }

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_TOKEN_SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }


}
