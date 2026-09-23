-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurante_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Creacion de la base de datos
--

CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

--
-- Table structure for table `categoria_menu`
--

DROP TABLE IF EXISTS `categoria_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_menu` (
  `idCategoria` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_menu`
--

LOCK TABLES `categoria_menu` WRITE;
/*!40000 ALTER TABLE `categoria_menu` DISABLE KEYS */;
INSERT INTO `categoria_menu` VALUES (1,'Entradas','Platillos para abrir el apetito'),(2,'Platos Fuertes','Platillos principales'),(3,'Bebidas','Bebidas frías y calientes'),(4,'Postres','Dulces para cerrar la comida');
/*!40000 ALTER TABLE `categoria_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `telefono` varchar(30) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `loyalty` int NOT NULL DEFAULT '0',
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `fk_cliente_usuario` (`idUsuario`),
  CONSTRAINT `fk_cliente_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `chk_cliente_loyalty` CHECK ((`loyalty` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'50212345678','Zona 10, Ciudad de Guatemala',150,4),(2,'50287654321','Zona 15, Ciudad de Guatemala',40,5);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `idDetalle` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `idPedido` int NOT NULL,
  `idProducto` int NOT NULL,
  PRIMARY KEY (`idDetalle`),
  KEY `fk_detalle_pedido` (`idPedido`),
  KEY `fk_detalle_producto` (`idProducto`),
  CONSTRAINT `fk_detalle_pedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`),
  CONSTRAINT `fk_detalle_producto` FOREIGN KEY (`idProducto`) REFERENCES `producto_menu` (`idProducto`),
  CONSTRAINT `chk_detalle_cantidad` CHECK ((`cantidad` > 0)),
  CONSTRAINT `chk_detalle_subtotal` CHECK ((`subtotal` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` VALUES (1,1,'Extra queso',35.00,1,1),(2,2,NULL,24.00,1,6),(3,1,'Término medio',85.00,2,4),(4,1,NULL,60.00,2,5),(5,2,NULL,90.00,3,3);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_mesa`
--

DROP TABLE IF EXISTS `estado_mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_mesa` (
  `idEstadoMesa` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idEstadoMesa`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_mesa`
--

LOCK TABLES `estado_mesa` WRITE;
/*!40000 ALTER TABLE `estado_mesa` DISABLE KEYS */;
INSERT INTO `estado_mesa` VALUES (1,'Disponible'),(4,'Fuera de servicio'),(2,'Ocupada'),(3,'Reservada');
/*!40000 ALTER TABLE `estado_mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_pedido`
--

DROP TABLE IF EXISTS `estado_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_pedido` (
  `idEstadoPedido` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idEstadoPedido`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_pedido`
--

LOCK TABLES `estado_pedido` WRITE;
/*!40000 ALTER TABLE `estado_pedido` DISABLE KEYS */;
INSERT INTO `estado_pedido` VALUES (4,'Cancelado'),(2,'En preparación'),(1,'Pendiente'),(3,'Servido');
/*!40000 ALTER TABLE `estado_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_reservacion`
--

DROP TABLE IF EXISTS `estado_reservacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_reservacion` (
  `idEstadoReservacion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idEstadoReservacion`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_reservacion`
--

LOCK TABLES `estado_reservacion` WRITE;
/*!40000 ALTER TABLE `estado_reservacion` DISABLE KEYS */;
INSERT INTO `estado_reservacion` VALUES (3,'Cancelada'),(4,'Completada'),(1,'Confirmada'),(2,'Pendiente');
/*!40000 ALTER TABLE `estado_reservacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `idFactura` int NOT NULL AUTO_INCREMENT,
  `fFactura` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) NOT NULL,
  `iva` decimal(10,2) NOT NULL,
  `idPedido` int NOT NULL,
  PRIMARY KEY (`idFactura`),
  KEY `fk_factura_pedido` (`idPedido`),
  CONSTRAINT `fk_factura_pedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`),
  CONSTRAINT `chk_factura_iva` CHECK ((`iva` >= 0)),
  CONSTRAINT `chk_factura_total` CHECK ((`total` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,'2026-09-18 21:37:50',59.00,6.32,1),(2,'2026-09-18 21:37:50',145.00,15.54,2);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma_pago`
--

DROP TABLE IF EXISTS `forma_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forma_pago` (
  `idFormaPago` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idFormaPago`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma_pago`
--

LOCK TABLES `forma_pago` WRITE;
/*!40000 ALTER TABLE `forma_pago` DISABLE KEYS */;
INSERT INTO `forma_pago` VALUES (1,'Efectivo'),(2,'Tarjeta de crédito'),(3,'Tarjeta de débito'),(4,'Transferencia');
/*!40000 ALTER TABLE `forma_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesa` (
  `idMesa` int NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `capacidad` int NOT NULL,
  `loc` varchar(100) DEFAULT NULL,
  `idEstadoMesa` int NOT NULL,
  PRIMARY KEY (`idMesa`),
  UNIQUE KEY `numero` (`numero`),
  KEY `fk_mesa_estado` (`idEstadoMesa`),
  CONSTRAINT `fk_mesa_estado` FOREIGN KEY (`idEstadoMesa`) REFERENCES `estado_mesa` (`idEstadoMesa`),
  CONSTRAINT `chk_mesa_capacidad` CHECK ((`capacidad` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa`
--

LOCK TABLES `mesa` WRITE;
/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` VALUES (1,1,2,'Terraza',1),(2,2,4,'Terraza',2),(3,3,4,'Salón principal',1),(4,4,6,'Salón principal',3),(5,5,8,'Salón VIP',1),(6,6,2,'Barra',1);
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `idPago` int NOT NULL AUTO_INCREMENT,
  `total` decimal(10,2) NOT NULL,
  `fPago` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idPedido` int NOT NULL,
  `idFormaPago` int NOT NULL,
  PRIMARY KEY (`idPago`),
  KEY `fk_pago_pedido` (`idPedido`),
  KEY `fk_pago_forma` (`idFormaPago`),
  CONSTRAINT `fk_pago_forma` FOREIGN KEY (`idFormaPago`) REFERENCES `forma_pago` (`idFormaPago`),
  CONSTRAINT `fk_pago_pedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`),
  CONSTRAINT `chk_pago_total` CHECK ((`total` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,59.00,'2026-09-18 21:37:50',1,1),(2,145.00,'2026-09-18 21:37:50',2,2);
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `idPedido` int NOT NULL AUTO_INCREMENT,
  `fCreation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fUpdate` datetime DEFAULT NULL,
  `nota` varchar(500) DEFAULT NULL,
  `idMesa` int NOT NULL,
  `idUsuario` int NOT NULL,
  `idEstadoPedido` int NOT NULL,
  PRIMARY KEY (`idPedido`),
  KEY `fk_pedido_mesa` (`idMesa`),
  KEY `fk_pedido_usuario` (`idUsuario`),
  KEY `fk_pedido_estado` (`idEstadoPedido`),
  CONSTRAINT `fk_pedido_estado` FOREIGN KEY (`idEstadoPedido`) REFERENCES `estado_pedido` (`idEstadoPedido`),
  CONSTRAINT `fk_pedido_mesa` FOREIGN KEY (`idMesa`) REFERENCES `mesa` (`idMesa`),
  CONSTRAINT `fk_pedido_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,'2026-09-18 21:37:50',NULL,'Sin cebolla en los nachos',2,2,3),(2,'2026-09-18 21:37:50',NULL,'Cliente frecuente, mesa VIP',5,2,2),(3,'2026-09-18 21:37:50',NULL,'Para llevar',3,2,1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_menu`
--

DROP TABLE IF EXISTS `producto_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_menu` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `img` varchar(500) DEFAULT NULL,
  `idCategoria` int NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_producto_categoria` (`idCategoria`),
  CONSTRAINT `fk_producto_categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categoria_menu` (`idCategoria`),
  CONSTRAINT `chk_producto_precio` CHECK ((`precio` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_menu`
--

LOCK TABLES `producto_menu` WRITE;
/*!40000 ALTER TABLE `producto_menu` DISABLE KEYS */;
INSERT INTO `producto_menu` VALUES (1,'Nachos con queso','Totopos bañados en queso fundido',35.00,NULL,1),(2,'Alitas BBQ','Alitas de pollo bañadas en salsa BBQ',45.00,NULL,1),(3,'Pechuga a la plancha','Pechuga de pollo con guarnición',65.00,NULL,2),(4,'Lomito al carbón','Lomito de res a la parrilla',85.00,NULL,2),(5,'Pasta Alfredo','Pasta en salsa alfredo con pollo',60.00,NULL,2),(6,'Limonada','Limonada natural',15.00,NULL,3),(7,'Gaseosa','Bebida carbonatada 400ml',12.00,NULL,3),(8,'Café','Café americano',10.00,NULL,3),(9,'Tres leches','Pastel tres leches',25.00,NULL,4),(10,'Helado','Dos bolas de helado a elegir',20.00,NULL,4);
/*!40000 ALTER TABLE `producto_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_promocion`
--

DROP TABLE IF EXISTS `producto_promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_promocion` (
  `idPromocion` int NOT NULL,
  `idProducto` int NOT NULL,
  PRIMARY KEY (`idPromocion`,`idProducto`),
  KEY `fk_pp_producto` (`idProducto`),
  CONSTRAINT `fk_pp_producto` FOREIGN KEY (`idProducto`) REFERENCES `producto_menu` (`idProducto`),
  CONSTRAINT `fk_pp_promocion` FOREIGN KEY (`idPromocion`) REFERENCES `promocion` (`idPromocion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_promocion`
--

LOCK TABLES `producto_promocion` WRITE;
/*!40000 ALTER TABLE `producto_promocion` DISABLE KEYS */;
INSERT INTO `producto_promocion` VALUES (1,1),(2,3),(2,4),(3,5),(1,6);
/*!40000 ALTER TABLE `producto_promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocion`
--

DROP TABLE IF EXISTS `promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promocion` (
  `idPromocion` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `discountPct` decimal(5,2) NOT NULL,
  `expiration` date DEFAULT NULL,
  PRIMARY KEY (`idPromocion`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `chk_discount` CHECK (((`discountPct` >= 0) and (`discountPct` <= 100)))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocion`
--

LOCK TABLES `promocion` WRITE;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` VALUES (1,'BIENVENIDA10','Descuento de bienvenida para nuevos clientes',10.00,'2026-12-31'),(2,'FINDE15','Descuento de fin de semana',15.00,'2026-11-30'),(3,'COMBOFAM','Descuento para combos familiares',20.00,'2026-10-15');
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservacion`
--

DROP TABLE IF EXISTS `reservacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservacion` (
  `idReservacion` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `personas` int NOT NULL,
  `fCreation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idCliente` int NOT NULL,
  `idMesa` int NOT NULL,
  `idEstadoReservacion` int NOT NULL,
  PRIMARY KEY (`idReservacion`),
  KEY `fk_reservacion_cliente` (`idCliente`),
  KEY `fk_reservacion_mesa` (`idMesa`),
  KEY `fk_reservacion_estado` (`idEstadoReservacion`),
  CONSTRAINT `fk_reservacion_cliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `fk_reservacion_estado` FOREIGN KEY (`idEstadoReservacion`) REFERENCES `estado_reservacion` (`idEstadoReservacion`),
  CONSTRAINT `fk_reservacion_mesa` FOREIGN KEY (`idMesa`) REFERENCES `mesa` (`idMesa`),
  CONSTRAINT `chk_reservacion_personas` CHECK ((`personas` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservacion`
--

LOCK TABLES `reservacion` WRITE;
/*!40000 ALTER TABLE `reservacion` DISABLE KEYS */;
INSERT INTO `reservacion` VALUES (1,'2026-09-25','19:00:00',4,'2026-09-18 21:37:50',1,3,1),(2,'2026-09-28','13:30:00',2,'2026-09-18 21:37:50',2,6,2);
/*!40000 ALTER TABLE `reservacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idRol`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Administrador','Acceso total al sistema'),(2,'Mesero','Atiende mesas y toma pedidos'),(3,'Cocina','Prepara los pedidos'),(4,'Cliente','Usuario cliente del restaurante');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fCreation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idRol` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_usuario_rol` (`idRol`),
  CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Daniel Marroquín','admin@restaurante.com','hash_admin_123','2026-09-18 21:37:50',1),(2,'Carlos Méndez','carlos.mesero@restaurante.com','hash_mesero_123','2026-09-18 21:37:50',2),(3,'Ana López','ana.cocina@restaurante.com','hash_cocina_123','2026-09-18 21:37:50',3),(4,'María Pérez','maria.perez@gmail.com','hash_cliente_123','2026-09-18 21:37:50',4),(5,'José García','jose.garcia@gmail.com','hash_cliente_456','2026-09-18 21:37:50',4);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-18 21:46:00
