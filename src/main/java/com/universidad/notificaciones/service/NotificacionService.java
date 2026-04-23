package com.universidad.notificaciones.service;

import com.universidad.notificaciones.dto.request.NotificacionRequestDTO;
import com.universidad.notificaciones.dto.response.NotificacionResponseDTO;
import java.util.List;

public interface NotificacionService {
    NotificacionResponseDTO crear(NotificacionRequestDTO dto);
    NotificacionResponseDTO enviar(Long id);
    NotificacionResponseDTO buscarPorId(Long id);
    List<NotificacionResponseDTO> listarTodas();
    List<NotificacionResponseDTO> listarPorEstado(String estado);
    List<NotificacionResponseDTO> listarPorTipo(String tipo);
}

