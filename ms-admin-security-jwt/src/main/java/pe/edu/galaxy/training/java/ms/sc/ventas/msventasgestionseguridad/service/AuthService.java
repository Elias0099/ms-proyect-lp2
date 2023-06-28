package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.AuthUserDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.NewUserDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.RequestDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.TokenDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.entity.AuthUser;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.repository.AuthUserRepository;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.security.JwtProvider;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(NewUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(dto.getPassword(),user.get().getPassword())){
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        return null;
    }

    public TokenDto validate(String token, RequestDto requestDto){
        if(!jwtProvider.validate(token,requestDto)){
            return null;
        }
        String userName = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByUserName(userName).isPresent()){
            return null;
        }
        return new TokenDto(token);
    }
}
