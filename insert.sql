-- =========================
-- ROOMS
-- =========================

INSERT INTO room
(capacity, description, image_path, price, size, type, room_number)
VALUES

    (
        2,
        'Our elegantly appointed Deluxe Sea View Room is designed to offer the utmost in comfort and style.\n\n
        Featuring a private balcony with breathtaking sea views, this spacious room includes a king-size bed, premium bedding, modern lighting, and refined contemporary decor.\n\n
        Guests can enjoy high-speed WiFi, a smart TV, minibar, coffee station, and a luxurious marble bathroom with complimentary amenities.\n\n
        Whether you are traveling for leisure or a romantic getaway, this room provides a peaceful and unforgettable experience.',
        '/images/pages/room/1.webp',
        320,
        35,
        'Deluxe Sea View',
        '101'
    ),

    (
        1,
        'The Single Comfort Room offers a warm and relaxing atmosphere ideal for solo travelers and business guests.\n\n
        Carefully designed with modern furnishings, the room includes a comfortable single bed, ergonomic workspace, air conditioning, and soundproof windows for maximum comfort.\n\n
        Guests can also benefit from free WiFi, flat-screen television, and a private bathroom equipped with premium toiletries.\n\n
        A practical yet elegant choice for short and extended stays.',
        '/images/pages/room/2.webp',
        180,
        20,
        'Single Comfort Room',
        '102'
    ),

    (
        3,
        'Our spacious Triple Family Room is perfect for families and small groups seeking both convenience and comfort.\n\n
        The room features three comfortable beds, a cozy seating area, large wardrobe space, and elegant interior decoration inspired by Mediterranean hospitality.\n\n
        Modern amenities include high-speed internet, smart TV, minibar, and a contemporary bathroom with walk-in shower.\n\n
        Large windows provide abundant natural light, creating a bright and welcoming environment for guests of all ages.',
        '/images/pages/room/3.webp',
        280,
        40,
        'Triple Family Room',
        '103'
    ),

    (
        4,
        'The Connecting Family Suite combines luxury, privacy, and functionality for families and group travelers.\n\n
        This elegant suite includes two connected bedrooms, separate bathrooms, a comfortable lounge area, and beautiful panoramic city views.\n\n
        Guests can enjoy premium bedding, smart entertainment systems, coffee machine, minibar, and exclusive room service.\n\n
        Thoughtfully designed to provide both shared moments and personal privacy during your stay.',
        '/images/pages/room/4.webp',
        450,
        65,
        'Connecting Suite',
        '104'
    ),

    (
        2,
        'Experience comfort and accessibility in our specially designed Accessible Premium Room.\n\n
        This room offers spacious circulation areas, adapted bathroom facilities, accessible furniture, and modern safety features to ensure a stress-free experience for all guests.\n\n
        Elegant decor, comfortable bedding, and contemporary amenities create a relaxing and welcoming atmosphere.\n\n
        Ideal for guests seeking accessibility without compromising luxury and style.',
        '/images/pages/room/5.webp',
        260,
        32,
        'Accessible Room',
        '105'
    );



-- =========================
-- USERS
-- =========================

INSERT INTO user (email, name, password, role)
VALUES
    (
        'admin@reservia.tn',
        'Administrateur',
        '$2a$10$rLfP5rDWhnOE0qsQUEkESeSb0NOvVnt1JuNKHBG7XQlkQHm7/NUiS',
        'ROLE_ADMIN'
    ),

    (
        'sarah.benali@gmail.com',
        'Sarah Ben Ali',
        '$2a$10$0Pe054rq7.T9qb6Q4tdWKu4glzbejpDvAXUgd7ygtVwTdRW4q9Qwu',
        'ROLE_USER'
    ),

    (
        'amine.trabelsi@gmail.com',
        'Amine Trabelsi',
        '$2a$10$mO8mhexXsrEb/7Cl1Ukw4uxm1bGLPiBVtbtNk1WSQZ0UOcsGJNTsi',
        'ROLE_USER'
    ),

    (
        'yasmine.kefi@gmail.com',
        'Yasmine Kefi',
        '$2a$10$0Pe054rq7.T9qb6Q4tdWKu4glzbejpDvAXUgd7ygtVwTdRW4q9Qwu',
        'ROLE_USER'
    );



