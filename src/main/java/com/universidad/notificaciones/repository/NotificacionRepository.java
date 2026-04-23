package com.universidad.notificaciones.repository;

import com.universidad.notificaciones.entity.Notificacion;
import com.universidad.notificaciones.entity.enums.EstadoNotificacion;
import com.universidad.notificaciones.entity.enums.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByCodigo(String codigo);
    List<Notificacion> findByEstado(EstadoNotificacion estado);
    List<Notificacion> findByTipo(TipoNotificacion tipo);
    List<Notificacion> findByDestinatario(String destinatario);
}