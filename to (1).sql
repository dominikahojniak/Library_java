-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 05, 2024 at 05:05 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `to`
--

-- --------------------------------------------------------

--
-- Zastąpiona struktura widoku `1`
-- (See below for the actual view)
--
CREATE TABLE `1` (
`id` int(20)
,`name` varchar(40)
);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `audiobooks`
--

CREATE TABLE `audiobooks` (
  `resource_id` int(20) NOT NULL,
  `duration` bigint(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `audiobooks`
--

INSERT INTO `audiobooks` (`resource_id`, `duration`) VALUES
(27, 700),
(28, 850),
(29, 700);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `books`
--

CREATE TABLE `books` (
  `resource_id` int(20) NOT NULL,
  `page_count` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`resource_id`, `page_count`) VALUES
(8, 300),
(24, 345),
(25, 897),
(26, 432),
(35, 340);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `borrows`
--

CREATE TABLE `borrows` (
  `id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `resource_id` int(40) NOT NULL,
  `start_date` date NOT NULL,
  `return_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrows`
--

INSERT INTO `borrows` (`id`, `user_id`, `resource_id`, `start_date`, `return_date`) VALUES
(18, 1, 7, '2024-01-11', '2024-01-11'),
(23, 3, 7, '2024-01-11', '2024-01-11'),
(26, 7, 25, '2024-01-15', '2024-01-15');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `comics`
--

CREATE TABLE `comics` (
  `resource_id` int(20) NOT NULL,
  `illustrator` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comics`
--

INSERT INTO `comics` (`resource_id`, `illustrator`) VALUES
(30, 'Mike Deodato'),
(31, 'John Byrne'),
(32, 'David Messina');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `magazines`
--

CREATE TABLE `magazines` (
  `resource_id` int(20) NOT NULL,
  `issue_number` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `magazines`
--

INSERT INTO `magazines` (`resource_id`, `issue_number`) VALUES
(7, 1223),
(33, 25215991),
(34, 63009613);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ratings`
--

CREATE TABLE `ratings` (
  `id` int(20) NOT NULL,
  `user_id` int(40) NOT NULL,
  `resource_id` int(40) NOT NULL,
  `rate` int(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ratings`
--

INSERT INTO `ratings` (`id`, `user_id`, `resource_id`, `rate`) VALUES
(7, 7, 25, 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `resources`
--

CREATE TABLE `resources` (
  `id` int(20) NOT NULL,
  `title` varchar(40) NOT NULL,
  `author` varchar(40) NOT NULL,
  `type` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resources`
--

INSERT INTO `resources` (`id`, `title`, `author`, `type`) VALUES
(7, 'Vouge', 'A.P', 'magazine'),
(8, 'Witcher ', 'Andrzej Sapkowski', 'book'),
(24, 'Gdzie śpiewają raki', 'Delia Owens', 'book'),
(25, 'Małe życie', 'Hanya Yanagihara', 'book'),
(26, 'Atomowe nawyki', 'James Clear', 'book'),
(27, 'Ostatnie życzenie', 'Andrzej Sapkowski', 'audiobook'),
(28, 'Ekspozycja', 'Remigiusz Mróz', 'audiobook'),
(29, 'Yellowface', 'Rebecca Kuang', 'audiobook'),
(30, 'Amazing Spider-Man', 'J. Michael Straczynski', 'comic'),
(31, 'Zjawiskowa She-Hulk', 'John Byrne', 'comic'),
(32, 'Star Wars. Han Solo i Chewbacca', 'Marc Guggenheim', 'comic'),
(33, 'Vogue Polska', 'Opracowanie zbiorowe', 'magazine'),
(34, 'Twój Styl', 'Karolina Weber-Bęben  ', 'magazine'),
(35, 'dom', 'Ania', 'book');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`) VALUES
(1, 'ania'),
(3, 'adam'),
(6, 'Filip'),
(7, 'dominika');

-- --------------------------------------------------------

--
-- Struktura widoku `1`
--
DROP TABLE IF EXISTS `borrowed_books_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `borrowed_books_view`  AS SELECT `borrows`.`id` AS `borrow_id`, `users`.`id` AS `user_id`, `users`.`name` AS `user_name`, `resources`.`title` AS `resource_title`, `resources`.`author` AS `resource_author`, `borrows`.`start_date` AS `start_date`, `borrows`.`return_date` AS `return_date` FROM ((`borrows` join `users` on(`borrows`.`user_id` = `users`.`id`)) join `resources` on(`borrows`.`resource_id` = `resources`.`id`)) ;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `audiobooks`
--
ALTER TABLE `audiobooks`
  ADD KEY `id_audio` (`resource_id`);

--
-- Indeksy dla tabeli `books`
--
ALTER TABLE `books`
  ADD KEY `id_book` (`resource_id`);

--
-- Indeksy dla tabeli `borrows`
--
ALTER TABLE `borrows`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`user_id`),
  ADD KEY `resources_idsss` (`resource_id`);

--
-- Indeksy dla tabeli `comics`
--
ALTER TABLE `comics`
  ADD KEY `id_comics` (`resource_id`);

--
-- Indeksy dla tabeli `magazines`
--
ALTER TABLE `magazines`
  ADD KEY `idsss` (`resource_id`);

--
-- Indeksy dla tabeli `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userr` (`user_id`),
  ADD KEY `resources_id` (`resource_id`);

--
-- Indeksy dla tabeli `resources`
--
ALTER TABLE `resources`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `borrows`
--
ALTER TABLE `borrows`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `ratings`
--
ALTER TABLE `ratings`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `resources`
--
ALTER TABLE `resources`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `audiobooks`
--
ALTER TABLE `audiobooks`
  ADD CONSTRAINT `id_audio` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `id_book` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `borrows`
--
ALTER TABLE `borrows`
  ADD CONSTRAINT `id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `resources_idsss` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `comics`
--
ALTER TABLE `comics`
  ADD CONSTRAINT `id_comics` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `magazines`
--
ALTER TABLE `magazines`
  ADD CONSTRAINT `idsss` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `ratings`
--
ALTER TABLE `ratings`
  ADD CONSTRAINT `resources_id` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `userr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
