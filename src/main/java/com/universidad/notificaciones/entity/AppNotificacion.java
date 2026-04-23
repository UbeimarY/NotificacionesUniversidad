package com.universidad.notificaciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "notificaciones_app")
@DiscriminatorValue("APP")
@PrimaryKeyJoinColumn(name = "notificacion_id")
public class AppNotificacion extends Notificacion {

    @Column(nullable = false)
    private String tokenDispositivo;

    private String plataforma; // ANDROID, IOS
    private String icono;
    private String urlAccion;

    @Override
    public void enviar() {
        System.out.printf("🔔 Push notification → Token: %s | Plataforma: %s%n",
                tokenDispositivo, plataforma);
        this.setEstado(com.universidad.notificaciones.entity.enums.EstadoNotificacion.ENVIADA);
    }

    @Override
    public String getDescripcionMedio() {
        return "Notificación en app móvil (" + plataforma + ")";
    }
}