package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionseguridad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestDto {

    private String uri;
    private String method;

}
