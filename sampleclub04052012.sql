-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 04, 2012 at 03:18 AM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Stores Member details' AUTO_INCREMENT=5 ;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`ID`, `FirstName`, `LastName`, `JoinDate`, `Position`, `Password`, `Address`, `Authority`) VALUES
(1, 'Abhijit', 'Pothula', '2012-04-03', 'President', 'ap', '', 1),
(2, 'Varun', 'Raja', '2012-05-01', 'Vice President', 'vr', '', 2),
(3, 'Arjun', 'Kumar', '2012-05-02', 'Publicity Head', 'ak', '', 2),
(4, 'Saumya', 'Solanki', '2012-05-02', 'Creative Head', 'ss', '', 4);

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
(1, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='Holds all notifications' AUTO_INCREMENT=2 ;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`NotificationId`, `Time`, `MemberIdFrom`, `Content`, `MemberFrom`) VALUES
(1, '2012-05-04 05:00:30', 1, 'Test Notification from the Evil Overlord', NULL);

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

--
-- Constraints for dumped tables
--

--
-- Constraints for table `notificationmember`
--
ALTER TABLE `notificationmember`
  ADD CONSTRAINT `notificationmember_ibfk_1` FOREIGN KEY (`MemberId`) REFERENCES `members` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notificationmember_ibfk_2` FOREIGN KEY (`NotificationId`) REFERENCES `notifications` (`NotificationId`) ON DELETE CASCADE ON UPDATE CASCADE;
