-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.28-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table thigk2.employees
CREATE TABLE IF NOT EXISTS `employees` (
  `ID` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `employee_type` enum('Experience','Fresher','Intern') DEFAULT NULL,
  `exp_in_year` int(11) DEFAULT NULL,
  `pro_skill` varchar(50) DEFAULT NULL,
  `graduation_date` date DEFAULT NULL,
  `graduation_rank` varchar(50) DEFAULT NULL,
  `education` varchar(50) DEFAULT NULL,
  `majors` varchar(50) DEFAULT NULL,
  `semester` varchar(50) DEFAULT NULL,
  `university_name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table thigk2.employees: ~3 rows (approximately)
INSERT INTO `employees` (`ID`, `full_name`, `birth_day`, `phone`, `email`, `employee_type`, `exp_in_year`, `pro_skill`, `graduation_date`, `graduation_rank`, `education`, `majors`, `semester`, `university_name`) VALUES
	('1', 'dfh', '2022-04-04', '7676667', 'dfhdgh', 'Intern', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('1323', 'dfh', '2002-03-03', '58589', 'dfh', 'Intern', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	('33', 'dfh', '2002-06-06', '4587', 'dfhdfgh', 'Experience', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
