--
-- Database: `flatauction_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_status`
--

CREATE TABLE `account_status` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `amenities`
--

CREATE TABLE `amenities` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `availabilities`
--

CREATE TABLE `availabilities` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE `bids` (
  `id` int(11) NOT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `created_time` varchar(100) DEFAULT NULL,
  `full_name` varchar(500) DEFAULT NULL,
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `modified_time` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `product_title` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE `locations` (
  `id` int(11) NOT NULL,
  `lat` double DEFAULT '0',
  `location_type` varchar(200) DEFAULT NULL,
  `lon` double DEFAULT '0',
  `postcode` varchar(200) DEFAULT NULL,
  `search_string` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message_bodys`
--

CREATE TABLE `message_bodys` (
  `id` int(11) NOT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `message` varchar(1000) DEFAULT NULL,
  `message_header_id` int(11) DEFAULT NULL,
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message_headers`
--

CREATE TABLE `message_headers` (
  `id` int(11) NOT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `product_id` int(11) DEFAULT NULL,
  `receiver_user_id` int(11) DEFAULT NULL,
  `sender_user_id` int(11) DEFAULT NULL,
  `subject` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `occupations`
--

CREATE TABLE `occupations` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pets`
--

CREATE TABLE `pets` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `amenity_ids` varchar(500) DEFAULT NULL,
  `amenity_titles` varchar(500) DEFAULT NULL,
  `auction_end_date` varchar(500) DEFAULT NULL,
  `auction_end_time_id` int(11) UNSIGNED DEFAULT '1',
  `auction_start_date` varchar(500) DEFAULT NULL,
  `auction_start_time_id` int(11) UNSIGNED DEFAULT '1',
  `availability_ids` varchar(500) DEFAULT NULL,
  `availability_titles` varchar(500) DEFAULT NULL,
  `available_from` varchar(500) DEFAULT NULL,
  `available_to` varchar(500) DEFAULT NULL,
  `base_price` double DEFAULT NULL,
  `category_id` int(11) UNSIGNED DEFAULT '1',
  `category_title` varchar(500) DEFAULT NULL,
  `company_name` varchar(500) DEFAULT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `daily_email_alert` tinyint(1) DEFAULT '0',
  `description` varchar(1000) DEFAULT NULL,
  `first_name` varchar(500) DEFAULT NULL,
  `images` varchar(1000) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `instant_email_alert` tinyint(1) DEFAULT '0',
  `last_name` varchar(500) DEFAULT NULL,
  `lat` int(11) UNSIGNED DEFAULT '1',
  `location_id` int(11) UNSIGNED DEFAULT '1',
  `location_title` varchar(500) DEFAULT NULL,
  `lon` int(11) UNSIGNED DEFAULT '1',
  `max_stay_id` int(11) UNSIGNED DEFAULT '1',
  `max_stay_title` varchar(500) DEFAULT NULL,
  `min_stay_id` int(11) UNSIGNED DEFAULT '1',
  `min_stay_title` varchar(500) DEFAULT NULL,
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `occupation_id` int(11) UNSIGNED DEFAULT '1',
  `occupation_title` varchar(500) DEFAULT NULL,
  `on_going` tinyint(1) DEFAULT '0',
  `pet_id` int(11) UNSIGNED DEFAULT '1',
  `pet_title` varchar(500) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `reference_id` varchar(100) DEFAULT NULL,
  `size_id` int(11) UNSIGNED DEFAULT '1',
  `size_title` varchar(500) DEFAULT NULL,
  `smoking_id` int(11) UNSIGNED DEFAULT '1',
  `smoking_title` varchar(500) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `total_bids` int(11) DEFAULT NULL,
  `type_id` int(11) UNSIGNED DEFAULT '1',
  `type_title` varchar(500) DEFAULT NULL,
  `unix_auction_end` int(11) UNSIGNED DEFAULT '1',
  `unix_auction_start` int(11) UNSIGNED DEFAULT '1',
  `unix_available_from` int(11) UNSIGNED DEFAULT '0',
  `unix_available_to` int(11) UNSIGNED DEFAULT '0',
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_categories`
--

CREATE TABLE `product_categories` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_sizes`
--

CREATE TABLE `product_sizes` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product_types`
--

CREATE TABLE `product_types` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `saved_products`
--

CREATE TABLE `saved_products` (
  `id` int(11) NOT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `smokings`
--

CREATE TABLE `smokings` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stays`
--

CREATE TABLE `stays` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `times`
--

CREATE TABLE `times` (
  `id` int(11) NOT NULL,
  `sec` int(11) UNSIGNED DEFAULT '0',
  `title` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `account_status_id` int(11) NOT NULL DEFAULT '1',
  `address` varchar(255) DEFAULT NULL,
  `agent_logo` varchar(255) DEFAULT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `cell` varchar(255) DEFAULT NULL,
  `created_on` int(11) UNSIGNED DEFAULT '0',
  `document` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `is_verified` tinyint(1) DEFAULT '0',
  `last_name` varchar(255) DEFAULT NULL,
  `modified_on` int(11) UNSIGNED DEFAULT '0',
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `id` int(11) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_status`
--
ALTER TABLE `account_status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `amenities`
--
ALTER TABLE `amenities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `availabilities`
--
ALTER TABLE `availabilities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `bids`
--
ALTER TABLE `bids`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_product_id` (`product_id`),
  ADD KEY `idx_user_id` (`user_id`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_search_string` (`search_string`);

--
-- Indexes for table `message_bodys`
--
ALTER TABLE `message_bodys`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_message_header_id` (`message_header_id`);

--
-- Indexes for table `message_headers`
--
ALTER TABLE `message_headers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_sender_user_id` (`sender_user_id`);

--
-- Indexes for table `occupations`
--
ALTER TABLE `occupations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `pets`
--
ALTER TABLE `pets`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_reference_id` (`reference_id`);

--
-- Indexes for table `product_categories`
--
ALTER TABLE `product_categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `product_sizes`
--
ALTER TABLE `product_sizes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `product_types`
--
ALTER TABLE `product_types`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `saved_products`
--
ALTER TABLE `saved_products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_user_id_product_id` (`user_id`,`product_id`);

--
-- Indexes for table `smokings`
--
ALTER TABLE `smokings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `stays`
--
ALTER TABLE `stays`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `times`
--
ALTER TABLE `times`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_title` (`title`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_name` (`cell`),
  ADD UNIQUE KEY `idx_email` (`email`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_user_id_role_id` (`user_id`,`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_status`
--
ALTER TABLE `account_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `amenities`
--
ALTER TABLE `amenities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `availabilities`
--
ALTER TABLE `availabilities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `bids`
--
ALTER TABLE `bids`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `message_bodys`
--
ALTER TABLE `message_bodys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `message_headers`
--
ALTER TABLE `message_headers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `occupations`
--
ALTER TABLE `occupations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pets`
--
ALTER TABLE `pets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_categories`
--
ALTER TABLE `product_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_sizes`
--
ALTER TABLE `product_sizes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_types`
--
ALTER TABLE `product_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `saved_products`
--
ALTER TABLE `saved_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `smokings`
--
ALTER TABLE `smokings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `stays`
--
ALTER TABLE `stays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `times`
--
ALTER TABLE `times`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users_roles`
--
ALTER TABLE `users_roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
