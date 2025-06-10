CREATE DATABASE  IF NOT EXISTS `proyecto_construccion` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `proyecto_construccion`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: proyecto_construccion
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `academico`
--

DROP TABLE IF EXISTS `academico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academico` (
  `idUsuario` int NOT NULL,
  `noPersonal` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_academico_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_academico_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academico`
--

LOCK TABLES `academico` WRITE;
/*!40000 ALTER TABLE `academico` DISABLE KEYS */;
INSERT INTO `academico` VALUES (8,45731),(9,22546),(11,45213),(25,94211),(26,54452);
/*!40000 ALTER TABLE `academico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academico_evaluador`
--

DROP TABLE IF EXISTS `academico_evaluador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academico_evaluador` (
  `idUsuario` int NOT NULL,
  `noPersonal` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_academico_evaluador_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_academico_evaluador_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academico_evaluador`
--

LOCK TABLES `academico_evaluador` WRITE;
/*!40000 ALTER TABLE `academico_evaluador` DISABLE KEYS */;
INSERT INTO `academico_evaluador` VALUES (10,64247),(13,45497),(16,10134),(18,13787),(22,45784);
/*!40000 ALTER TABLE `academico_evaluador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `afirmacion_evaluacion_ov`
--

DROP TABLE IF EXISTS `afirmacion_evaluacion_ov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `afirmacion_evaluacion_ov` (
  `idAfirmacionEvaluacionOV` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `idCategoriaEvaluacionOV` int NOT NULL,
  PRIMARY KEY (`idAfirmacionEvaluacionOV`),
  KEY `fk_afirmacion_evaluacion_ov_categoria_evaluacion_ov1_idx` (`idCategoriaEvaluacionOV`),
  CONSTRAINT `fk_afirmacion_evaluacion_ov_categoria_evaluacion_ov1` FOREIGN KEY (`idCategoriaEvaluacionOV`) REFERENCES `categoria_evaluacion_ov` (`idCategoriaEvaluacionOV`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afirmacion_evaluacion_ov`
--

LOCK TABLES `afirmacion_evaluacion_ov` WRITE;
/*!40000 ALTER TABLE `afirmacion_evaluacion_ov` DISABLE KEYS */;
INSERT INTO `afirmacion_evaluacion_ov` VALUES (1,'Mostró responsabilidad en las actividades.',1),(2,'Fue puntual.',1),(3,'Mostró disciplina durante su estancia en la Organización Vinculada.',1),(4,'Se integró a la Organización Vinculada de forma efectiva.',1),(5,'Mostró seguridad en las actividades encomendadas.',1),(6,'Mostró respeto a sus compañeros y su conducta en general fue adecuada.',1),(7,'Demostró tener compromiso con la Organización Vinculada.',1),(8,'Encontró en el perfil del alumno, factores de competitividad.',1),(9,'Empleó diversas metodologías de la ingeniería de software en la ejecución de sus actividades.',2),(10,'Se enfocó en el desarrollo de sus actividades.',2),(11,'Organizó su material de trabajo oportunamente.',2),(12,'Distribuyó su tiempo para asegurar el desarrollo oportuno de sus actividades.',2),(13,'Aplicó conocimineto teórico-prácticos en el desarrollo de sus actividades.',2),(14,'Aportó ideas para la toma de decisiones en la solución de problemas.',2),(15,'El perfil del estudiante responde a las necesidades de su Organización Vinculada.',2),(16,'Realizó las actividades encomendadas correctamente.',3),(17,'Cubrió satisfactoriamente su programa de trabajo.',3),(18,'Contribuyó a realizar mejoras en la Organizaicón Vinculada a través de su trabajo.',3),(19,'La calidad de los productos desarrolladors es adecuada.',3),(20,'Volvería a incluir al alumno en un proyecto a su cargo.',3);
/*!40000 ALTER TABLE `afirmacion_evaluacion_ov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_evaluacion_ov`
--

DROP TABLE IF EXISTS `categoria_evaluacion_ov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_evaluacion_ov` (
  `idCategoriaEvaluacionOV` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoriaEvaluacionOV`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_evaluacion_ov`
--

LOCK TABLES `categoria_evaluacion_ov` WRITE;
/*!40000 ALTER TABLE `categoria_evaluacion_ov` DISABLE KEYS */;
INSERT INTO `categoria_evaluacion_ov` VALUES (1,'Actitud del alumno'),(2,'Desarrollo de actividades'),(3,'Evaluación de actividades');
/*!40000 ALTER TABLE `categoria_evaluacion_ov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinador`
--

DROP TABLE IF EXISTS `coordinador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coordinador` (
  `idUsuario` int NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_coordinador_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_coordinador_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinador`
--

LOCK TABLES `coordinador` WRITE;
/*!40000 ALTER TABLE `coordinador` DISABLE KEYS */;
INSERT INTO `coordinador` VALUES (12,'2288451296'),(14,'2281324598'),(17,'2287146556'),(20,'229457557'),(21,'228320459'),(32,'228134582');
/*!40000 ALTER TABLE `coordinador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterio_evaluacion_exposicion`
--

DROP TABLE IF EXISTS `criterio_evaluacion_exposicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criterio_evaluacion_exposicion` (
  `idCriterioEvaluacionExposicion` int NOT NULL AUTO_INCREMENT,
  `criterio` varchar(50) NOT NULL,
  `nivel` varchar(30) NOT NULL,
  `valor` decimal(4,2) NOT NULL,
  `idEvaluacionExposicion` int NOT NULL,
  PRIMARY KEY (`idCriterioEvaluacionExposicion`),
  KEY `fk_criterio_evaluacion_exposicion_evaluacion_exposicion1_idx` (`idEvaluacionExposicion`),
  CONSTRAINT `fk_criterio_evaluacion_exposicion_evaluacion_exposicion1` FOREIGN KEY (`idEvaluacionExposicion`) REFERENCES `evaluacion_exposicion` (`idEvaluacionExposicion`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterio_evaluacion_exposicion`
--

LOCK TABLES `criterio_evaluacion_exposicion` WRITE;
/*!40000 ALTER TABLE `criterio_evaluacion_exposicion` DISABLE KEYS */;
INSERT INTO `criterio_evaluacion_exposicion` VALUES (1,'Dominio del tema','Puede mejorar',0.70,1),(2,'Formalidad de la presentación','Excelente',1.00,1),(3,'Organización del equipo','Puede mejorar',0.70,1),(4,'Dominio del tema','Excelente',1.00,2),(5,'Formalidad de la presentación','Satisfactorio',0.85,2),(6,'Organización del equipo','No cumple con lo requerido',0.50,2),(7,'Dominio del tema','Satisfactorio',0.85,3),(8,'Formalidad de la presentación','Excelente',1.00,3),(9,'Organización del equipo','No cumple con lo requerido',0.50,3),(10,'Dominio del tema','Satisfactorio',0.85,4),(11,'Formalidad de la presentación','Satisfactorio',0.85,4),(12,'Organización del equipo','No cumple con lo requerido',0.50,4),(13,'Dominio del tema','Satisfactorio',0.85,5),(14,'Formalidad de la presentación','Satisfactorio',0.85,5),(15,'Organización del equipo','No cumple con lo requerido',0.50,5),(16,'Dominio del tema','Satisfactorio',0.85,6),(17,'Formalidad de la presentación','Satisfactorio',0.85,6),(18,'Organización del equipo','Puede mejorar',0.70,6),(19,'Dominio del tema','Excelente',1.00,7),(20,'Formalidad de la presentación','Satisfactorio',0.85,7),(21,'Organización del equipo','No cumple con lo requerido',0.50,7),(22,'Dominio del tema','Satisfactorio',0.85,8),(23,'Formalidad de la presentación','Satisfactorio',0.85,8),(24,'Organización del equipo','Puede mejorar',0.70,8),(25,'Dominio del tema','Satisfactorio',0.85,9),(26,'Formalidad de la presentación','Excelente',1.00,9),(27,'Organización del equipo','Puede mejorar',0.70,9),(28,'Dominio del tema','No cumple con lo requerido',0.50,10),(29,'Formalidad de la presentación','No cumple con lo requerido',0.50,10),(30,'Organización del equipo','Puede mejorar',0.70,10),(31,'Dominio del tema','No cumple con lo requerido',0.50,11),(32,'Formalidad de la presentación','Puede mejorar',0.70,11),(33,'Organización del equipo','Puede mejorar',0.70,11),(34,'Dominio del tema','No cumple con lo requerido',0.50,12),(35,'Formalidad de la presentación','Puede mejorar',0.70,12),(36,'Organización del equipo','Puede mejorar',0.70,12),(37,'Dominio del tema','Puede mejorar',0.70,13),(38,'Formalidad de la presentación','Puede mejorar',0.70,13),(39,'Organización del equipo','No cumple con lo requerido',0.50,13),(40,'Dominio del tema','Puede mejorar',0.70,14),(41,'Formalidad de la presentación','No cumple con lo requerido',0.50,14),(42,'Organización del equipo','Puede mejorar',0.70,14),(43,'Dominio del tema','No cumple con lo requerido',0.50,15),(44,'Formalidad de la presentación','Puede mejorar',0.70,15),(45,'Organización del equipo','Satisfactorio',0.85,15),(46,'Dominio del tema','Puede mejorar',0.70,16),(47,'Formalidad de la presentación','Puede mejorar',0.70,16),(48,'Organización del equipo','Satisfactorio',0.85,16),(49,'Dominio del tema','No cumple con lo requerido',0.50,17),(50,'Formalidad de la presentación','Puede mejorar',0.70,17),(51,'Organización del equipo','Puede mejorar',0.70,17),(52,'Dominio del tema','Excelente',1.00,18),(53,'Formalidad de la presentación','Excelente',1.00,18),(54,'Organización del equipo','Satisfactorio',0.85,18),(55,'Dominio del tema','Excelente',1.00,19),(56,'Formalidad de la presentación','Excelente',1.00,19),(57,'Organización del equipo','Satisfactorio',0.85,19),(58,'Dominio del tema','Excelente',1.00,20),(59,'Formalidad de la presentación','Excelente',1.00,20),(60,'Organización del equipo','Excelente',1.00,20),(61,'Dominio del tema','Excelente',1.00,21),(62,'Formalidad de la presentación','Excelente',1.00,21),(63,'Organización del equipo','Satisfactorio',0.85,21);
/*!40000 ALTER TABLE `criterio_evaluacion_exposicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criterio_evaluacion_ov`
--

DROP TABLE IF EXISTS `criterio_evaluacion_ov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criterio_evaluacion_ov` (
  `idCriterioEvaluacionOV` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `valor` decimal(3,2) NOT NULL,
  PRIMARY KEY (`idCriterioEvaluacionOV`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criterio_evaluacion_ov`
--

LOCK TABLES `criterio_evaluacion_ov` WRITE;
/*!40000 ALTER TABLE `criterio_evaluacion_ov` DISABLE KEYS */;
INSERT INTO `criterio_evaluacion_ov` VALUES (1,'Nunca',1.00),(2,'Pocas veces',2.00),(3,'Algunas veces',3.00),(4,'Muchas veces',4.00),(5,'Siempre',5.00);
/*!40000 ALTER TABLE `criterio_evaluacion_ov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento_final`
--

DROP TABLE IF EXISTS `documento_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento_final` (
  `idDocumentoFinal` int NOT NULL AUTO_INCREMENT,
  `fechaEntregado` date NOT NULL,
  `nombreArchivo` varchar(100) NOT NULL,
  `extensionArchivo` varchar(10) NOT NULL,
  `archivo` longblob,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idDocumentoFinal`),
  KEY `idx_entrega_doc_final_idexpediente` (`idExpediente`),
  CONSTRAINT `entrega_doc_final_ibfk_2` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento_final`
--

LOCK TABLES `documento_final` WRITE;
/*!40000 ALTER TABLE `documento_final` DISABLE KEYS */;
INSERT INTO `documento_final` VALUES (1,'2025-06-02','Autoevaluación','pdf',NULL,2),(2,'2025-06-02','Autoevaluación','pdf',NULL,3),(3,'2025-06-01','Autoevaluación','pdf',NULL,4),(4,'2025-06-01','Autoevaluación','pdf',NULL,5),(5,'2025-06-03','Autoevaluación','pdf',NULL,6);
/*!40000 ALTER TABLE `documento_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento_inicio`
--

DROP TABLE IF EXISTS `documento_inicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento_inicio` (
  `idDocumentoInicio` int NOT NULL AUTO_INCREMENT,
  `fechaEntregado` date NOT NULL,
  `nombreArchivo` varchar(100) NOT NULL,
  `extensionArchivo` varchar(10) NOT NULL,
  `archivo` longblob,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idDocumentoInicio`),
  KEY `idx_entrega_doc_inicio_idexpediente` (`idExpediente`),
  CONSTRAINT `entrega_doc_inicio_ibfk_2` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento_inicio`
--

LOCK TABLES `documento_inicio` WRITE;
/*!40000 ALTER TABLE `documento_inicio` DISABLE KEYS */;
INSERT INTO `documento_inicio` VALUES (1,'2024-02-16','CartaAceptacion','pdf',NULL,1),(2,'2024-02-18','Cronograma','pdf',NULL,1),(3,'2024-02-19','Horario','pdf',NULL,1),(4,'2024-02-19','OficioAsignación','pdf',NULL,1),(5,'2025-02-10','CartaAceptacion','pdf',NULL,2),(6,'2025-02-10','Cronograma','pdf',NULL,2),(7,'2025-02-10','Horario','pdf',NULL,2),(8,'2025-02-10','OficioAsignación','pdf',NULL,2),(9,'2025-02-10','CartaAceptacion','pdf',NULL,3),(10,'2025-02-10','Cronograma','pdf',NULL,3),(11,'2025-02-10','Horario','pdf',NULL,3),(12,'2025-02-10','OficioAsignación','pdf',NULL,3),(13,'2025-02-10','CartaAceptacion','pdf',NULL,4),(14,'2025-02-10','Cronograma','pdf',NULL,4),(15,'2025-02-10','Horario','pdf',NULL,4),(16,'2025-02-10','OficioAsignación','pdf',NULL,4),(17,'2025-02-10','CartaAceptacion','pdf',NULL,5),(18,'2025-02-10','Cronograma','pdf',NULL,5),(19,'2025-02-10','Horario','pdf',NULL,5),(20,'2025-02-10','OficioAsignación','pdf',NULL,5),(21,'2025-02-10','CartaAceptacion','pdf',NULL,6),(22,'2025-02-10','Cronograma','pdf',NULL,6),(23,'2025-02-10','Horario','pdf',NULL,6),(24,'2025-02-10','OficioAsignación','pdf',NULL,6);
/*!40000 ALTER TABLE `documento_inicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `idUsuario` int NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `matricula` varchar(25) NOT NULL,
  `idExperiencia` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_alumno_experiencia_educativa1_idx` (`idExperiencia`),
  KEY `fk_estudiante_usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_alumno_experiencia_educativa1` FOREIGN KEY (`idExperiencia`) REFERENCES `experiencia_educativa` (`idExperiencia`),
  CONSTRAINT `fk_estudiante_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (7,'2005-04-06','S23045042',6),(15,'2005-09-12','S23045734',6),(19,'2004-12-01','S22074091',6),(23,'2005-10-09','S23022442',6),(24,'2005-04-09','S23054004',6),(28,'2003-07-05','S21230417',6),(29,'2002-10-27','S20160802',6),(30,'2005-04-20','S23010169',6);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluacion_exposicion`
--

DROP TABLE IF EXISTS `evaluacion_exposicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluacion_exposicion` (
  `idEvaluacionExposicion` int NOT NULL AUTO_INCREMENT,
  `comentarios` varchar(200) DEFAULT NULL,
  `puntajeFinal` decimal(5,2) NOT NULL,
  `idExpediente` int NOT NULL,
  `idAcademicoEvaluador` int NOT NULL,
  PRIMARY KEY (`idEvaluacionExposicion`),
  KEY `idx_evaluacion_exposicion_idexpediente` (`idExpediente`),
  KEY `fk_evaluacion_exposicion_academico_evaluador1_idx` (`idAcademicoEvaluador`),
  CONSTRAINT `evaluacion_exposicion_ibfk_1` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`),
  CONSTRAINT `fk_evaluacion_exposicion_academico_evaluador1` FOREIGN KEY (`idAcademicoEvaluador`) REFERENCES `academico_evaluador` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluacion_exposicion`
--

LOCK TABLES `evaluacion_exposicion` WRITE;
/*!40000 ALTER TABLE `evaluacion_exposicion` DISABLE KEYS */;
INSERT INTO `evaluacion_exposicion` VALUES (1,NULL,2.40,2,10),(2,NULL,2.35,2,13),(3,NULL,2.35,2,16),(4,NULL,2.20,2,18),(5,NULL,2.20,2,22),(6,NULL,2.40,3,10),(7,NULL,2.35,3,13),(8,NULL,2.40,3,16),(9,NULL,2.35,3,18),(10,NULL,1.70,4,13),(11,NULL,1.90,4,16),(12,NULL,1.90,4,22),(13,NULL,1.90,5,10),(14,NULL,1.90,5,13),(15,NULL,2.05,5,16),(16,NULL,2.25,5,18),(17,NULL,1.90,5,22),(18,NULL,2.85,6,10),(19,NULL,2.85,6,13),(20,NULL,3.00,6,18),(21,NULL,2.85,6,22);
/*!40000 ALTER TABLE `evaluacion_exposicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluacion_ov`
--

DROP TABLE IF EXISTS `evaluacion_ov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluacion_ov` (
  `idEvaluacionOV` int NOT NULL AUTO_INCREMENT,
  `comentarios` varchar(200) DEFAULT NULL,
  `fecha` date NOT NULL,
  `puntaje_final` decimal(5,2) NOT NULL,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idEvaluacionOV`),
  KEY `idx_evaluacion_a_ov_idexpediente` (`idExpediente`),
  CONSTRAINT `evaluacion_a_ov_ibfk_1` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluacion_ov`
--

LOCK TABLES `evaluacion_ov` WRITE;
/*!40000 ALTER TABLE `evaluacion_ov` DISABLE KEYS */;
INSERT INTO `evaluacion_ov` VALUES (1,NULL,'2025-06-04',82.00,2),(2,NULL,'2025-06-04',83.00,3),(3,'Se necesita que el estudiante sea más responsable.','2025-06-04',76.00,4),(4,NULL,'2025-06-04',79.00,5),(5,'Excelente estudiante.','2025-06-04',84.00,6);
/*!40000 ALTER TABLE `evaluacion_ov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expediente`
--

DROP TABLE IF EXISTS `expediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expediente` (
  `idExpediente` int NOT NULL AUTO_INCREMENT,
  `estatus` enum('Activo','Baja','Concluido') NOT NULL,
  `horasAcumuladas` int NOT NULL,
  `idProyecto` int NOT NULL,
  `idPeriodo` int NOT NULL,
  `idEstudiante` int NOT NULL,
  PRIMARY KEY (`idExpediente`),
  KEY `idx_expediente_idproyecto` (`idProyecto`),
  KEY `fk_expediente_periodo1_idx` (`idPeriodo`),
  KEY `fk_expediente_estudiante1_idx` (`idEstudiante`),
  CONSTRAINT `expediente_ibfk_1` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`),
  CONSTRAINT `fk_expediente_estudiante1` FOREIGN KEY (`idEstudiante`) REFERENCES `estudiante` (`idUsuario`),
  CONSTRAINT `fk_expediente_periodo1` FOREIGN KEY (`idPeriodo`) REFERENCES `periodo` (`idPeriodo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expediente`
--

LOCK TABLES `expediente` WRITE;
/*!40000 ALTER TABLE `expediente` DISABLE KEYS */;
INSERT INTO `expediente` VALUES (1,'Baja',20,3,5,19),(2,'Activo',440,1,6,7),(3,'Activo',440,2,6,15),(4,'Activo',440,4,6,19),(5,'Activo',440,5,6,23),(6,'Activo',440,1,6,24);
/*!40000 ALTER TABLE `expediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiencia_educativa`
--

DROP TABLE IF EXISTS `experiencia_educativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experiencia_educativa` (
  `idExperiencia` int NOT NULL AUTO_INCREMENT,
  `bloque` int NOT NULL,
  `seccion` int NOT NULL,
  `nrc` int NOT NULL,
  `idPeriodo` int NOT NULL,
  `idAcademico` int NOT NULL,
  PRIMARY KEY (`idExperiencia`),
  KEY `idx_experiencia_idperiodo` (`idPeriodo`),
  KEY `fk_experiencia_educativa_academico1_idx` (`idAcademico`),
  CONSTRAINT `experiencia_ibfk_1` FOREIGN KEY (`idPeriodo`) REFERENCES `periodo` (`idPeriodo`),
  CONSTRAINT `fk_experiencia_educativa_academico1` FOREIGN KEY (`idAcademico`) REFERENCES `academico` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiencia_educativa`
--

LOCK TABLES `experiencia_educativa` WRITE;
/*!40000 ALTER TABLE `experiencia_educativa` DISABLE KEYS */;
INSERT INTO `experiencia_educativa` VALUES (1,7,1,78494,1,8),(2,7,1,78424,2,9),(3,7,1,78002,3,8),(4,7,1,71641,4,11),(5,7,2,78464,5,25),(6,7,1,78912,6,26);
/*!40000 ALTER TABLE `experiencia_educativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizacion_vinculada`
--

DROP TABLE IF EXISTS `organizacion_vinculada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizacion_vinculada` (
  `idOrganizacionVinculada` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`idOrganizacionVinculada`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizacion_vinculada`
--

LOCK TABLES `organizacion_vinculada` WRITE;
/*!40000 ALTER TABLE `organizacion_vinculada` DISABLE KEYS */;
INSERT INTO `organizacion_vinculada` VALUES (1,'EPAM','2282345678','Faltante','epam@epam.com'),(2,'HighTek','2282836940','Faltante','hightek@hightek.com'),(3,'Grupo PerTI','2282841541','Faltante','grupoperti@grupoperti.com'),(4,'Tata','2281234567','Faltante','tata@tata.com'),(5,'SAP','2282836147','Faltante','sap@sap.com');
/*!40000 ALTER TABLE `organizacion_vinculada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodo` (
  `idPeriodo` int NOT NULL AUTO_INCREMENT,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`idPeriodo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo`
--

LOCK TABLES `periodo` WRITE;
/*!40000 ALTER TABLE `periodo` DISABLE KEYS */;
INSERT INTO `periodo` VALUES (1,'2020-02-10','2020-06-04','Febrero-Julio 20'),(2,'2021-02-10','2021-06-04','Febrero-Julio 21'),(3,'2022-02-10','2022-06-04','Febrero-Julio 22'),(4,'2023-02-10','2023-06-04','Febrero-Julio 23'),(5,'2024-02-10','2024-06-04','Febrero-Julio 24'),(6,'2025-02-10','2025-06-04','Febrero-Julio 25');
/*!40000 ALTER TABLE `periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyecto` (
  `idProyecto` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `idResponsableDeProyecto` int NOT NULL,
  `idCoordinador` int NOT NULL,
  `objetivos` varchar(255) NOT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `idx_proyecto_idresponsabledeproyecto` (`idResponsableDeProyecto`),
  KEY `fk_proyecto_coordinador1_idx` (`idCoordinador`),
  CONSTRAINT `fk_proyecto_coordinador1` FOREIGN KEY (`idCoordinador`) REFERENCES `coordinador` (`idUsuario`),
  CONSTRAINT `proyecto_ibfk_2` FOREIGN KEY (`idResponsableDeProyecto`) REFERENCES `responsable_de_proyecto` (`idResponsableDeProyecto`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
INSERT INTO `proyecto` VALUES (1,'Plataforma de monitoreo y optimización de rendimiento para infraestructuras tecnológicas.','2025-02-11','2025-06-04','QuantumFlow',2,17,'Faltante'),(2,'Sistema de logística inteligente con trazabilidad en tiempo real para cadenas de suministro.','2025-02-11','2025-06-04','SmartLogix',4,17,'Faltante'),(3,'Suite modular ERP para pequeñas y medianas empresas enfocada en contabilidad, inventario y recursos humanos.','2024-02-11','2024-06-04','PerTISuite ERP',3,21,'Faltante'),(4,'Un sistema inteligente de integración de datos y automatización de procesos basado en IA.','2025-02-11','2025-06-04','NeuroSync',1,17,'Faltante'),(5,'Solución de análisis predictivo y visualización de KPIs empresariales integrada con SAP HANA.','2025-02-11','2025-06-04','VisionOps',5,20,'Faltante'),(6,'Desarrollo de App Móvil para Gestión de Eventos','2025-01-15','2025-07-28','App Eventos',6,32,'Faltante'),(7,'Seguridad informática en dispositivos locales de host','2025-01-15','2025-07-28','Seguridad de host',5,32,'Faltantes'),(8,'Desarrollo de sistemas web con HTML y PHP','2025-01-15','2025-07-28','Desarrollo web',1,17,'Faltante'),(9,'Un proyecto donde se requiere de conocimiento critíco de la situación actual de la comunidad, y en sus soluciones.','2025-02-04','2025-06-29','FastForward Software',2,12,'Realizar el diseño de base de datos del proyecto FastForward Software.\nQue el enseñado comprenda las relaciones del entorno y el software.');
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_mensual`
--

DROP TABLE IF EXISTS `reporte_mensual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_mensual` (
  `idReporteMensual` int NOT NULL AUTO_INCREMENT,
  `numeroReporteMensual` int NOT NULL,
  `numeroHoras` double NOT NULL,
  `observaciones` varchar(200) DEFAULT NULL,
  `nombreArchivo` varchar(100) NOT NULL,
  `extensionArchivo` varchar(10) NOT NULL,
  `archivo` longblob,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idReporteMensual`),
  KEY `idx_entrega_reporte_mensual_idexpediente` (`idExpediente`),
  CONSTRAINT `entrega_reporte_mensual_ibfk_2` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_mensual`
--

LOCK TABLES `reporte_mensual` WRITE;
/*!40000 ALTER TABLE `reporte_mensual` DISABLE KEYS */;
INSERT INTO `reporte_mensual` VALUES (1,1,85,NULL,'ReporteFebrero','pdf',NULL,2),(2,2,103,NULL,'ReporteMarzo','pdf',NULL,2),(3,3,120,NULL,'ReporteAbril','pdf',NULL,2),(4,4,132,NULL,'ReporteMayo','pdf',NULL,2),(5,1,70,NULL,'ReporteFebrero','pdf',NULL,3),(6,2,110,NULL,'ReporteMarzo','pdf',NULL,3),(7,3,138,NULL,'ReporteAbril','pdf',NULL,3),(8,4,122,NULL,'ReporteMayo','pdf',NULL,3),(9,1,75,NULL,'ReporteFebrero','pdf',NULL,4),(10,2,109,NULL,'ReporteMarzo','pdf',NULL,4),(11,3,133,NULL,'ReporteAbril','pdf',NULL,4),(12,4,123,NULL,'ReporteMayo','pdf',NULL,4),(13,1,87,NULL,'ReporteFebrero','pdf',NULL,5),(14,2,107,NULL,'ReporteMarzo','pdf',NULL,5),(15,3,129,NULL,'ReporteAbril','pdf',NULL,5),(16,4,117,NULL,'ReporteMayo','pdf',NULL,5),(17,1,77,NULL,'ReporteFebrero','pdf',NULL,6),(18,2,109,NULL,'ReporteMarzo','pdf',NULL,6),(19,3,137,NULL,'ReporteAbril','pdf',NULL,6),(20,4,117,NULL,'ReporteMayo','pdf',NULL,6);
/*!40000 ALTER TABLE `reporte_mensual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsable_de_proyecto`
--

DROP TABLE IF EXISTS `responsable_de_proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsable_de_proyecto` (
  `idResponsableDeProyecto` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `idOrganizacionVinculada` int NOT NULL,
  `departamento` varchar(50) NOT NULL,
  `puesto` varchar(50) NOT NULL,
  PRIMARY KEY (`idResponsableDeProyecto`),
  KEY `idx_responsable_de_proyecto_idorganizacionvinculada` (`idOrganizacionVinculada`),
  CONSTRAINT `responsable_de_proyecto_ibfk_1` FOREIGN KEY (`idOrganizacionVinculada`) REFERENCES `organizacion_vinculada` (`idOrganizacionVinculada`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsable_de_proyecto`
--

LOCK TABLES `responsable_de_proyecto` WRITE;
/*!40000 ALTER TABLE `responsable_de_proyecto` DISABLE KEYS */;
INSERT INTO `responsable_de_proyecto` VALUES (1,'robinson.jg@epam.com','John G. Robinson','2282456781',1,'Recursos Humanos','Especialista de Adquisición de Talento'),(2,'gomez.xd@hightek.com','Daniel Gómez Xolot','2288744557',2,'Recursos Humanos','Especialista de Adquisición de Talento'),(3,'sofia.hv@grupoperti.com','Sofía Herrera Velez','2283456598',3,'Recursos Humanos','Especialista de Adquisición de Talento'),(4,'drequansmith@tata.com','Drequan Smith','2284539724',4,'Recursos Humanos','Especialista de Adquisición de Talento'),(5,'karim.hafs@sap.com','Karim Hafstanovic','2289764041',5,'Recursos Humanos','Especialista de Adquisición de Talento'),(6,'javierA@gmail.com','Javier Aguirre','228105792',4,'Recursos Humanos','Especialista de Adquisición de Talento');
/*!40000 ALTER TABLE `responsable_de_proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultado_evaluacion_ov`
--

DROP TABLE IF EXISTS `resultado_evaluacion_ov`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resultado_evaluacion_ov` (
  `idResultadoEvaluacionOV` int NOT NULL AUTO_INCREMENT,
  `idCriterioEvaluacionOV` int NOT NULL,
  `idAfirmacionEvaluacionOV` int NOT NULL,
  `idEvaluacionOV` int NOT NULL,
  PRIMARY KEY (`idResultadoEvaluacionOV`),
  KEY `fk_resultado_evaluacion_ov_criterio_evaluacion_ov1_idx` (`idCriterioEvaluacionOV`),
  KEY `fk_resultado_evaluacion_ov_afirmacion_evaluacion_ov1_idx` (`idAfirmacionEvaluacionOV`),
  KEY `fk_resultado_evaluacion_ov_evaluacion_ov1_idx` (`idEvaluacionOV`),
  CONSTRAINT `fk_resultado_evaluacion_ov_afirmacion_evaluacion_ov1` FOREIGN KEY (`idAfirmacionEvaluacionOV`) REFERENCES `afirmacion_evaluacion_ov` (`idAfirmacionEvaluacionOV`),
  CONSTRAINT `fk_resultado_evaluacion_ov_criterio_evaluacion_ov1` FOREIGN KEY (`idCriterioEvaluacionOV`) REFERENCES `criterio_evaluacion_ov` (`idCriterioEvaluacionOV`),
  CONSTRAINT `fk_resultado_evaluacion_ov_evaluacion_ov1` FOREIGN KEY (`idEvaluacionOV`) REFERENCES `evaluacion_ov` (`idEvaluacionOV`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultado_evaluacion_ov`
--

LOCK TABLES `resultado_evaluacion_ov` WRITE;
/*!40000 ALTER TABLE `resultado_evaluacion_ov` DISABLE KEYS */;
INSERT INTO `resultado_evaluacion_ov` VALUES (1,4,1,1),(2,4,2,1),(3,4,3,1),(4,4,4,1),(5,4,5,1),(6,4,6,1),(7,4,7,1),(8,4,8,1),(9,4,9,1),(10,4,10,1),(11,5,11,1),(12,4,12,1),(13,4,13,1),(14,4,14,1),(15,5,15,1),(16,4,16,1),(17,4,17,1),(18,4,18,1),(19,4,19,1),(20,4,20,1),(21,4,1,2),(22,4,2,2),(23,4,3,2),(24,4,4,2),(25,4,5,2),(26,4,6,2),(27,4,7,2),(28,4,8,2),(29,4,9,2),(30,4,10,2),(31,5,11,2),(32,4,12,2),(33,5,13,2),(34,4,14,2),(35,5,15,2),(36,4,16,2),(37,4,17,2),(38,4,18,2),(39,4,19,2),(40,4,20,2),(41,3,1,3),(42,3,2,3),(43,3,3,3),(44,4,4,3),(45,4,5,3),(46,3,6,3),(47,3,7,3),(48,3,8,3),(49,4,9,3),(50,4,10,3),(51,5,11,3),(52,4,12,3),(53,4,13,3),(54,4,14,3),(55,5,15,3),(56,4,16,3),(57,4,17,3),(58,4,18,3),(59,4,19,3),(60,4,20,3),(61,4,1,4),(62,2,2,4),(63,3,3,4),(64,4,4,4),(65,4,5,4),(66,4,6,4),(67,4,7,4),(68,4,8,4),(69,4,9,4),(70,4,10,4),(71,5,11,4),(72,4,12,4),(73,4,13,4),(74,4,14,4),(75,5,15,4),(76,4,16,4),(77,4,17,4),(78,4,18,4),(79,4,19,4),(80,4,20,4),(81,4,1,5),(82,4,2,5),(83,4,3,5),(84,4,4,5),(85,4,5,5),(86,4,6,5),(87,4,7,5),(88,5,8,5),(89,4,9,5),(90,5,10,5),(91,5,11,5),(92,4,12,5),(93,4,13,5),(94,4,14,5),(95,5,15,5),(96,4,16,5),(97,4,17,5),(98,4,18,5),(99,4,19,5),(100,4,20,5);
/*!40000 ALTER TABLE `resultado_evaluacion_ov` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellidoPaterno` varchar(255) NOT NULL,
  `apellidoMaterno` varchar(255) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (7,'Laura','Gómez','Ramírez','laura.gomez@gmail.com','laurag','pass1234'),(8,'Carlos','Mendoza','Torres','carlos.mtz@outlook.com','cmendoza','qwerty21'),(9,'Ana','López','García','ana.lopez@gmail.com','anitaxd','123abcDEF'),(10,'Jorge','Hérnandez','Silva','jorge.hs@hotmail.com','jhernandez','9876pass'),(11,'Mariana','Rojas','Pérez','mariana.rp@gmail.com','marianita','mRojas#2025'),(12,'Luis','Sánchez','Aguilar','luis.sa@gmail.com','sanluis','ls@2024'),(13,'Daniela','Flores','Ortega','daniela.fo@outlook.com','dflor','dfoPass!'),(14,'Pedro','Ramírez','Castro','pedro.rc@yahoo.com','pramirez','passPedro1'),(15,'Andrea','Morales','Herrera','andrea.mh@gmail.com','andym','amor2025'),(16,'Martín','Vargas','Díaz','martin.vd@gmail.com','mvargas','mart1nD'),(17,'Fernanda','Torres','Gómez','fernanda.tg@gmail.com','fer_tg','torres123'),(18,'Diego','Ruiz','Martínez','diego.rm@gmail.com','diegor','drMtz456'),(19,'Paulina','Reyes','Soto','paulina.rs@hotmail.com','pauli_rey','ps2024!'),(20,'Ricardo','Domínguez','Morales','ricardo.dm@hotmail.com','rdominguez','rdmz#321'),(21,'Sofía','Castillo','Navarro','sofia.cn@outlook.com','scastillo','castilloS1'),(22,'Alan','Romero','Peña','alan.rp@outlook.com','aromero','alan4321'),(23,'Valeria','Delgado','Cruz','valeria.dc@gmail.com','valedel','vd*pass98'),(24,'Enrique','Salinas','Mejía','enrique.sm@gmail.com','ensalinas','enrique789'),(25,'Karen','Paredes','León','karen.pl@gmail.com','karenp','plK2025'),(26,'Tomás','Cordero','Ibarra','tomas.ci@gmail.com','tcordero','tomib@321'),(28,'Justin','Navarro','Silva','JNava@gmail.com','jusnava','navasil'),(29,'Monica','Illescas','Luna','monille@gmail.com','monille','monilu'),(30,'Karla','Figueroa','Fernández','karlaFF@gmail.com','karlaFF','fifer'),(31,'Juan','Hernández','Reyes','juanHR@gmail.com','jhern','hereyes'),(32,'Tomás','Silva','Pereira','silvaP@gmail.com','tomS','silvaper');
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

-- Dump completed on 2025-06-10 10:57:39
