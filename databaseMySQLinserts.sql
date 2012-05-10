-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 10, 2012 at 01:24 PM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sampleclub`
--
DROP DATABASE `sampleclub`;
CREATE DATABASE `sampleclub` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sampleclub`;

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
  `EventId` int(11) NOT NULL AUTO_INCREMENT,
  `EventName` text NOT NULL,
  `DateFrom` date NOT NULL,
  `DateTo` date NOT NULL,
  `MiscInfo` text,
  `Members` text NOT NULL,
  PRIMARY KEY (`EventId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Holds info on all events' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `events`
--


-- --------------------------------------------------------

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` text NOT NULL,
  `LastName` text NOT NULL,
  `JoinDate` date NOT NULL,
  `Position` text NOT NULL,
  `Password` text NOT NULL,
  `Address` text,
  `Authority` int(11) NOT NULL DEFAULT '4',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Stores Member details' AUTO_INCREMENT=13 ;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`ID`, `FirstName`, `LastName`, `JoinDate`, `Position`, `Password`, `Address`, `Authority`) VALUES
(1, 'Abhijit', 'Pothula', '2012-04-03', 'President', 'ap', '', 1),
(2, 'Varun', 'Raja', '2012-05-01', 'Vice President', 'vr', '', 2),
(3, 'Arjun', 'Kumar', '2012-05-02', 'Publicity Head', 'ak', '', 2),
(4, 'Saumya', 'Solanki', '2012-05-02', 'Creative Head', 'ss', '', 2),
(5, 'Harshya', 'Vardhan', '2012-03-05', 'Management Committee', 'hv', 'Block 14', 3),
(6, 'Kirat', 'Saluja', '2012-05-03', 'Management Committee', 'ks', 'Block 9', 3),
(9, 'Astha', 'Jayakar', '2012-05-02', 'Member', 'aj', 'Block 12', 4),
(11, 'Shaunak', 'Kulhali', '2012-03-03', 'Vice President', 'sk', 'Block 14', 2),
(12, 'Other name', 'lastName', '2012-05-05', 'Vice President', 'ol', 'Block 13', 2);

-- --------------------------------------------------------

--
-- Table structure for table `notificationmember`
--

DROP TABLE IF EXISTS `notificationmember`;
CREATE TABLE IF NOT EXISTS `notificationmember` (
  `NotificationId` int(11) NOT NULL,
  `MemberId` int(11) NOT NULL,
  PRIMARY KEY (`NotificationId`,`MemberId`),
  KEY `MemberId` (`MemberId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notificationmember`
--

INSERT INTO `notificationmember` (`NotificationId`, `MemberId`) VALUES
(13, 0),
(18, 0),
(17, 1),
(15, 2),
(16, 2),
(14, 3),
(19, 4),
(20, 8);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `NotificationId` int(11) NOT NULL AUTO_INCREMENT,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MemberIdFrom` int(11) NOT NULL,
  `Content` text,
  `MemberFrom` text,
  PRIMARY KEY (`NotificationId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Holds all notifications' AUTO_INCREMENT=21 ;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`NotificationId`, `Time`, `MemberIdFrom`, `Content`, `MemberFrom`) VALUES
(13, '2012-05-05 04:22:45', 1, 'Test public notification to everyone.', 'Abhijit'),
(14, '2012-05-05 04:23:05', 1, 'Test private notification to Arjun.', 'Abhijit'),
(15, '2012-05-05 04:26:11', 1, 'Test private note to Varun.', 'Abhijit'),
(16, '2012-05-05 04:26:16', 1, 'Test private note to Varun.', 'Abhijit'),
(17, '2012-05-05 06:06:54', 2, 'Test private message to Abhijit.', 'Varun'),
(18, '2012-05-05 06:22:39', 3, 'Test publicity notification to everyone.', 'Arjun'),
(19, '2012-05-05 06:23:28', 3, 'Saumya please submit your art for publicity at the earliest.', 'Arjun'),
(20, '2012-05-05 10:48:47', 1, 'Private notification to yogesh.', 'Abhijit');

--
-- Triggers `notifications`
--
DROP TRIGGER IF EXISTS `before_ins_notifications`;
DELIMITER //
CREATE TRIGGER `before_ins_notifications` BEFORE INSERT ON `notifications`
 FOR EACH ROW BEGIN

DECLARE m varchar(50);
SELECT FirstName INTO m FROM members WHERE ID = NEW.MemberIdFrom;
SET NEW.MemberFrom = m;

END
//
DELIMITER ;
