# ¿Qué es la View en MVC y cómo se relaciona con Controller y Model?

## 1. ¿Qué es la View?
La **View** (Vista) es la parte de la aplicación responsable de mostrar la información al usuario y de recibir sus interacciones (clics, entradas de texto, etc.). En aplicaciones Java Swing, la View está compuesta por ventanas, paneles, tablas, botones y formularios.

- Su función principal es la **interfaz gráfica**.
- No contiene lógica de negocio ni accede directamente a la base de datos.
- Solo muestra datos y envía las acciones del usuario al Controller.

## 2. Relación con el Controller
- La View **no procesa datos** ni toma decisiones de negocio.
- Cuando el usuario realiza una acción (por ejemplo, presiona un botón), la View notifica al **Controller**.
- El Controller interpreta la acción, valida los datos y decide qué hacer (por ejemplo, guardar, actualizar, eliminar o consultar información).
- El Controller puede actualizar el Model y luego pedirle a la View que refresque la información mostrada.

## 3. Relación con el Model
- La View **no accede directamente** al Model (los datos o la base de datos).
- El Model representa los datos y la lógica de negocio (por ejemplo, las entidades y los repositorios).
- El Controller es el intermediario: recibe acciones de la View, manipula el Model y actualiza la View según sea necesario.

## 4. Ejemplo de flujo
1. El usuario hace clic en "Agregar" en la View.
2. La View llama al método correspondiente del Controller.
3. El Controller valida los datos y usa el Model para guardar la información.
4. El Controller le indica a la View que actualice la tabla para mostrar el nuevo registro.

## 5. Resumen visual
```
[Usuario] ⇄ [View] ⇄ [Controller] ⇄ [Model]
```
- **View:** Interfaz gráfica, muestra datos y recibe acciones.
- **Controller:** Lógica de control, valida y coordina.
- **Model:** Datos y lógica de negocio.

---

**En tu proyecto:**
- Las clases como `TipoUsuarioDLG` y `UsuarioDLG` son Views.
- Los Controllers (`ControllerTipoUsuario`, etc.) reciben las acciones de la View.
- Los Models (`TipoUsuario`, `Usuario`, repositorios) representan y manipulan los datos.

---


## Lógica y relación detallada de cada clase de la View

### 1. `PrincipalDlg`
**Rol:** Es la ventana principal de la aplicación (hereda de `JFrame`).

- Crea la barra de menú y los menús de administración.
- Permite al usuario navegar entre diferentes paneles de administración (por ejemplo, tipos de usuario, usuarios).
- Cuando el usuario selecciona una opción del menú, llama al método `cambiarContenido(JPanel contenido)`, que reemplaza el panel actual por el seleccionado.
- No contiene lógica de negocio ni accede a datos directamente, solo gestiona la navegación visual.

**Relación con otras capas:**
- No interactúa directamente con Model ni Controller, pero es el punto de entrada para mostrar los paneles de administración (que sí interactúan con Controller y Model).

### 2. `TipoUsuarioDLG` y `UsuarioDLG`
**Rol:** Son paneles de administración (heredan de `BaseDLG`).

- Muestran una tabla con los registros de la entidad (`TipoUsuario` o `Usuario`).
- Incluyen botones para buscar, agregar, editar y eliminar registros.
- Cuando el usuario realiza una acción (por ejemplo, buscar), el panel llama a métodos de su respectivo controlador (`ControllerTipoUsuario` o `ControllerUsuario`).
- El controlador se encarga de validar y procesar la acción, y de interactuar con el modelo (repositorio y entidad).
- Cuando se agregan o editan registros, abren un diálogo modal para capturar los datos.

**Relación con otras capas:**
- Se comunican directamente con su Controller, nunca con el Model de forma directa.
- El Controller recibe las acciones, valida y usa el Model para acceder a la base de datos.
- Cuando el Controller devuelve los datos, el panel los muestra en la tabla.

### 3. `BaseDLG`
**Rol:** Clase abstracta base para los paneles de administración.

- Define la estructura visual común: barra de búsqueda, tabla de resultados, botones CRUD.
- Obliga a las subclases a implementar los métodos para cada acción (`eventoBotonBuscar`, `eventoBotonAgregar`, etc.).
- Permite reutilizar el diseño y la lógica básica para cualquier entidad.

**Relación con otras capas:**
- No interactúa directamente con Controller ni Model, pero sirve como base para los paneles que sí lo hacen.

### 4. `BaseModelDLG`
**Rol:** Clase abstracta base para los diálogos modales de captura/edición (hereda de `JDialog`).

- Define la estructura visual básica: campos de formulario, botones aceptar/cancelar.
- Obliga a las subclases a implementar la lógica de aceptación de datos (`eventoBotonAceptar`).
- Se usa para capturar o editar los datos de una entidad específica.

**Relación con otras capas:**
- No interactúa directamente con Controller ni Model, pero las subclases (como `TipoUsuarioModalDlg`) sí lo harán para validar y guardar los datos.

### 5. `TipoUsuarioModalDlg` y `UsuarioModalDlg` (por implementar)
**Rol:** Diálogos modales concretos para agregar o editar registros de tipo usuario o usuario.

- Heredan de `BaseModelDLG`.
- Presentan campos específicos para cada entidad (por ejemplo, nombre para tipo de usuario, nombre/email para usuario).
- Al aceptar, validan los datos y llaman al Controller correspondiente para guardar o actualizar la información.
- Al cerrar, notifican al panel principal para que actualice la tabla.

**Relación con otras capas:**
- Interactúan directamente con el Controller para validar y guardar los datos.
- El Controller usa el Model (repositorio y entidad) para realizar la operación en la base de datos.

---

### Ejemplo de flujo completo (Agregar un tipo de usuario):
1. El usuario abre la ventana principal (`PrincipalDlg`) y selecciona "Gestionar tipos de usuario".
2. Se muestra el panel `TipoUsuarioDLG` con la tabla y los botones.
3. El usuario hace clic en "Agregar". Se abre el diálogo modal `TipoUsuarioModalDlg`.
4. El usuario ingresa el nombre y acepta.
5. El diálogo llama a `ControllerTipoUsuario.save(nuevoTipoUsuario)`.
6. El Controller valida y usa el repositorio para guardar el dato en la base de datos.
7. Al cerrar el diálogo, el panel principal actualiza la tabla mostrando el nuevo registro.

---

**Resumen:**
- `PrincipalDlg`: Navegación y menú principal.
- `TipoUsuarioDLG`/`UsuarioDLG`: Paneles CRUD, muestran y gestionan registros, se comunican con Controller.
- `BaseDLG`: Base visual y lógica para paneles CRUD.
- `BaseModelDLG`: Base para diálogos modales de captura/edición.
- `TipoUsuarioModalDlg`/`UsuarioModalDlg`: Diálogos modales, validan y capturan datos, se comunican con Controller.
