create table users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  username VARCHAR(100),
  password VARCHAR(255),
  enabled INT,
  role VARCHAR(50))
  ENGINE=InnoDB character set = utf8 collate utf8_general_ci;

insert into users (name, username, password, enabled, role)
values
('Admin Ármin', 'admin1', '$2a$10$/SvUS6S0td59CevIBhCZO.7tk0nca2U/inbp7RIFeRxQlvZQqYzIu', 1, 'ROLE_ADMIN'),
('Admin Helga', 'admin2', '$2a$10$uEneIypi9sSz8omnAOGSROZkHXtrThP.n1pvFMmJjoQdqPkktVI9S', 1, 'ROLE_ADMIN'),
('User Ferenc', 'user1', '$2a$10$nW.oMm2g69Bn1LkACLhObe3pF6GHxzs.XIHBBbIRXR.my8KoxknTi', 1, 'ROLE_USER'),
('User Krisztina', 'user2', '$2a$10$67aGiU9GgoLtOS4US09Y7uEkkfVAypOYr.otokLXnZhLz.k3ke9NS', 1, 'ROLE_USER'),
('User Richárd', 'user3', '$2a$10$X6tOC0wGPv4fJB7WpUDwMuV2pptDfOd/fg5Whd5tYkKeEZa3KFCeG', 1, 'ROLE_USER');