-- =========================
-- EXTRA SERVICES
-- =========================

INSERT INTO extra_service (name, per_night, price)
VALUES
    ('Breakfast Buffet', b'1', 25),
    ('Airport Transfer', b'0', 80),
    ('Spa Access', b'1', 40),
    ('Private Parking', b'0', 15),
    ('Pet Friendly Service', b'1', 20);



-- =========================
-- BOOKINGS
-- =========================

INSERT INTO booking
(booking_date, status, total_price, user_id, version)
VALUES

    (CURDATE(), 'CONFIRMED', 985, 2, 1),

    (CURDATE(), 'PENDING', 560, 3, 1),

    (CURDATE(), 'COMPLETED', 1340, 4, 1),

    (CURDATE(), 'CANCELLED', 320, 2, 1);



-- =========================
-- BOOKING ITEMS
-- =========================

INSERT INTO booking_item
(end_date, price, quantity, start_date, booking_id, room_id)
VALUES

    (
        DATE_ADD(CURDATE(), INTERVAL 4 DAY),
        320,
        1,
        DATE_ADD(CURDATE(), INTERVAL 1 DAY),
        1,
        1
    ),

    (
        DATE_ADD(CURDATE(), INTERVAL 3 DAY),
        220,
        1,
        CURDATE(),
        2,
        2
    ),

    (
        DATE_ADD(CURDATE(), INTERVAL 5 DAY),
        450,
        1,
        DATE_SUB(CURDATE(), INTERVAL 6 DAY),
        3,
        4
    ),

    (
        DATE_ADD(CURDATE(), INTERVAL 2 DAY),
        320,
        1,
        DATE_ADD(CURDATE(), INTERVAL 1 DAY),
        4,
        1
    );



-- =========================
-- BOOKING EXTRA SERVICES
-- =========================

INSERT INTO booking_extra_services
(booking_id, extra_service_id)
VALUES

    (1, 1),
    (1, 3),

    (2, 4),

    (3, 1),
    (3, 2),
    (3, 3),

    (4, 5);

-- =========================
-- SETTINGS
-- =========================

INSERT INTO settings
(address, email, hotel_name, phone)
VALUES
    (
        'Route Touristique, Hammamet 8050, Tunisie',
        'contact@reservia.tn',
        'Reservia Luxury Hotel',
        '+216 72 555 210'
    );

-- =========================
-- ADDITIONAL ROOMS
-- =========================

