package com.universidad.notificaciones.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificacionResponseDTO {
    private Long id;
    private String codigo;
    private String destinatario;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String estado;
    private String tipo;
    private String medio;
    private String detalles;
}