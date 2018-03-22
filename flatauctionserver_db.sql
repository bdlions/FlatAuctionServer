INSERT INTO `account_status`(`id`, `title`) values
(1, 'Active'),
(2, 'Inactive');

INSERT INTO `roles`(`id`, `title`, `description`) values
(1, 'ADMIN', 'Admin'),
(2, 'LANDLORD', 'Landlord'),
(3, 'TENANT', 'Tenant'),
(4, 'AGENT', 'Agent');

INSERT INTO `genders`(`id`, `title`, `order_no`) values
(1, 'Male', 1),
(2, 'Female', 2);

INSERT INTO `users`(`id`, `email`, `password`, `first_name`, `last_name`, `account_status_id`, `cell`) values
(1, 'admin@gmail.com', 'pass', 'Nazmul', 'Hasan', 1, '01711123456'),
(2, 'landlord@gmail.com', 'pass', 'Alamgir', 'Kabir', 1, '01722123456'),
(3, 'tanent@gmail.com', 'pass', 'Zobaer', 'Badal', 1, '01733123456'),
(4, 'agent@gmail.com', 'pass', 'Shem', 'Haye', 1, '01744123456');

INSERT INTO `users_roles`(`id`, `user_id`, `role_id`) values
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4);

INSERT INTO `product_categories`(`id`, `title`) values
(1, 'Flat/Apartmnt'),
(2, 'House or Property');

INSERT INTO `product_sizes`(`id`, `title`, `order_no`) values
(1, '1 Bed', 1),
(2, '2 Bed', 2),
(3, '3 Bed', 3),
(4, '4 Bed', 4),
(5, '5 Bed', 5),
(6, '6 Bed', 6),
(7, '7 Bed', 7),
(8, '8 Bed', 8),
(9, '9 Bed', 9),
(10, '10 Bed', 10);

INSERT INTO `product_types`(`id`, `title`, `order_no`) values
(1, '1 Room', 1),
(2, '2 Rooms', 2),
(3, '3 Rooms', 3),
(4, '4 Rooms', 4),
(5, '5 Rooms', 5),
(6, '6 Rooms', 6),
(7, '7 Rooms', 7),
(8, '8 Rooms', 8),
(9, '9 Roomss', 9),
(10, '10 Rooms', 10);

INSERT INTO `locations`(`id`, `search_string`, `location_type`, `postcode`, `lat`, `lon`) values
(1, 'london', '1', 'c1', 0, 0);

INSERT INTO `stays`(`id`, `title`, `order_no`) values
(1, 'No Limit', 1),
(2, '1 Day', 2),
(3, '3 Weeks', 3),
(4, '1 Month', 4),
(5, '2 Months', 5),
(6, '3 Months', 6),
(7, '4 Months', 7),
(8, '5 Months', 8),
(9, '6 Months', 9),
(10, '7 Months', 10),
(11, '8 Months', 11),
(12, '9 Months', 12),
(13, '10 Months', 13),
(14, '11 Months', 14),
(15, '1 Year', 15),
(16, '1 Year 3 Months', 16),
(17, '1 Year 6 Months', 17),
(18, '1 Year 9 Months', 18),
(19, '2 Years', 19),
(20, '3 Years', 20);

INSERT INTO `smokings`(`id`, `title`) values
(1, 'No preference'),
(2, 'No');

INSERT INTO `occupations`(`id`, `title`) values
(1, 'No preference'),
(2, 'Student'),
(3, 'Professional');

INSERT INTO `pets`(`id`, `title`) values
(1, 'No preference'),
(2, 'No');

INSERT INTO `amenities`(`id`, `title`) values
(1, 'Parking'),
(2, 'Balcony/patio'),
(3, 'Garden/roof terrace'),
(4, 'Disabled access'),
(5, 'Garage');

INSERT INTO `availabilities`(`id`, `title`) values
(1, 'Daily'),
(2, 'Weekly'),
(3, 'Monthly');

INSERT INTO `times` (`id`, `title`, `sec`) VALUES
(1, '00:00', 0),
(2, '01:00', 3600),
(3, '02:00', 7200),
(4, '03:00', 10800),
(5, '04:00', 14400),
(6, '05:00', 18000),
(7, '06:00', 21600),
(8, '07:00', 25200),
(9, '08:00', 28800),
(10, '09:00', 32400),
(11, '10:00', 36000),
(12, '11:00', 39600),
(13, '12:00', 43200),
(14, '13:00', 46800),
(15, '14:00', 50400),
(16, '15:00', 54000),
(17, '16:00', 57600),
(18, '17:00', 61200),
(19, '18:00', 64800),
(20, '19:00', 68400),
(21, '20:00', 72000),
(22, '21:00', 75600),
(23, '22:00', 79200),
(24, '23:00', 82800);
