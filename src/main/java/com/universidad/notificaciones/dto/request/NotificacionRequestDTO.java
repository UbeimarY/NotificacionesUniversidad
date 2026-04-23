package com.universidad.notificaciones.dto.request;

import com.universidad.notificaciones.entity.enums.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionRequestDTO {

    @NotBlank
    private String destinatario;

    @NotBlank
    private String mensaje;

    @NotNull
    private TipoNotificacion tipo;

    // Medio: EMAIL, SMS, APP
    @NotBlank
    private String medio;

    // Campos específicos de Email
    private String asunto;
    private String emailDestinatario;
    private String nombreRemitente;
    private Boolean tieneAdjunto;

    // Campos específicos de SMS
    private String numeroTelefono;
    private String codigoPais;

    // Campos específicos de App
    private String tokenDispositivo;
    private String plataforma;
    private String icono;
    private String urlAccion;
}