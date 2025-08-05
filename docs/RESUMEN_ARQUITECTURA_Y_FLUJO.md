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


---

## Explicación detallada de la clase `TipoUsuarioDLG`


La clase `TipoUsuarioDLG` es un panel de administración (hereda de `BaseDLG`) especializado en la gestión de los registros de la entidad `TipoUsuario`. Su función principal es mostrar, buscar, eliminar, agregar y editar los tipos de usuario en la base de datos, interactuando con el controlador (`ControllerTipoUsuario`) y el modelo (`TipoUsuario` y `RepositoryTipoUsuario`).

### ¿Cómo se relaciona con el Controlador y el Modelo?

**Relación con el Controlador (`ControllerTipoUsuario`):**
- La variable privada `controllerTipoUsuario` es la instancia del controlador.
- Se inicializa en el método `init()` con `controllerTipoUsuario = new ControllerTipoUsuario();`.
- Todos los métodos de evento (buscar, eliminar, agregar, editar) usan esta variable para acceder a la lógica de negocio y, a través de ella, al modelo/repositorio.

**Relación con el Modelo (`TipoUsuario` y `RepositoryTipoUsuario`):**
- El controlador tiene el método `getRepository()`, que retorna el repositorio específico para `TipoUsuario`.
- El repositorio implementa métodos como `obtenerPorNombre(String)`, `delete(long)`, y otros CRUD, que manipulan objetos de tipo `TipoUsuario`.

### Métodos y su relación con las capas

- **eventoBotonBuscar()**
  - Llama a `controllerTipoUsuario.getRepository().obtenerPorNombre(parametro)` para obtener una lista de objetos `TipoUsuario` desde la base de datos.
  - Los resultados se muestran en la tabla de la vista.

- **eventoBotonEliminar()**
  - Obtiene el ID seleccionado y llama a `controllerTipoUsuario.getRepository().delete(ID)` para eliminar el registro en la base de datos.
  - Luego actualiza la tabla llamando a `eventoBotonBuscar()`.

- **eventoBotonAgregar()**
  - (A implementar) Debería abrir un diálogo modal para capturar datos y, tras validación, llamar a un método del controlador para agregar un nuevo `TipoUsuario` usando el repositorio (ejemplo: `controllerTipoUsuario.getRepository().insert(obj)`).

- **eventoBotonEditar()**
  - (A implementar) Debería abrir un diálogo modal con los datos actuales, permitir editarlos y, tras validación, llamar a un método del controlador para actualizar el registro (ejemplo: `controllerTipoUsuario.getRepository().update(obj)`).

### Resumen de la interacción

1. El usuario interactúa con la vista (`TipoUsuarioDLG`).
2. La vista llama métodos del controlador (`controllerTipoUsuario`).
3. El controlador usa el repositorio para acceder o modificar los datos (`TipoUsuario`).
4. Los resultados se reflejan en la interfaz gráfica.

Esto asegura una separación clara de responsabilidades y un flujo MVC puro.

### Responsabilidades principales:
- Inicializa la tabla y los componentes visuales específicos para `TipoUsuario`.
- Se comunica con el `ControllerTipoUsuario` para obtener y manipular los datos.
- Implementa los métodos protegidos heredados de `BaseDLG` para manejar los eventos de los botones CRUD (Buscar, Agregar, Editar, Eliminar).

### Flujo de trabajo:
1. **Inicialización:**
   - En el constructor, llama a `super()` para inicializar la estructura base y luego ejecuta el método `init()`.
   - En `init()`, instancia el controlador, agrega las columnas "ID" y "Nombre" a la tabla y hace visible el panel.

2. **Buscar:**
   - El método `eventoBotonBuscar()` limpia la tabla, obtiene el texto de búsqueda, consulta al repositorio a través del controlador y muestra los resultados en la tabla.

3. **Eliminar:**
   - El método `eventoBotonEliminar()` obtiene el ID seleccionado, valida la selección, elimina el registro usando el repositorio y actualiza la tabla.

4. **Agregar y Editar:**
   - Los métodos `eventoBotonAgregar()` y `eventoBotonEditar()` están preparados para abrir los diálogos modales correspondientes (a implementar), capturar los datos y actualizar la tabla.

### Relación con otras capas:
- **Vista:** Es la interfaz directa con el usuario para la administración de tipos de usuario.
- **Controlador:** Usa `ControllerTipoUsuario` para validar y coordinar las operaciones.
- **Modelo:** El controlador accede al repositorio y a la entidad `TipoUsuario` para realizar las operaciones en la base de datos.

### Resumen visual:
```
Usuario → TipoUsuarioDLG (Vista) → ControllerTipoUsuario (Controlador) → RepositoryTipoUsuario (Modelo/Repositorio) → Base de datos
```

Esta clase ejemplifica el flujo MVC: la vista captura la acción, el controlador valida y coordina, y el modelo ejecuta la operación.


### Detalle de la relación entre métodos, variables y repositorios

