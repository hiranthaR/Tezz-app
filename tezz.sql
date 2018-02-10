-- phpMyAdmin SQL Dump
-- version 4.7.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 10, 2018 at 03:36 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tezz`
--

-- --------------------------------------------------------

--
-- Table structure for table `fcmtoken`
--

CREATE TABLE `fcmtoken` (
  `id` int(20) NOT NULL,
  `token` varchar(400) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fcmtoken`
--

INSERT INTO `fcmtoken` (`id`, `token`) VALUES
(2, ''),
(6, 'dA9bxlZWvvo:APA91bEOtV75rHMGqUQoME5uTnnEOZduzWb4jgxutgbOXIjci1GFG4VODHQCboR5yKY-oDayjjebugNwEi4F22trgklEwIoFZFgNLFsQB-LpGaZaeYGmFmWfkUym_gxjAT6Uv7ncdcrb');

-- --------------------------------------------------------

--
-- Table structure for table `oders`
--

CREATE TABLE `oders` (
  `id` int(20) NOT NULL,
  `tid` int(20) NOT NULL,
  `cid` int(20) NOT NULL,
  `height` varchar(20) NOT NULL,
  `chest` varchar(20) NOT NULL,
  `details` varchar(150) NOT NULL,
  `side` mediumblob NOT NULL,
  `front` mediumblob NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `who` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `name`, `password`, `address`, `email`, `telephone`, `who`) VALUES
(6, 't', 'take', 'e', 'd', 'fd@scd.d', '1111441144', 1),
(5, 'h', 'hirantha', 'd', 'd', 'd@d.d', '1111111111', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `oders`
--
ALTER TABLE `oders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `oders`
--
ALTER TABLE `oders`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
