package com.universidad.notificaciones.entity;

import com.universidad.notificaciones.entity.enums.EstadoNotificacion;
import com.universidad.notificaciones.entity.enums.TipoNotificacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notificaciones")
@DiscriminatorColumn(name = "medio", discriminatorType = DiscriminatorType.STRING)
public abstract class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNotificacion estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    // Polimorfismo: cada subclase implementa su propio envío
    public abstract void enviar();

    public abstract String getDescripcionMedio();

    public String getDetalles() {
        return String.format("Notificación [%s] vía %s - Estado: %s",
                codigo, getDescripcionMedio(), estado);
    }
}