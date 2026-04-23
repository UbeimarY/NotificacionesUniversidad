package com.universidad.notificaciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "notificaciones_sms")
@DiscriminatorValue("SMS")
@PrimaryKeyJoinColumn(name = "notificacion_id")
public class SMSNotificacion extends Notificacion {

    @Column(nullable = false)
    private String numeroTelefono;

    private String codigoPais;
    private int longitud;
    private boolean esMensajeMultiparte;

    @Override
    public void enviar() {
        System.out.printf("📱 Enviando SMS al %s%s | Longitud: %d chars%n",
                codigoPais, numeroTelefono, longitud);
        this.setEstado(com.universidad.notificaciones.entity.enums.EstadoNotificacion.ENVIADA);
    }

    @Override
    public String getDescripcionMedio() {
        return "Mensaje de texto (SMS)";
    }

    public String getNumeroCompleto() {
        return codigoPais + numeroTelefono;
    }
}