CREATE DATABASE  IF NOT EXISTS `restaurante_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `restaurante_db`;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  UNIQUE KEY `uq_cliente_usuario` (`idUsuario`),
  CONSTRAINT `fk_cliente_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `chk_cliente_loyalty` CHECK ((`loyalty` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `idDetalle` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `precioUnitario` decimal(10,2) NOT NULL DEFAULT '0.00',
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
  `referencia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPago`),
  KEY `fk_pago_pedido` (`idPedido`),
  KEY `fk_pago_forma` (`idFormaPago`),
  CONSTRAINT `fk_pago_forma` FOREIGN KEY (`idFormaPago`) REFERENCES `forma_pago` (`idFormaPago`),
  CONSTRAINT `fk_pago_pedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`),
  CONSTRAINT `chk_pago_total` CHECK ((`total` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `motivoCancelacion` varchar(255) DEFAULT NULL,
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
  `disponible` tinyint(1) NOT NULL DEFAULT '1',
  `idCategoria` int NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_producto_categoria` (`idCategoria`),
  CONSTRAINT `fk_producto_categoria` FOREIGN KEY (`idCategoria`) REFERENCES `categoria_menu` (`idCategoria`),
  CONSTRAINT `chk_producto_precio` CHECK ((`precio` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `motivoCancelacion` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-16 18:10:27
