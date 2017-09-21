INSERT INTO `role` (`role`) VALUES ('ROLE_ADMIN'),('ROLE_EMPLOYED'),('ROLE_MEMBER');

INSERT INTO `user` (`create_date`, `email`, `enabled`, `login`, `name`, `password`, `surname`, `role_id`) VALUES ('2017-04-16 12:26:39', 'emial@test.com', true, 'admin', 'Admin', '$2a$04$rFv4HpCY3htmNsJ.ZfXiYO0FQ2/IyiUl3qpomYt87dFWgTgoazPzS', 'Admin', 1);