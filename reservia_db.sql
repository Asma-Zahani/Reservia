-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 mai 2026 à 07:27
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

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
-- Structure de la table `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `booking_date` date DEFAULT NULL,
  `status` enum('CANCELLED','COMPLETED','CONFIRMED','PAID','PENDING') DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `bookings`
--

INSERT INTO `bookings` (`id`, `booking_date`, `status`, `total_price`, `version`, `user_id`) VALUES
(1, '2026-05-12', 'CONFIRMED', 985, 1, 2),
(2, '2026-05-12', 'PENDING', 560, 1, 3),
(3, '2026-05-12', 'COMPLETED', 1340, 1, 4),
(4, '2026-05-12', 'CANCELLED', 320, 1, 2),
(5, '2026-05-12', 'CONFIRMED', 1280, 1, 3),
(6, '2026-05-12', 'CONFIRMED', 760, 1, 4),
(7, '2026-05-12', 'PENDING', 540, 1, 5),
(8, '2026-05-12', 'CONFIRMED', 2150, 1, 3),
(9, '2026-05-12', 'COMPLETED', 890, 1, 4),
(10, '2026-05-12', 'CONFIRMED', 470, 1, 5),
(11, '2026-05-12', 'PENDING', 980, 1, 3),
(12, '2026-05-12', 'CONFIRMED', 1560, 1, 4),
(13, '2026-05-12', 'CANCELLED', 620, 1, 5),
(14, '2026-05-12', 'CONFIRMED', 740, 1, 3),
(15, '2026-05-11', 'COMPLETED', 1350, 1, 4),
(16, '2026-05-10', 'COMPLETED', 990, 1, 5),
(17, '2026-05-09', 'CANCELLED', 430, 1, 3),
(18, '2026-05-13', 'PENDING', 1600, 1, 4),
(19, '2026-05-14', 'CONFIRMED', 1850, 1, 5);

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
(1, 3),
(2, 4),
(3, 1),
(3, 2),
(3, 3),
(4, 5),
(5, 1),
(5, 3),
(6, 4),
(7, 1),
(7, 5),
(8, 1),
(8, 2),
(8, 3),
(9, 3),
(10, 4),
(11, 1),
(11, 3),
(12, 2),
(12, 4),
(14, 1),
(18, 1),
(18, 3),
(19, 1),
(19, 2),
(19, 3);

-- --------------------------------------------------------

--
-- Structure de la table `booking_items`
--

CREATE TABLE `booking_items` (
  `id` bigint(20) NOT NULL,
  `end_date` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `booking_items`
--

INSERT INTO `booking_items` (`id`, `end_date`, `price`, `quantity`, `start_date`, `booking_id`, `room_id`) VALUES
(1, '2026-05-16', 320, 1, '2026-05-13', 1, 1),
(2, '2026-05-15', 220, 1, '2026-05-12', 2, 2),
(3, '2026-05-17', 450, 1, '2026-05-06', 3, 4),
(4, '2026-05-14', 320, 1, '2026-05-13', 4, 1),
(5, '2026-05-15', 640, 1, '2026-05-12', 5, 6),
(6, '2026-05-14', 380, 1, '2026-05-12', 6, 2),
(7, '2026-05-16', 540, 1, '2026-05-12', 7, 3),
(8, '2026-05-17', 2150, 1, '2026-05-12', 8, 20),
(9, '2026-05-14', 890, 1, '2026-05-12', 9, 8),
(10, '2026-05-13', 470, 1, '2026-05-12', 10, 18),
(11, '2026-05-15', 980, 1, '2026-05-12', 11, 13),
(12, '2026-05-18', 1560, 1, '2026-05-12', 12, 17),
(13, '2026-05-14', 620, 1, '2026-05-12', 13, 11),
(14, '2026-05-16', 740, 1, '2026-05-12', 14, 14),
(15, '2026-05-11', 1350, 1, '2026-05-07', 15, 19),
(16, '2026-05-10', 990, 1, '2026-05-06', 16, 15),
(17, '2026-05-13', 430, 1, '2026-05-12', 17, 12),
(18, '2026-05-17', 1600, 1, '2026-05-13', 18, 16),
(19, '2026-05-19', 1850, 1, '2026-05-14', 19, 7);

-- --------------------------------------------------------

--
-- Structure de la table `extra_service`
--

CREATE TABLE `extra_service` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `per_night` bit(1) NOT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `extra_service`
--

INSERT INTO `extra_service` (`id`, `name`, `per_night`, `price`) VALUES
(1, 'Breakfast Buffet', b'1', 25),
(2, 'Airport Transfer', b'0', 80),
(3, 'Spa Access', b'1', 40),
(4, 'Private Parking', b'0', 0),
(5, 'Pet Friendly Service', b'1', 20);

-- --------------------------------------------------------

--
-- Structure de la table `rooms`
--

CREATE TABLE `rooms` (
  `id` bigint(20) NOT NULL,
  `capacity` int(11) NOT NULL,
  `description` text DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `size` int(11) NOT NULL,
  `total_quantity` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rooms`