- **Variable `controllerTipoUsuario`:**
  - Es una instancia privada de `ControllerTipoUsuario`.
  - Se inicializa en el método `init()` y es el puente entre la vista (`TipoUsuarioDLG`) y la lógica de negocio/controlador.

- **Métodos sobrescritos (`eventoBotonBuscar`, `eventoBotonEliminar`, `eventoBotonAgregar`, `eventoBotonEditar`):**
  - Estos métodos son llamados automáticamente cuando el usuario interactúa con los botones CRUD de la interfaz.
  - Cada uno utiliza la variable `controllerTipoUsuario` para acceder a la lógica de negocio y, a través de ella, al repositorio.

- **Relación con el repositorio:**
  - `controllerTipoUsuario` tiene un método `getRepository()` que retorna una instancia de `RepositoryTipoUsuario`.
  - Por ejemplo, en `eventoBotonBuscar()`, se llama a `controllerTipoUsuario.getRepository().obtenerPorNombre(parametro)` para obtener los datos filtrados desde la base de datos.
  - En `eventoBotonEliminar()`, se llama a `controllerTipoUsuario.getRepository().delete(ID)` para eliminar un registro.

- **Modelo de datos:**
  - Los datos que se manipulan son instancias de la clase `TipoUsuario` (modelo/entidad), que representan cada fila de la tabla en la base de datos.
  - Los métodos del repositorio devuelven listas de `TipoUsuario` o manipulan objetos de este tipo.

- **Tabla visual (`modeloTabla`):**
  - Es el modelo de la tabla Swing donde se muestran los datos.
  - Se actualiza en los métodos de evento según los resultados de las operaciones del repositorio.

**Resumen de la cadena de interacción:**

1. El usuario hace clic en un botón (Buscar, Agregar, Editar, Eliminar).
2. Se ejecuta el método correspondiente en `TipoUsuarioDLG`.
3. Este método usa `controllerTipoUsuario` para acceder a la lógica de negocio.
4. El controlador usa su repositorio para consultar o modificar la base de datos.
5. Los resultados (lista de entidades, confirmación de borrado, etc.) se reflejan en la tabla de la vista.

**Ejemplo concreto:**

```java
// Buscar tipos de usuario
List<TipoUsuario> lista = controllerTipoUsuario.getRepository().obtenerPorNombre(parametro);
// Eliminar un tipo de usuario
controllerTipoUsuario.getRepository().delete(ID);
```

Así, cada capa cumple su función y la relación entre métodos, variables y repositorios es clara y directa, siguiendo el patrón MVC.


---

## Explicación detallada de la clase `TipoUsuarioModalDLG`

La clase `TipoUsuarioModalDLG` es un diálogo modal (hereda de `BaseModelDLG`) diseñado para capturar y editar los datos de un `TipoUsuario`. Se utiliza cuando el usuario desea agregar un nuevo tipo de usuario o editar uno existente desde el panel de administración (`TipoUsuarioDLG`).

### ¿Cómo se relaciona con el Controlador y el Modelo?

**Relación con el Controlador (`ControllerTipoUsuario`):**
- Recibe una instancia del controlador (o del objeto a editar) al ser creada.
- Utiliza el controlador para validar los datos ingresados y para guardar o actualizar el registro en la base de datos.

**Relación con el Modelo (`TipoUsuario` y `RepositoryTipoUsuario`):**
- Manipula directamente una instancia de `TipoUsuario` (nuevo o existente).
- El controlador, a su vez, usa el repositorio para insertar o actualizar el registro en la base de datos.

### Métodos y su relación con las capas

- **mostrarDialogo(TipoUsuario obj)**
  - Método típico para mostrar el diálogo y cargar los datos del objeto a editar (o dejar vacío para agregar).

- **eventoGuardar()**
  - Valida los datos ingresados usando el controlador (`controllerTipoUsuario.validate(obj)`).
  - Si es válido, llama a `controllerTipoUsuario.getRepository().insert(obj)` para agregar o `update(obj)` para editar, según el caso.
  - Cierra el diálogo y notifica a la vista principal para refrescar la tabla.

- **eventoCancelar()**
  - Cierra el diálogo sin guardar cambios.

### Resumen de la interacción

1. El usuario abre el diálogo desde `TipoUsuarioDLG` (Agregar o Editar).
2. El diálogo muestra los campos para capturar o modificar los datos.
3. Al guardar, valida y persiste los datos usando el controlador y el repositorio.
4. Al cerrar, notifica a la vista principal para actualizar la tabla.

### Responsabilidades principales:
- Mostrar y capturar los datos de un `TipoUsuario`.
- Validar los datos antes de guardar.
- Insertar o actualizar el registro en la base de datos.
- Notificar a la vista principal para refrescar la información.

### Resumen visual:
```
TipoUsuarioDLG (Vista principal) → TipoUsuarioModalDLG (Modal) → ControllerTipoUsuario (Controlador) → RepositoryTipoUsuario (Modelo/Repositorio) → Base de datos
```

Esta clase asegura que la edición y alta de registros se realice de forma controlada, validando los datos y manteniendo la coherencia del flujo MVC.
