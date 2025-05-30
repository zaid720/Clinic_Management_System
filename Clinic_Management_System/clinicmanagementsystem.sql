-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2025 at 07:11 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clinicmanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_appointments`
--

CREATE TABLE `tbl_appointments` (
  `ID` int(11) NOT NULL,
  `Patient_id` int(11) NOT NULL,
  `Doctor_id` int(11) NOT NULL,
  `Appointment_date` date NOT NULL,
  `Appointment_time` time NOT NULL,
  `Status` enum('محجوز','ملغي','مكتمل','قادم') NOT NULL,
  `Created_at` datetime NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_appointments`
--

INSERT INTO `tbl_appointments` (`ID`, `Patient_id`, `Doctor_id`, `Appointment_date`, `Appointment_time`, `Status`, `Created_at`, `Active`) VALUES
(2, 1, 9, '2025-05-31', '14:30:51', 'محجوز', '2025-05-20 13:30:51', 0),
(3, 5, 9, '2025-05-13', '03:20:26', 'ملغي', '2025-05-20 13:36:01', 0),
(6, 5, 9, '2025-05-31', '14:38:55', 'قادم', '2025-05-27 09:39:07', 0),
(7, 5, 9, '2025-05-31', '11:46:58', 'ملغي', '2025-05-27 14:47:11', 0),
(8, 1, 9, '2025-05-31', '15:03:13', 'قادم', '2025-05-27 15:03:40', 0),
(9, 5, 9, '2025-05-24', '08:34:59', 'مكتمل', '2025-05-28 09:35:08', 0),
(10, 7, 9, '2025-05-31', '08:36:54', 'قادم', '2025-05-29 08:37:10', 0),
(11, 8, 24, '2025-05-29', '09:18:01', 'محجوز', '2025-05-29 09:18:16', 1),
(12, 9, 24, '2025-05-29', '16:05:25', 'محجوز', '2025-05-29 16:05:30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_patients`
--

CREATE TABLE `tbl_patients` (
  `ID` int(11) NOT NULL,
  `Full_name` varchar(100) NOT NULL,
  `Age` int(2) NOT NULL,
  `Gender` enum('Male','Female') NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(100) NOT NULL,
  `Medication_notes` text NOT NULL,
  `Created_at` datetime NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_patients`
--

INSERT INTO `tbl_patients` (`ID`, `Full_name`, `Age`, `Gender`, `Phone`, `Address`, `Medication_notes`, `Created_at`, `Active`) VALUES
(1, 'أحمد', 43, 'Male', '778945612', 'صنعاء', 'لديه مرض السكر', '2025-05-20 13:08:26', 0),
(2, 'فاطمة', 10, 'Female', '789456123', 'صنعاء', 'بعد كل وجبة', '2025-05-25 11:18:26', 0),
(3, 'الزهراء', 15, 'Female', '789456123', 'صنعاء', 'حبة كل يوم', '2025-05-25 15:35:42', 0),
(4, 'هاشم محمد', 15, 'Male', '789456123', 'صنعاء', 'لا يوجد', '2025-05-25 15:54:08', 0),
(5, 'هشام', 15, 'Male', '789456123', 'صنعاء', 'لا يوجد', '2025-05-25 15:56:08', 0),
(6, 'علي', 45, 'Male', '789456123', 'ليبس', 'لبييسلب', '2025-05-28 09:34:23', 0),
(7, 'ahmed', 34, 'Male', '123847219', 'sana\'a', 'hello', '2025-05-29 08:36:49', 0),
(8, 'ali mohammed', 45, 'Male', '415243625', 'sana\'a', 'no think', '2025-05-29 09:17:23', 1),
(9, 'فاطمة', 45, 'Female', '', 'سشيب', 'يسشبسشي', '2025-05-29 16:05:21', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_prescriptions`
--

CREATE TABLE `tbl_prescriptions` (
  `ID` int(11) NOT NULL,
  `Visit_id` int(11) NOT NULL,
  `Medicatoin` text NOT NULL,
  `Dosage` varchar(50) NOT NULL,
  `Duration` varchar(50) NOT NULL,
  `Created_at` datetime NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_prescriptions`
--

INSERT INTO `tbl_prescriptions` (`ID`, `Visit_id`, `Medicatoin`, `Dosage`, `Duration`, `Created_at`, `Active`) VALUES
(1, 2, 'heelo', 'af', 'dsafsa', '2025-05-30 09:23:36', 0),
(2, 3, 'dsafsadfsdfadsfdsafdsaf', 'dsaf', 'dsaf', '2025-05-30 10:59:45', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `ID` int(11) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(150) NOT NULL,
  `Role` enum('Admin','User','Doctor') NOT NULL DEFAULT 'User',
  `Full_name` varchar(100) DEFAULT NULL,
  `Phone` varchar(20) NOT NULL,
  `Created_at` datetime NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`ID`, `Username`, `Password`, `Role`, `Full_name`, `Phone`, `Created_at`, `Active`) VALUES
(3, 'أحمد', '123', 'Admin', 'أحمد محمد', '789456123', '2025-05-18 16:17:36', 0),
(7, 'محمد', '123', 'User', 'محمد أحمد', '779456123', '2025-05-18 16:18:25', 0),
(9, 'ali', 'ali123qwali', 'Doctor', 'ali mohammed', '789654123', '2025-05-20 13:10:31', 1),
(10, 'Amjed', 'ali', 'Admin', '', '123456789', '2025-05-28 17:27:47', 1),
(22, 'Ahmed', 'ali123ali', 'Admin', '', '778945612', '2025-05-28 17:45:21', 1),
(23, 'ali3', 'asdf', 'Admin', '', '789456455', '2025-05-29 08:08:21', 0),
(24, 'mohammed', 'mohammed', 'Doctor', 'mohammed ahmed', '482759832', '2025-05-29 09:14:32', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_visits`
--

CREATE TABLE `tbl_visits` (
  `ID` int(11) NOT NULL,
  `Appointment_id` int(11) NOT NULL,
  `Visit_date` date NOT NULL,
  `Diagnosis` text NOT NULL,
  `Notes` text DEFAULT NULL,
  `Created_at` datetime NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_visits`
--

INSERT INTO `tbl_visits` (`ID`, `Appointment_id`, `Visit_date`, `Diagnosis`, `Notes`, `Created_at`, `Active`) VALUES
(2, 9, '2025-05-31', 'تغيير الدواء', 'لديه تحسن كبير', '2025-05-28 10:44:48', 0),
(3, 11, '2025-05-31', 'لا أعلم', 'لا شيء', '2025-05-29 15:26:31', 1),
(4, 12, '2025-05-29', 'adsf', 'safsd', '2025-05-29 15:35:33', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Patient_id` (`Patient_id`),
  ADD KEY `Doctor_id` (`Doctor_id`);

--
-- Indexes for table `tbl_patients`
--
ALTER TABLE `tbl_patients`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_prescriptions`
--
ALTER TABLE `tbl_prescriptions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Visit_id` (`Visit_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD UNIQUE KEY `Phone` (`Phone`);

--
-- Indexes for table `tbl_visits`
--
ALTER TABLE `tbl_visits`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Appointment_id` (`Appointment_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_patients`
--
ALTER TABLE `tbl_patients`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tbl_prescriptions`
--
ALTER TABLE `tbl_prescriptions`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `tbl_visits`
--
ALTER TABLE `tbl_visits`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_appointments`
--
ALTER TABLE `tbl_appointments`
  ADD CONSTRAINT `tbl_appointments_ibfk_1` FOREIGN KEY (`Patient_id`) REFERENCES `tbl_patients` (`ID`),
  ADD CONSTRAINT `tbl_appointments_ibfk_2` FOREIGN KEY (`Doctor_id`) REFERENCES `tbl_users` (`ID`);

--
-- Constraints for table `tbl_prescriptions`
--
ALTER TABLE `tbl_prescriptions`
  ADD CONSTRAINT `tbl_prescriptions_ibfk_1` FOREIGN KEY (`Visit_id`) REFERENCES `tbl_visits` (`ID`);

--
-- Constraints for table `tbl_visits`
--
ALTER TABLE `tbl_visits`
  ADD CONSTRAINT `tbl_visits_ibfk_1` FOREIGN KEY (`Appointment_id`) REFERENCES `tbl_appointments` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
