package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.AuthUserDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.NewUserDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.RequestDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto.TokenDto;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.entity.AuthUser;
import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto){
        TokenDto tokenDto = authService.login(authUserDto);
        if(authUserDto == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto requestDto){
        TokenDto tokenDto = authService.validate(token,requestDto);
        if(tokenDto == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
        AuthUser authUser = authService.save((dto));
        if(authUser == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authUser);
    }
}