--

INSERT INTO `rooms` (`id`, `capacity`, `description`, `image_path`, `price`, `room_number`, `size`, `total_quantity`, `type`) VALUES
(1, 2, 'Our elegantly appointed Deluxe Sea View Room is designed to offer the utmost in comfort and style.\n\nFeaturing a private balcony with breathtaking sea views, this spacious room includes a king-size bed, premium bedding, modern lighting, and refined contemporary decor.\n\nGuests can enjoy high-speed WiFi, a smart TV, minibar, coffee station, and a luxurious marble bathroom with complimentary amenities.\n\nWhether you are traveling for leisure or a romantic getaway, this room provides a peaceful and unforgettable experience.', '/images/pages/room/1.jpg', 320, '101', 35, 5, 'Deluxe Sea View'),
(2, 1, 'The Single Comfort Room offers a warm and relaxing atmosphere ideal for solo travelers and business guests.\n\nCarefully designed with modern furnishings, the room includes a comfortable single bed, ergonomic workspace, air conditioning, and soundproof windows for maximum comfort.\n\nGuests can also benefit from free WiFi, flat-screen television, and a private bathroom equipped with premium toiletries.\n\nA practical yet elegant choice for short and extended stays.', '/images/pages/room/2.jpg', 180, '102', 20, 8, 'Single Comfort Room'),
(3, 3, 'Our spacious Triple Family Room is perfect for families and small groups seeking both convenience and comfort.\n\nThe room features three comfortable beds, a cozy seating area, large wardrobe space, and elegant interior decoration inspired by Mediterranean hospitality.\n\nModern amenities include high-speed internet, smart TV, minibar, and a contemporary bathroom with walk-in shower.\n\nLarge windows provide abundant natural light, creating a bright and welcoming environment for guests of all ages.', '/images/pages/room/3.jpg', 280, '103', 40, 4, 'Triple Family Room'),
(4, 4, 'The Connecting Family Suite combines luxury, privacy, and functionality for families and group travelers.\n\nThis elegant suite includes two connected bedrooms, separate bathrooms, a comfortable lounge area, and beautiful panoramic city views.\n\nGuests can enjoy premium bedding, smart entertainment systems, coffee machine, minibar, and exclusive room service.\n\nThoughtfully designed to provide both shared moments and personal privacy during your stay.', '/images/pages/room/4.jpg', 450, '104', 65, 3, 'Connecting Suite'),
(5, 2, 'Experience comfort and accessibility in our specially designed Accessible Premium Room.\n\nThis room offers spacious circulation areas, adapted bathroom facilities, accessible furniture, and modern safety features to ensure a stress-free experience for all guests.\n\nElegant decor, comfortable bedding, and contemporary amenities create a relaxing and welcoming atmosphere.\n\nIdeal for guests seeking accessibility without compromising luxury and style.', '/images/pages/room/5.jpg', 260, '105', 32, 6, 'Accessible Room'),
(6, 2, 'Our Premium Garden Room combines elegance and tranquility in a warm modern setting.\n\nGuests can enjoy a beautiful garden view, king-size bedding, smart television, minibar, and complimentary high-speed WiFi.\n\nThe room is designed with soft lighting, refined decoration, and premium amenities to ensure a peaceful and relaxing stay.', '/images/pages/room/6.jpg', 290, 'Premium Garden Room', 106, 5, '30'),
(7, 2, 'The Executive Business Room is specially designed for professionals seeking comfort and productivity.\n\nIt features a spacious work desk, ergonomic chair, soundproof walls, and fast internet connection.\n\nElegant furniture and modern facilities create the ideal atmosphere for business trips and extended stays.', '/images/pages/room/7.jpg', 310, 'Executive Business Room', 107, 4, '32'),
(8, 3, 'Enjoy unforgettable moments in our panoramic Ocean Breeze Suite.\n\nThis luxurious suite offers stunning sea views, modern decoration, spacious living areas, and premium bedding.\n\nGuests benefit from exclusive services including in-room breakfast, coffee station, and personalized concierge assistance.', '/images/pages/room/8.jpg', 520, 'Ocean Breeze Suite', 108, 3, '58'),
(9, 2, 'The Romantic Honeymoon Suite offers a luxurious atmosphere perfect for couples and special occasions.\n\nThe suite features elegant decoration, ambient lighting, a private jacuzzi, and a spacious terrace with panoramic sunset views.\n\nEvery detail has been carefully selected to create an unforgettable romantic experience.', '/images/pages/room/9.jpg', 650, 'Honeymoon Suite', 109, 2, '60'),
(10, 4, 'Our Royal Family Apartment provides exceptional space and comfort for families and groups.\n\nIt includes multiple sleeping areas, a lounge corner, dining table, and modern bathroom facilities.\n\nThe apartment combines functionality and elegance to make long stays enjoyable and relaxing.', '/images/pages/room/10.jpg', 590, 'Royal Family Apartment', 110, 2, '75'),
(11, 2, 'The Deluxe Pool View Room offers direct views of the hotel swimming pool and relaxation area.\n\nGuests can enjoy contemporary decoration, luxury bedding, and a bright open atmosphere enhanced by natural sunlight.\n\nPerfect for travelers looking for comfort and leisure in a stylish environment.', '/images/pages/room/11.jpg', 340, 'Deluxe Pool View', 111, 3, '34'),
(12, 1, 'Designed for modern travelers, the Urban Solo Room provides simplicity, comfort, and practicality.\n\nThe room includes a cozy single bed, smart workspace, air conditioning, and minimalist modern decoration.\n\nAn excellent choice for solo stays and short business trips.', '/images/pages/room/12.jpg', 170, 'Urban Solo Room', 112, 10, '18'),
(13, 2, 'The Luxury Wellness Suite combines premium comfort with a relaxing wellness experience.\n\nGuests enjoy access to a private sauna area, elegant king-size bedding, mood lighting, and luxury bathroom facilities.\n\nIdeal for guests seeking relaxation and exclusivity during their stay.', '/images/pages/room/13.jpg', 720, 'Luxury Wellness Suite', 113, 2, '68'),
(14, 3, 'Our Mediterranean Family Room reflects the charm and warmth of coastal hospitality.\n\nDecorated with soft colors and natural materials, the room includes spacious sleeping arrangements and a comfortable lounge area.\n\nPerfect for family vacations and group stays.', '/images/pages/room/14.jpg', 360, 'Mediterranean Family Room', 114, 4, '42'),
(15, 2, 'The Prestige City View Room offers stunning panoramic views of the city skyline.\n\nThe room combines modern architecture, elegant furnishings, and high-end amenities including minibar and coffee machine.\n\nGuests enjoy both luxury and convenience in a sophisticated environment.', '/images/pages/room/15.jpg', 390, 'Prestige City View', 115, 3, '36'),
(16, 2, 'Experience elegance and serenity in our Sunset Terrace Room.\n\nFeaturing a private outdoor terrace, this room is ideal for guests who enjoy relaxing evenings and open-air comfort.\n\nModern decoration and premium bedding complete the luxurious atmosphere.', '/images/pages/room/16.jpg', 410, 'Sunset Terrace Room', 116, 2, '38'),
(17, 4, 'The Imperial Suite is one of the hotel most luxurious accommodations.\n\nThis exceptional suite includes separate living and dining spaces, premium furniture, luxury bathroom, and exclusive VIP services.\n\nA perfect choice for guests seeking prestige and ultimate comfort.', '/images/pages/room/17.jpg', 950, 'Imperial Suite', 117, 1, '95'),
(18, 2, 'The Cozy Classic Room offers affordable comfort with modern amenities and elegant decoration.\n\nThe room features a queen-size bed, workspace, smart television, and private bathroom.\n\nAn excellent balance between practicality and comfort.', '/images/pages/room/18.jpg', 210, 'Cozy Classic Room', 118, 6, '24'),
(19, 2, 'Our Contemporary Design Room showcases minimalist architecture and stylish interior decoration.\n\nLarge windows, natural lighting, and premium materials create a calm and sophisticated atmosphere.\n\nGuests can enjoy modern technology and luxurious comfort throughout their stay.', '/images/pages/room/19.jpg', 370, 'Contemporary Design Room', 119, 4, '35'),
(20, 5, 'The Presidential Family Suite offers unmatched space, luxury, and exclusivity.\n\nThis premium accommodation includes multiple bedrooms, a private lounge, dining area, and panoramic views.\n\nIdeal for VIP guests, large families, and long luxury stays.', '/images/pages/room/20.jpg', 1200, 'Presidential Family Suite', 120, 1, '120');

