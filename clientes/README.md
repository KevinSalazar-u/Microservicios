# Microservicio 1: Clientes generado con IA (Skill)

## Skill / prompt utilizado

Prompt usado en ChatGPT:

> Genera un microservicio REST en Spring Boot basado en un proyecto existente de ventas. Debe implementar una entidad Cliente con nombre, email, teléfono, dirección, estado activo y fecha de registro. La API debe tener GET listar con paginación, GET por ID, GET por nombre o email, POST, PUT y DELETE. Debe usar Spring Data JPA, MySQL, arquitectura por capas: controller, service, service implementation, repository, entity y manejo básico de errores. El código debe compilar y estar listo para ejecutar en IntelliJ IDEA o Spring Tool Suite.

## Base de datos

Crear la base de datos en MySQL/phpMyAdmin:

```sql
CREATE DATABASE IF NOT EXISTS ventas;
USE ventas;
```

Hibernate crea la tabla `clientes` automáticamente porque está configurado:

```properties
spring.jpa.hibernate.ddl-auto=update
```

## Configuración principal

Archivo: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ventas
spring.datasource.username=root
spring.datasource.password=
server.port=8081
```

Si tu MySQL tiene contraseña, colócala en `spring.datasource.password`.

## Endpoints REST

URL base:

```text
http://localhost:8081/api/clientes
```

### 1. GET listar con paginación

```http
GET http://localhost:8081/api/clientes?page=0&size=5
```

También acepta ordenamiento:

```http
GET http://localhost:8081/api/clientes?page=0&size=5&sortBy=nombre&direction=asc
```

### 2. GET por ID

```http
GET http://localhost:8081/api/clientes/1
```

### 3. GET por nombre o email

```http
GET http://localhost:8081/api/clientes/buscar?valor=juan
```

```http
GET http://localhost:8081/api/clientes/buscar?valor=juan@gmail.com
```

### 4. POST crear cliente

```http
POST http://localhost:8081/api/clientes
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@gmail.com",
  "telefono": "0999999999",
  "direccion": "Quito - Ecuador",
  "activo": true
}
```

### 5. PUT actualizar cliente

```http
PUT http://localhost:8081/api/clientes/1
Content-Type: application/json

{
  "nombre": "Juan Pérez Actualizado",
  "email": "juan.actualizado@gmail.com",
  "telefono": "0988888888",
  "direccion": "Guayaquil - Ecuador",
  "activo": true
}
```

### 6. DELETE eliminar cliente

```http
DELETE http://localhost:8081/api/clientes/1
```

## Cómo ejecutar

En terminal, dentro del proyecto:

```bash
./gradlew bootRun
```

En Windows:

```bash
gradlew.bat bootRun
```

También puedes ejecutarlo desde la clase:

```text
ClientesIaApplication.java
```

## Evidencia de requisitos

| Requisito | Cumplimiento |
|---|---|
| Uso de skill estructurado | Incluido en este README |
| API REST completa | Sí |
| GET listar | `/api/clientes` |
| GET by ID | `/api/clientes/{id}` |
| GET by name/email | `/api/clientes/buscar?valor=` |
| POST | `/api/clientes` |
| PUT | `/api/clientes/{id}` |
| DELETE | `/api/clientes/{id}` |
| Compilación/ejecución | Proyecto Gradle Spring Boot |
| Paginación | Parámetros `page`, `size`, `sortBy`, `direction` |
