# Sistema de Notificaciones - Universidad

API REST desarrollada con Java + Spring Boot + PostgreSQL.

## Tecnologías
- Java 17
- Spring Boot 4.0.5
- PostgreSQL 16
- Docker
- Maven

## Ejecutar el proyecto

### 1. Levantar base de datos
```bash
docker run --name postgres-notificaciones \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=987883an \
  -e POSTGRES_DB=notificaciones_db \
  -p 5432:5432 \
  -d postgres:16
```

### 2. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/notificaciones | Crear notificación |
| POST | /api/notificaciones/{id}/enviar | Enviar notificación |
| GET | /api/notificaciones | Listar todas |
| GET | /api/notificaciones/{id} | Buscar por ID |
| GET | /api/notificaciones/estado/{estado} | Filtrar por estado |
| GET | /api/notificaciones/tipo/{tipo} | Filtrar por tipo |

## Medios soportados
- `EMAIL` — Correo electrónico
- `SMS` — Mensaje de texto
- `APP` — Notificación móvil

## Tipos de notificación
- `PUBLICACION_CALIFICACIONES`
- `RECORDATORIO_PAGO`
- `CANCELACION_CLASE`
- `CONFIRMACION_INSCRIPCION`
