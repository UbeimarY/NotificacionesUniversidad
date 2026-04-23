package com.universidad.notificaciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "notificaciones_email")
@DiscriminatorValue("EMAIL")
@PrimaryKeyJoinColumn(name = "notificacion_id")
public class EmailNotificacion extends Notificacion {

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false)
    private String emailDestinatario;

    private String nombreRemitente;
    private boolean tieneAdjunto;

    @Override
    public void enviar() {
        // Aquí iría la integración real (JavaMailSender, SendGrid, etc.)
        System.out.printf("📧 Enviando email a %s | Asunto: %s%n",
                emailDestinatario, asunto);
        this.setEstado(com.universidad.notificaciones.entity.enums.EstadoNotificacion.ENVIADA);
    }

    @Override
    public String getDescripcionMedio() {
        return "Correo electrónico";
    }
}