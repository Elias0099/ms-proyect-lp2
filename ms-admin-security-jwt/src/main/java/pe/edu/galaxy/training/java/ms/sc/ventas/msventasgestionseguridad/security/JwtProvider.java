package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.RequestDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.entity.AuthUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private RouteValidator routeValidator;

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser authUser){
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getUserName());
        claims.put("id",authUser.getId());
        claims.put("role",authUser.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean validate(String token, RequestDto requestDto){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception exception){
            return false;
        }
        if(!isAdmin(token) && routeValidator.isAdmin(requestDto)){
            return false;
        }
        return true;
    }

    public String getUserNameFromToken(String token){
        try{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception exception){
            return "Bad token";
        }
    }

    private boolean isAdmin(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
    }
}