INSERT INTO room
(capacity, description, image_path, price, room_number, size, type)
VALUES

    (
        2,
        'Our Premium Garden Room combines elegance and tranquility in a warm modern setting.\n\n
        Guests can enjoy a beautiful garden view, king-size bedding, smart television, minibar, and complimentary high-speed WiFi.\n\n
        The room is designed with soft lighting, refined decoration, and premium amenities to ensure a peaceful and relaxing stay.',
        '/images/pages/room/6.webp',
        290,
        '106',
        30,
        'Premium Garden Room'
    ),

    (
        2,
        'The Executive Business Room is specially designed for professionals seeking comfort and productivity.\n\n
        It features a spacious work desk, ergonomic chair, soundproof walls, and fast internet connection.\n\n
        Elegant furniture and modern facilities create the ideal atmosphere for business trips and extended stays.',
        '/images/pages/room/7.webp',
        310,
        '107',
        32,
        'Executive Business Room'
    ),

    (
        3,
        'Enjoy unforgettable moments in our panoramic Ocean Breeze Suite.\n\n
        This luxurious suite offers stunning sea views, modern decoration, spacious living areas, and premium bedding.\n\n
        Guests benefit from exclusive services including in-room breakfast, coffee station, and personalized concierge assistance.',
        '/images/pages/room/8.webp',
        520,
        '108',
        58,
        'Ocean Breeze Suite'
    ),

    (
        2,
        'The Romantic Honeymoon Suite offers a luxurious atmosphere perfect for couples and special occasions.\n\n
        The suite features elegant decoration, ambient lighting, a private jacuzzi, and a spacious terrace with panoramic sunset views.\n\n
        Every detail has been carefully selected to create an unforgettable romantic experience.',
        '/images/pages/room/9.webp',
        650,
        '109',
        60,
        'Honeymoon Suite'
    ),

    (
        4,
        'Our Royal Family Apartment provides exceptional space and comfort for families and groups.\n\n
        It includes multiple sleeping areas, a lounge corner, dining table, and modern bathroom facilities.\n\n
        The apartment combines functionality and elegance to make long stays enjoyable and relaxing.',
        '/images/pages/room/10.webp',
        590,
        '110',
        75,
        'Royal Family Apartment'
    ),

    (
        2,
        'The Deluxe Pool View Room offers direct views of the hotel swimming pool and relaxation area.\n\n
        Guests can enjoy contemporary decoration, luxury bedding, and a bright open atmosphere enhanced by natural sunlight.\n\n
        Perfect for travelers looking for comfort and leisure in a stylish environment.',
        '/images/pages/room/11.webp',
        340,
        '111',
        34,
        'Deluxe Pool View'
    ),

    (
        1,
        'Designed for modern travelers, the Urban Solo Room provides simplicity, comfort, and practicality.\n\n
        The room includes a cozy single bed, smart workspace, air conditioning, and minimalist modern decoration.\n\n
        An excellent choice for solo stays and short business trips.',
        '/images/pages/room/12.webp',
        170,
        '112',
        18,
        'Urban Solo Room'
    ),

    (
        2,
        'The Luxury Wellness Suite combines premium comfort with a relaxing wellness experience.\n\n
        Guests enjoy access to a private sauna area, elegant king-size bedding, mood lighting, and luxury bathroom facilities.\n\n
        Ideal for guests seeking relaxation and exclusivity during their stay.',
        '/images/pages/room/13.webp',
        720,
        '113',
        68,
        'Luxury Wellness Suite'
    ),

    (
        3,
        'Our Mediterranean Family Room reflects the charm and warmth of coastal hospitality.\n\n
        Decorated with soft colors and natural materials, the room includes spacious sleeping arrangements and a comfortable lounge area.\n\n
        Perfect for family vacations and group stays.',
        '/images/pages/room/14.webp',
        360,
        '114',
        42,
        'Mediterranean Family Room'
    ),

    (
        2,
        'The Prestige City View Room offers stunning panoramic views of the city skyline.\n\n
        The room combines modern architecture, elegant furnishings, and high-end amenities including minibar and coffee machine.\n\n
        Guests enjoy both luxury and convenience in a sophisticated environment.',
        '/images/pages/room/15.webp',
        390,
        '115',
        36,
        'Prestige City View'
    ),

    (
        2,
        'Experience elegance and serenity in our Sunset Terrace Room.\n\n
        Featuring a private outdoor terrace, this room is ideal for guests who enjoy relaxing evenings and open-air comfort.\n\n
        Modern decoration and premium bedding complete the luxurious atmosphere.',
        '/images/pages/room/16.webp',
        410,
        '116',
        38,
        'Sunset Terrace Room'
    ),

    (
        4,
        'The Imperial Suite is one of the hotel most luxurious accommodations.\n\n
        This exceptional suite includes separate living and dining spaces, premium furniture, luxury bathroom, and exclusive VIP services.\n\n
        A perfect choice for guests seeking prestige and ultimate comfort.',
        '/images/pages/room/17.webp',
        950,
        '117',
        95,
        'Imperial Suite'
    ),

    (
        2,
        'The Cozy Classic Room offers affordable comfort with modern amenities and elegant decoration.\n\n
        The room features a queen-size bed, workspace, smart television, and private bathroom.\n\n
        An excellent balance between practicality and comfort.',
        '/images/pages/room/18.webp',
        210,
        '118',
        24,
        'Cozy Classic Room'
    ),

    (
        2,
        'Our Contemporary Design Room showcases minimalist architecture and stylish interior decoration.\n\n
        Large windows, natural lighting, and premium materials create a calm and sophisticated atmosphere.\n\n
        Guests can enjoy modern technology and luxurious comfort throughout their stay.',
        '/images/pages/room/19.webp',
        370,
        '119',
        35,
        'Contemporary Design Room'
    ),

    (
        5,
        'The Presidential Family Suite offers unmatched space, luxury, and exclusivity.\n\n
        This premium accommodation includes multiple bedrooms, a private lounge, dining area, and panoramic views.\n\n
        Ideal for VIP guests, large families, and long luxury stays.',
        '/images/pages/room/20.webp',
        1200,
        '120',
        120,
        'Presidential Family Suite'
    );

