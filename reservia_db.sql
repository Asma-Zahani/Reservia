-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 08 mai 2026 à 11:34
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `reservia_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `booking`
--

CREATE TABLE `booking` (
  `booking_date` date DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` enum('CANCELLED','COMPLETED','CONFIRMED','PENDING') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `booking`
--

INSERT INTO `booking` (`booking_date`, `total_price`, `version`, `id`, `user_id`, `status`) VALUES
('2026-05-01', 360, 0, 1, 2, 'CONFIRMED'),
('2026-05-02', 180, 0, 2, 3, 'CONFIRMED'),
('2026-05-03', 500, 0, 3, 3, 'PENDING'),
('2026-05-04', 220, 0, 4, 2, 'CONFIRMED'),
('2026-05-05', 390, 0, 5, 4, 'CANCELLED'),
('2026-05-06', 1000, 0, 6, 5, 'COMPLETED');

-- --------------------------------------------------------

--
-- Structure de la table `booking_extra_services`
--

CREATE TABLE `booking_extra_services` (
  `booking_id` bigint(20) NOT NULL,
  `extra_service_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `booking_extra_services`
--

INSERT INTO `booking_extra_services` (`booking_id`, `extra_service_id`) VALUES
(1, 1),
(1, 4),
(2, 2),
(3, 3),
(4, 1),
(4, 5),
(5, 4),
(6, 1),
(6, 2),
(6, 3);

-- --------------------------------------------------------

--
-- Structure de la table `booking_item`
--

CREATE TABLE `booking_item` (
  `end_date` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `booking_item`
--

INSERT INTO `booking_item` (`end_date`, `price`, `quantity`, `start_date`, `booking_id`, `id`, `room_id`) VALUES
('2026-05-05', 360, 3, '2026-05-02', 1, 19, 1),
('2026-05-03', 180, 1, '2026-05-02', 2, 20, 2),
('2026-05-08', 500, 2, '2026-05-06', 3, 21, 6),
('2026-05-10', 220, 1, '2026-05-09', 4, 22, 4),
('2026-05-12', 390, 2, '2026-05-10', 5, 23, 8),
('2026-05-15', 1000, 4, '2026-05-11', 6, 24, 3);

-- --------------------------------------------------------

--
-- Structure de la table `extra_service`
--

CREATE TABLE `extra_service` (
  `per_night` bit(1) NOT NULL,
  `price` double DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `extra_service`
--

INSERT INTO `extra_service` (`per_night`, `price`, `id`, `name`) VALUES
(b'1', 20, 1, 'Petit déjeuner'),
(b'0', 50, 2, 'Navette aéroport'),
(b'1', 35, 3, 'Spa'),
(b'0', 15, 4, 'Parking'),
(b'1', 25, 5, 'Salle de sport');

-- --------------------------------------------------------

--
-- Structure de la table `room`
--

CREATE TABLE `room` (
  `capacity` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `size` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `room`
--

INSERT INTO `room` (`capacity`, `price`, `size`, `id`, `image_path`, `type`, `description`) VALUES
(1, 120, 20, 1, '/images/rooms/room1.jpg', 'Simple', 'Chambre simple confortable'),
(2, 180, 30, 2, '/images/rooms/room2.jpg', 'Double', 'Chambre double avec balcon'),
(4, 350, 50, 3, '/images/rooms/room3.jpg', 'Suite', 'Suite familiale luxueuse'),
(2, 220, 35, 4, '/images/rooms/room4.jpg', 'Double', 'Vue sur mer'),
(1, 100, 18, 5, '/images/rooms/room5.jpg', 'Simple', 'Petit budget'),
(5, 500, 70, 6, '/images/rooms/room6.jpg', 'Suite Luxe', 'Suite premium avec jacuzzi'),
(2, 210, 32, 7, '/images/rooms/room7.jpg', 'Double', 'Chambre moderne'),
(3, 390, 55, 8, '/images/rooms/room8.jpg', 'Suite', 'Suite élégante');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `name`, `password`, `role`) VALUES
(1, 'admin@gmail.com', 'Admin', '$2a$10$yr3eWHaGcXrs/dOA4JZf/Oc3nrXDpI76SFp8pt8g7vSwgZNBcrxcq', 'ROLE_ADMIN'),
(2, 'mariemmokni2003@gmail.com', 'Mariem Mokni', '$2a$10$AjXk0QDAp/EAEE6n2WzIJ.xqkBoFhOjNogDEXEHcmXgNsRldCfA1i', 'ROLE_USER'),
(3, 'noor@gmail.com', 'Noor Graieb', '$2a$10$4IH3A0NqtJbujfSU7qqX8eBUglM6M/MQqkryq6y/Pd2OFbfCVFsny', 'ROLE_USER'),
(4, 'asma@gmail.com', 'Asma Zahni', '$2a$10$Wb9poTptvjJv5WaMxHGEIeA9ZHLNrV1YlqCo1SHr0njmKS9YvT5rW', 'ROLE_USER'),
(5, 'ahmed@gmail.com', 'Ahmed ali', '$2a$10$/5KN0/fJevZV29mL5ojom.d3HPiQvHGtxxDFBG0J/Ok0SxL/InCAy', 'ROLE_USER');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkgseyy7t56x7lkjgu3wah5s3t` (`user_id`);

--
-- Index pour la table `booking_extra_services`
--
ALTER TABLE `booking_extra_services`
  ADD KEY `FKtrve3bkbq80rs24vcohw14x4n` (`extra_service_id`),
  ADD KEY `FK9olwy16m4fc4gyfbr3ux7od73` (`booking_id`);

--
-- Index pour la table `booking_item`
--
ALTER TABLE `booking_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbqq9ms8palbasvo7ydxgrrpx` (`booking_id`),
  ADD KEY `FK949uj746r24911b0096r4f5iw` (`room_id`);

--
-- Index pour la table `extra_service`
--
ALTER TABLE `extra_service`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `booking_item`
--
ALTER TABLE `booking_item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `extra_service`
--
ALTER TABLE `extra_service`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FKkgseyy7t56x7lkjgu3wah5s3t` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `booking_extra_services`
--
ALTER TABLE `booking_extra_services`
  ADD CONSTRAINT `FK9olwy16m4fc4gyfbr3ux7od73` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
  ADD CONSTRAINT `FKtrve3bkbq80rs24vcohw14x4n` FOREIGN KEY (`extra_service_id`) REFERENCES `extra_service` (`id`);

--
-- Contraintes pour la table `booking_item`
--
ALTER TABLE `booking_item`
  ADD CONSTRAINT `FK949uj746r24911b0096r4f5iw` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  ADD CONSTRAINT `FKtbqq9ms8palbasvo7ydxgrrpx` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
