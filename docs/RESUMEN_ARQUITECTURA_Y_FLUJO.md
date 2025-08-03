# Documentación del Proyecto pruebaSwing04

## 1. Arquitectura General
El proyecto sigue el patrón MVC (Modelo-Vista-Controlador) usando Java Swing para la interfaz gráfica y JDBC para la persistencia de datos. La estructura principal es:

- **Modelo:** Entidades Java que representan las tablas de la base de datos y repositorios para acceso a datos.
- **Vista:** Paneles y diálogos Swing para interacción con el usuario.
- **Controlador:** Clases que validan y coordinan la lógica entre la vista y el modelo.

## 2. Estructura de Carpetas
- `model/entity/` — Entidades Java (por ejemplo, `Usuario`, `TipoUsuario`).
- `model/repository/` — Repositorios genéricos y específicos para CRUD y consultas.
- `controller/` — Controladores genéricos y específicos para cada entidad.
- `view/PrincipalDlg.java` — Ventana principal (`JFrame`).
- `view/Administracion/` — Paneles de administración (`JPanel`) para cada entidad.
- `view/Tools/` — Componentes reutilizables y diálogos modales base.

## 3. Flujo de la Aplicación
1. El usuario interactúa con la ventana principal (`PrincipalDlg`).
2. Desde el menú, accede a paneles de administración como `TipoUsuarioDLG` o `UsuarioDLG`.
3. Estos paneles heredan de `BaseDLG` y muestran una tabla con los registros y botones CRUD.
4. Al agregar o editar, se abre un diálogo modal (`TipoUsuarioModalDlg`, `UsuarioModalDlg`) que hereda de `BaseModelDLG` para capturar datos.
5. Los controladores validan los datos y usan los repositorios para guardar, actualizar o eliminar en la base de datos.

## 4. Repositorios y Conexión a Base de Datos
- Los repositorios heredan de una clase genérica `Repository<T>`, que construye dinámicamente los queries SQL según el número de columnas de la tabla.
- El segundo parámetro del constructor de cada repositorio es el número de columnas (sin contar el id autoincremental).
- La clase `MiConexion` gestiona la conexión a la base de datos (SQLite o MariaDB) usando el patrón Singleton.
- Es importante cerrar la conexión después de cada operación para evitar fugas de recursos.

## 5. Controladores
- Los controladores heredan de una clase genérica y validan los datos antes de guardar o actualizar.
- Cada controlador tiene un método `validate` personalizado para su entidad.

## 6. Vistas y Componentes Reutilizables
- `BaseDLG` y `BaseModelDLG` permiten reutilizar la lógica y el diseño de los paneles y diálogos.
- Los paneles muestran tablas y botones CRUD; los diálogos modales capturan datos para agregar o editar.

## 7. Diagrama de Flujo de Ventanas
Según el diagrama en el pizarrón:
- `PrincipalDlg` abre paneles de administración.
- Los paneles abren diálogos modales para agregar/editar.
- Los modales interactúan con la base de datos y actualizan la tabla principal.

## 8. Buenas Prácticas y Aprendizajes
- Usar herencia y genéricos para evitar duplicar código.
- Separar la lógica de validación en los controladores.
- Cerrar siempre la conexión a la base de datos.
- Reutilizar componentes visuales para mantener la coherencia y facilitar el mantenimiento.

---

**Siguiente paso:** Implementar los diálogos modales para agregar/editar entidades y conectar completamente la vista con el modelo y el controlador.
