# Gestor de Torneos

Aplicación de escritorio desarrollada en JavaFX para la gestión de eventos y torneos deportivos.  
El sistema permite a promotores crear y administrar eventos, mientras que los participantes pueden inscribirse y consultar información de los torneos activos.

---

# Autor

Desarrollado por: JOSE A. CASTILLERO DÍAZ

---

# Tecnologías utilizadas

- Java 21
- JavaFX 21
- MySQL
- JDBC
- Maven
- SceneBuilder

---

# Funcionalidades principales

## Participantes
- Registro e inicio de sesión
- Visualización de eventos activos
- Inscripción y cancelación de inscripción
- Consulta de participantes apuntados
- Visualización de sus eventos inscritos

## Promotores
- Creación de eventos
- Edición de eventos
- Eliminación de eventos
- Gestión del estado del evento:
    - ACTIVO
    - INACTIVO

---

# Estructura del proyecto

```text
src/
 ├── controller/
 ├── DAO/
 ├── model/
 ├── util/
 ├── vista/
 └── dataAccess/