-- --------------------------------------------------------

--
-- Structure de la table `settings`
--

CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `settings`
--

INSERT INTO `settings` (`id`, `address`, `email`, `hotel_name`, `phone`) VALUES
(1, 'Route Touristique, Hammamet 8050, Tunisie', 'contact.reservia@gmail.com', 'Reservia Luxury Hotel', '+216 72 555 210');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `name`, `password`, `role`) VALUES
(1, 'admin@gmail.com', b'1', 'Admin', '$2a$10$4gKMDvY8ZgvMow/CDz1xk.aVCPOZA4SbZx7jzi.wqx9SFmm1stzkO', 'ROLE_ADMIN'),
(2, 'zh.asmazahani@gmail.com', b'1', 'Asma Zh', '$2a$10$3.FHO5FZQeVl4QgPapgFm.YQ9B50RnYwB6xgd5yq4x4D7pUd2WbKe', 'ROLE_USER'),
(3, 'sarah.benali@gmail.com', b'0', 'Sarah Ben Ali', '$2a$10$0Pe054rq7.T9qb6Q4tdWKu4glzbejpDvAXUgd7ygtVwTdRW4q9Qwu', 'ROLE_USER'),
(4, 'amine.trabelsi@gmail.com', b'0', 'Amine Trabelsi', '$2a$10$mO8mhexXsrEb/7Cl1Ukw4uxm1bGLPiBVtbtNk1WSQZ0UOcsGJNTsi', 'ROLE_USER'),
(5, 'yasmine.kefi@gmail.com', b'0', 'Yasmine Kefi', '$2a$10$0Pe054rq7.T9qb6Q4tdWKu4glzbejpDvAXUgd7ygtVwTdRW4q9Qwu', 'ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `verification_token`
--

CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `verification_token_seq`
--

CREATE TABLE `verification_token_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `verification_token_seq`
--

INSERT INTO `verification_token_seq` (`next_val`) VALUES
(1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeyog2oic85xg7hsu2je2lx3s6` (`user_id`);

--
-- Index pour la table `booking_extra_services`
--
ALTER TABLE `booking_extra_services`
  ADD KEY `FKtrve3bkbq80rs24vcohw14x4n` (`extra_service_id`),
  ADD KEY `FK521km2ifkx6xwmb9sfwd94v59` (`booking_id`);

--
-- Index pour la table `booking_items`
--
ALTER TABLE `booking_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrw74irmyat5c39cnjkn02u99m` (`booking_id`),
  ADD KEY `FKc8ojcbkfwgijv2ornfqjw615` (`room_id`);

--
-- Index pour la table `extra_service`
--
ALTER TABLE `extra_service`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Index pour la table `verification_token`
--
ALTER TABLE `verification_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKq6jibbenp7o9v6tq178xg88hg` (`user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `booking_items`
--
ALTER TABLE `booking_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `extra_service`
--
ALTER TABLE `extra_service`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `FKeyog2oic85xg7hsu2je2lx3s6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `booking_extra_services`
--
ALTER TABLE `booking_extra_services`
  ADD CONSTRAINT `FK521km2ifkx6xwmb9sfwd94v59` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`),
  ADD CONSTRAINT `FKtrve3bkbq80rs24vcohw14x4n` FOREIGN KEY (`extra_service_id`) REFERENCES `extra_service` (`id`);

--
-- Contraintes pour la table `booking_items`
--
ALTER TABLE `booking_items`
  ADD CONSTRAINT `FKc8ojcbkfwgijv2ornfqjw615` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  ADD CONSTRAINT `FKrw74irmyat5c39cnjkn02u99m` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

--
-- Contraintes pour la table `verification_token`
--
ALTER TABLE `verification_token`
  ADD CONSTRAINT `FK3asw9wnv76uxu3kr1ekq4i1ld` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