-- =========================================
-- BOOKINGS (TODAY BOOKINGS)
-- =========================================

INSERT INTO booking
(booking_date, status, total_price, version, user_id)
VALUES

    (CURDATE(), 'CONFIRMED', 1280, 1, 3),
    (CURDATE(), 'CONFIRMED', 760, 1, 4),
    (CURDATE(), 'PENDING', 540, 1, 5),
    (CURDATE(), 'CONFIRMED', 2150, 1, 3),
    (CURDATE(), 'COMPLETED', 890, 1, 4),
    (CURDATE(), 'CONFIRMED', 470, 1, 5),
    (CURDATE(), 'PENDING', 980, 1, 3),
    (CURDATE(), 'CONFIRMED', 1560, 1, 4),
    (CURDATE(), 'CANCELLED', 620, 1, 5),
    (CURDATE(), 'CONFIRMED', 740, 1, 3),

    (DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'COMPLETED', 1350, 1, 4),
    (DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'COMPLETED', 990, 1, 5),
    (DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'CANCELLED', 430, 1, 3),
    (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'PENDING', 1600, 1, 4),
    (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'CONFIRMED', 1850, 1, 5);



-- =========================================
-- BOOKING ITEMS
-- IMPORTANT:
-- booking_id commence après les anciens IDs
-- adapte si nécessaire
-- =========================================

INSERT INTO booking_item
(end_date, price, quantity, start_date, booking_id, room_id)
VALUES

    (DATE_ADD(CURDATE(), INTERVAL 3 DAY), 640, 1, CURDATE(), 5, 6),
    (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 380, 1, CURDATE(), 6, 2),
    (DATE_ADD(CURDATE(), INTERVAL 4 DAY), 540, 1, CURDATE(), 7, 3),
    (DATE_ADD(CURDATE(), INTERVAL 5 DAY), 2150, 1, CURDATE(), 8, 20),
    (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 890, 1, CURDATE(), 9, 8),
    (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 470, 1, CURDATE(), 10, 18),
    (DATE_ADD(CURDATE(), INTERVAL 3 DAY), 980, 1, CURDATE(), 11, 13),
    (DATE_ADD(CURDATE(), INTERVAL 6 DAY), 1560, 1, CURDATE(), 12, 17),
    (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 620, 1, CURDATE(), 13, 11),
    (DATE_ADD(CURDATE(), INTERVAL 4 DAY), 740, 1, CURDATE(), 14, 14),

    (DATE_SUB(CURDATE(), INTERVAL 1 DAY), 1350, 1, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 15, 19),
    (DATE_SUB(CURDATE(), INTERVAL 2 DAY), 990, 1, DATE_SUB(CURDATE(), INTERVAL 6 DAY), 16, 15),
    (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 430, 1, CURDATE(), 17, 12),
    (DATE_ADD(CURDATE(), INTERVAL 5 DAY), 1600, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 18, 16),
    (DATE_ADD(CURDATE(), INTERVAL 7 DAY), 1850, 1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 19, 7);



-- =========================================
-- EXTRA SERVICES
-- =========================================

INSERT INTO booking_extra_services
(booking_id, extra_service_id)
VALUES

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

