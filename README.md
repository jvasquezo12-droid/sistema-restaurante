# Sistema Web de Restaurante

Reservaciones, Pedidos y Facturación.
Proyecto Final de Programación II — Universidad Mariano Gálvez de Guatemala, sede Morales, Izabal.

## Contenido del repositorio

- `database/script.sql` — Script de creación de la base de datos con datos de prueba
- `sistema-restaurante/` — Proyecto Spring Boot

## Requisitos

- Java 17 o superior
- MySQL 8.0
- IntelliJ IDEA

## Cómo ejecutar el proyecto

**1. Crear la base de datos**

Abrir MySQL Workbench y ejecutar el archivo `database/script.sql`.
Esto crea la base `restaurante_db` con sus 17 tablas y los datos de prueba.

**2. Abrir el proyecto**

En IntelliJ IDEA: File → Open, y seleccionar la carpeta `sistema-restaurante`
(la que contiene el archivo `pom.xml`, no la raíz del repositorio).

**3. Credenciales de la base de datos**

La conexión usa por defecto el usuario `root` con contraseña `root`.

Si su MySQL usa otras credenciales, configurarlas sin modificar el código:
Run → Edit Configurations → Modify options → Environment variables, y agregar:

    DB_USER=suusuario
    DB_PASS=suclave

**4. Ejecutar**

Correr la clase `SistemaRestauranteApplication` y abrir el navegador en:

    http://localhost:8080

## Módulos implementados

| Módulo | Ruta | Operaciones |
|---|---|---|
| Categorías del menú | `/categorias` | Listar, crear, editar, eliminar |
| Productos del menú | `/productos` | Listar, crear, editar, eliminar |

## Integrantes

| Usuario de GitHub | Integrante | Rol |
|---|---|---|
| jvasquezo12-droid | Juan Francisco Vásquez Olivas | Coordinación y análisis |
| dgonzalezl31-GL | Daniel Alexander González Lázaro | Desarrollo backend |
| marrogames1211 | Carlos Daniel Marroquín Oliva | Desarrollo frontend y pruebas |
| yokonebula | Donovan André Milián Álvarez | Análisis y diseño UML/DER |
| vmendezg4-ai | Víctor Geovanni Méndez García | Análisis y documentación |
| earriaza006 | Edwin Raúl Arriaza Victoria | Ex participante — colaboró hasta la Entrega 3 |
