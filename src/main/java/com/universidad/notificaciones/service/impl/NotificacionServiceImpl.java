package com.universidad.notificaciones.service.impl;

import com.universidad.notificaciones.dto.request.NotificacionRequestDTO;
import com.universidad.notificaciones.dto.response.NotificacionResponseDTO;
import com.universidad.notificaciones.entity.*;
import com.universidad.notificaciones.entity.enums.EstadoNotificacion;
import com.universidad.notificaciones.entity.enums.TipoNotificacion;
import com.universidad.notificaciones.exception.RecursoNoEncontradoException;
import com.universidad.notificaciones.repository.NotificacionRepository;
import com.universidad.notificaciones.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repository;

    @Override
    @Transactional
    public NotificacionResponseDTO crear(NotificacionRequestDTO dto) {
        Notificacion notificacion = fabricar(dto);
        notificacion.setCodigo("NOT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        notificacion.setFechaEnvio(LocalDateTime.now());
        notificacion.setEstado(EstadoNotificacion.PENDIENTE);
        notificacion.setTipo(dto.getTipo());
        notificacion.setDestinatario(dto.getDestinatario());
        notificacion.setMensaje(dto.getMensaje());

        return toDTO(repository.save(notificacion));
    }

    @Override
    @Transactional
    public NotificacionResponseDTO enviar(Long id) {
        Notificacion n = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Notificación no encontrada: " + id));
        try {
            n.enviar(); // ← Polimorfismo en acción
        } catch (Exception e) {
            n.setEstado(EstadoNotificacion.FALLIDA);
        }
        return toDTO(repository.save(n));
    }

    @Override
    public NotificacionResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Notificación no encontrada: " + id));
    }

    @Override
    public List<NotificacionResponseDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponseDTO> listarPorEstado(String estado) {
        return repository.findByEstado(EstadoNotificacion.valueOf(estado.toUpperCase()))
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponseDTO> listarPorTipo(String tipo) {
        return repository.findByTipo(TipoNotificacion.valueOf(tipo.toUpperCase()))
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ── Fábrica (oculta la decisión de qué subclase instanciar) ──
    private Notificacion fabricar(NotificacionRequestDTO dto) {
        return switch (dto.getMedio().toUpperCase()) {
            case "EMAIL" -> {
                EmailNotificacion e = new EmailNotificacion();
                e.setAsunto(dto.getAsunto());
                e.setEmailDestinatario(dto.getEmailDestinatario());
                e.setNombreRemitente(dto.getNombreRemitente());
                e.setTieneAdjunto(Boolean.TRUE.equals(dto.getTieneAdjunto()));
                yield e;
            }
            case "SMS" -> {
                SMSNotificacion s = new SMSNotificacion();
                s.setNumeroTelefono(dto.getNumeroTelefono());
                s.setCodigoPais(dto.getCodigoPais() != null ? dto.getCodigoPais() : "+57");
                s.setLongitud(dto.getMensaje().length());
                s.setEsMensajeMultiparte(dto.getMensaje().length() > 160);
                yield s;
            }
            case "APP" -> {
                AppNotificacion a = new AppNotificacion();
                a.setTokenDispositivo(dto.getTokenDispositivo());
                a.setPlataforma(dto.getPlataforma());
                a.setIcono(dto.getIcono());
                a.setUrlAccion(dto.getUrlAccion());
                yield a;
            }
            default -> throw new IllegalArgumentException("Medio no soportado: " + dto.getMedio());
        };
    }

    private NotificacionResponseDTO toDTO(Notificacion n) {
        return NotificacionResponseDTO.builder()
                .id(n.getId())
                .codigo(n.getCodigo())
                .destinatario(n.getDestinatario())
                .mensaje(n.getMensaje())
                .fechaEnvio(n.getFechaEnvio())
                .estado(n.getEstado().name())
                .tipo(n.getTipo().name())
                .medio(n.getDescripcionMedio())
                .detalles(n.getDetalles())
                .build();
    }
}