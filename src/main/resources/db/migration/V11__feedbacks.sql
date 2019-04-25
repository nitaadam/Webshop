CREATE TABLE feedbacks(
id bigint AUTO_INCREMENT,
product_id bigint,
user_id bigint,
stars int,
review text,
date datetime,
CONSTRAINT pk_feedbacks PRIMARY KEY (id),
FOREIGN KEY (product_id) references products(id),
FOREIGN KEY (user_id) references users(id)
)ENGINE=InnoDB character set = utf8 collate utf8_general_ci;


Insert into feedbacks (product_id, user_id, stars, review, date) values (5, 3, 5, "Imádom!!!!!!", CURRENT_TIMESTAMP );
Insert into feedbacks (product_id, user_id, stars, review, date) values (2, 4, 4, "Gumikacsább a gumikacsánál", CURRENT_TIMESTAMP);
Insert into feedbacks (product_id, user_id, stars, review, date) values (3, 5, 1, "Nem csipog elég élethűen", CURRENT_TIMESTAMP);
Insert into feedbacks (product_id, user_id, stars, review, date) values (4, 3, 5, "Gumikacsát minden asztalra", CURRENT_TIMESTAMP);
Insert into feedbacks (product_id, user_id, stars, review, date) values (7, 4, 3, "Nem rossz, de állandóan elsüllyeszti a kishajóimat :(", CURRENT_TIMESTAMP);
Insert into feedbacks (product_id, user_id, stars, review, date) values (7, 5, 4, "Fél tőle a macskám", CURRENT_TIMESTAMP);