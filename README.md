# Sistema Web de Restaurante

Sistema de reservaciones, pedidos y facturación.
Proyecto final de Programación II - Universidad Mariano Gálvez, sede Morales.

## Requisitos

- Java 17
- MySQL 8.0
- IntelliJ IDEA

## Cómo ejecutar el proyecto

1. Abrir MySQL Workbench y ejecutar el script `database/script.sql`.
   Esto crea la base `restaurante_db` con sus tablas y datos de prueba.

2. Abrir el proyecto en IntelliJ IDEA y ejecutarlo.

La conexión usa por defecto el usuario `root` con contraseña `root`.
Si su MySQL usa otras credenciales, configurarlas como variables de
entorno `DB_USER` y `DB_PASS` en Run > Edit Configurations.

3. Abrir el navegador en http://localhost:8080

## Integrantes

- Juan Francisco Vásquez Olivas - Coordinación y análisis
- Daniel Alexander González Lázaro - Desarrollo backend
- Edwin Raúl Arriaza Victoria - Desarrollo backend
- Carlos Daniel Marroquín Oliva - Frontend y pruebas
- Víctor Geovanni Méndez García - Análisis y documentación
- Donovan André Milián Álvarez - Análisis y diseño UML/DER
