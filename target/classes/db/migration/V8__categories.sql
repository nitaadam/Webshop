CREATE TABLE categories (
  id BIGINT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  view_order INT,
  CONSTRAINT PK_category PRIMARY KEY(id)
) engine=InnoDB character set = utf8 collate utf8_general_ci;

insert into categories (name, view_order) values ("Foglalkozások", 5);
insert into categories (name, view_order) values ("Évszakok", 2);
insert into categories (name, view_order) values ("Természet", 3);
insert into categories (name, view_order) values ("Sport", 4);
insert into categories (name, view_order) values ("Félelmetes", 1);
insert into categories (name, view_order) values ("Egyéb", 6);
