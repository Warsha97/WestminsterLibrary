-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2021 at 07:39 AM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_of_westminster`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `isbn` varchar(30) NOT NULL,
  `title` varchar(100) NOT NULL,
  `sector` varchar(100) NOT NULL,
  `author` varchar(200) NOT NULL,
  `publisher` varchar(100) NOT NULL,
  `publicationDate` varchar(100) NOT NULL,
  `numOfPages` int(30) NOT NULL,
  `currentReaderId` int(30) DEFAULT NULL,
  `burrowedDate` varchar(100) DEFAULT NULL,
  `burrowedTime` varchar(100) DEFAULT NULL,
  `returnDate` varchar(100) DEFAULT NULL,
  `returnTime` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`isbn`, `title`, `sector`, `author`, `publisher`, `publicationDate`, `numOfPages`, `currentReaderId`, `burrowedDate`, `burrowedTime`, `returnDate`, `returnTime`) VALUES
('111-22-33-44', 'Harry Potter and the Chamber Of secrets', 'Fantasy', 'J K Rowling', 'Bloomsbury', '12/3/2000', 650, 9, '', '', '', ''),
('333-44-5-6', 'The Best Of Me', 'Ramonace', 'Nicholas Sparks', 'ABCD publication', '12/3/2012', 850, 5, '18/12/2018', '15:40', '25/12/2018', '15:40'),
('444-2-3-4', 'Design Architecture', 'Academic', 'Peter Koronovskow', 'Pink Publications', '23/5/2016', 900, 9, '', '', '', ''),
('55-6-4-33', 'Head First Java-2nd edition', 'Academic', 'Kathy Sierra, Bert Bates', 'O\'Reilly Media', '20/5/2005', 720, 11, '15/12/2018', '13:00', '22/12/2018', '13:00'),
('777-11-22-33', 'Alice in the wonderland', 'Fantasy', 'authorX', 'ZZZ publishers', '4/8/2000', 800, 9, '', '', '', ''),
('88-11-22-33', 'book1', 'Fantasy', 'author1', 'aa', '2/6/2012', 900, 9, '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `dvds`
--

CREATE TABLE `dvds` (
  `isbn` varchar(30) NOT NULL,
  `title` varchar(100) NOT NULL,
  `sector` varchar(100) NOT NULL,
  `producer` varchar(200) NOT NULL,
  `actors` varchar(200) NOT NULL,
  `publicationDate` varchar(100) NOT NULL,
  `languages` varchar(200) NOT NULL,
  `subtitles` varchar(200) NOT NULL,
  `currentReaderId` int(30) DEFAULT NULL,
  `burrowedDate` varchar(100) DEFAULT NULL,
  `burrowedTime` varchar(100) DEFAULT NULL,
  `returnDate` varchar(100) DEFAULT NULL,
  `returnTime` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dvds`
--

INSERT INTO `dvds` (`isbn`, `title`, `sector`, `producer`, `actors`, `publicationDate`, `languages`, `subtitles`, `currentReaderId`, `burrowedDate`, `burrowedTime`, `returnDate`, `returnTime`) VALUES
('11-11-11-11', 'Mission Impossible', 'Thriller', 'producerA', 'Tom cruz', '23/5/2007', 'English, Spanish, French', 'English, Chinese, Russian', 5, '25/11/2018', '13:45', '28/11/2018', '13:45'),
('22-22-22', 'Fantastic Beasts-Crimes of Grindelwald', 'Fantacy', 'producerB', 'Jonny Depp', '8/11/2018', 'English, Spanish, French', 'English, Chinese, Russian', 9, '', '', '', ''),
('33-33-33-33', 'Avengers-Infinity War', 'Fantasy', 'Jim Lee', 'Benedict Cumberbach, Chris Patt', '12/3/2018', 'English, Spanish, Russian', 'English, Sinhala, Chinese', 12, '01/12/2018', '02:30', '4/12/2018', '02:30'),
('44-44-44-44', 'The Greatest Showman', 'Fantasy', 'Walt Disney', 'Hugh Jackman, Zac Efron, Zendya', '22/5/2017', 'English', 'English', 13, '17/12/2018', '12:25', '20/12/2018', '12:25'),
('55-55-55-55', 'The Lucky One', 'Romance', 'Summit', ' Zac Efron', '22/5/2017', 'English', 'English', 9, '', '', NULL, NULL),
('77-77-77', 'DVD1', 'adventure', 'producer1', 'actor1,actor2', '3/7/2000', 'english', 'english', 9, '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `readers`
--

CREATE TABLE `readers` (
  `readerId` int(30) NOT NULL,
  `readerName` varchar(100) NOT NULL,
  `readerTp` varchar(15) NOT NULL,
  `readerEmail` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `readers`
--

INSERT INTO `readers` (`readerId`, `readerName`, `readerTp`, `readerEmail`) VALUES
(3, 'Alex Dough', '0711234567', 'alexd@gmail.com'),
(4, 'Hrininzi Kurekuri', '0912345678', 'harinzi@gmail.com'),
(5, 'Namali Jayasinghe', '091988776654', 'namali@gmail.com'),
(6, 'John Peters', '0779876756', 'john@gmail.com'),
(7, 'Thomas Lewis', '0712233223', 'thomaslewis@gmail.com'),
(8, 'Hannah Baker', '07765438276', 'Hannah@gmail.com'),
(9, 'none', 'none', 'none'),
(10, 'Katy perry', '0987654565', 'Katy@gmail.com'),
(11, 'Taylor Swift', '0981234567', 'taytay@gmail.com'),
(12, 'Ed Sheeran', '09864738476', 'edsheeran@gmail.com'),
(13, 'Jarred Radnich', '098765432', 'jarr@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`isbn`),
  ADD KEY `currentReaderId` (`currentReaderId`);

--
-- Indexes for table `dvds`
--
ALTER TABLE `dvds`
  ADD PRIMARY KEY (`isbn`),
  ADD KEY `currentReaderId` (`currentReaderId`);

--
-- Indexes for table `readers`
--
ALTER TABLE `readers`
  ADD PRIMARY KEY (`readerId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `readers`
--
ALTER TABLE `readers`
  MODIFY `readerId` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`currentReaderId`) REFERENCES `readers` (`readerId`);

--
-- Constraints for table `dvds`
--
ALTER TABLE `dvds`
  ADD CONSTRAINT `dvds_ibfk_1` FOREIGN KEY (`currentReaderId`) REFERENCES `readers` (`readerId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
