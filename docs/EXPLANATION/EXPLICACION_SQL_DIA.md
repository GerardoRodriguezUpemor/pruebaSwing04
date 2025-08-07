# Documentación de archivos: pruebaSwing04.sql y pruebaSwing04.dia

## 1. `pruebaSwing04.sql`

Este archivo contiene el **script SQL** necesario para crear las tablas de la base de datos del proyecto. Su función principal es definir la estructura real de la base de datos en el motor que elijas (por ejemplo, SQLite o MariaDB). 

- **¿Qué contiene?**
  - Instrucciones `CREATE TABLE` para crear las tablas, sus campos, tipos de datos, claves primarias, restricciones de unicidad, relaciones, etc.
- **¿Cómo se usa?**
  - Se ejecuta directamente en el gestor de base de datos (por ejemplo, usando una consola SQL, DBeaver, phpMyAdmin, etc.).
  - Al ejecutarlo, se crean físicamente las tablas y relaciones en la base de datos.
- **¿Qué NO hace?**
  - No contiene datos, solo la estructura.
  - No es un diagrama visual.

## 2. `pruebaSwing04.dia`

Este archivo es un **diagrama visual** creado con la aplicación Dia. Sirve para documentar y visualizar gráficamente la estructura de la base de datos.

- **¿Qué contiene?**
  - Representación gráfica de las tablas, sus campos y las relaciones entre ellas.
  - Es útil para entender y comunicar la estructura de la base de datos a otros desarrolladores o para documentación.
- **¿Cómo se usa?**
  - Se abre y edita con el programa Dia (disponible para Windows, Linux y Mac).
  - Permite modificar el diseño visualmente, pero no afecta la base de datos real.
- **¿Qué NO hace?**
  - No crea ni modifica la base de datos real.
  - No contiene datos reales, solo la estructura visual.

## Relación entre ambos archivos
- El `.sql` define la estructura real de la base de datos.
- El `.dia` documenta visualmente esa estructura para facilitar la comprensión y el diseño.
- Es recomendable mantener ambos sincronizados: si cambias la estructura en el SQL, actualiza el diagrama, y viceversa.

---
**Resumen:**
- Usa el `.sql` para crear las tablas en tu base de datos.
- Usa el `.dia` para visualizar y documentar la estructura de esas tablas.
