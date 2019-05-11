CREATE TABLE `t_user_info` (
	`f_id` INT NOT NULL AUTO_INCREMENT,
	`f_name` VARCHAR(100) NOT NULL,
	`f_email` VARCHAR(100) NOT NULL UNIQUE,
	`f_mobile` VARCHAR(100) NOT NULL UNIQUE,
	`f_password` VARCHAR(100) NOT NULL,
	`f_is_active` BOOLEAN(100) NOT NULL,
	`f_created_ts` TIMESTAMP NOT NULL,
	`f_updated_ts` TIMESTAMP NOT NULL,
	`f_agent` VARCHAR(100) NOT NULL,
	`f_comment` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`f_id`)
);

CREATE TABLE `t_message_info` (
	`f_id` INT NOT NULL AUTO_INCREMENT,
	`f_info` TEXT NOT NULL AUTO_INCREMENT,
	`f_created_user` INT NOT NULL AUTO_INCREMENT,
	`f_is_public` BOOLEAN NOT NULL DEFAULT false,
	`f_created_ts` TIMESTAMP NOT NULL DEFAULT true,
	`f_updated_ts` TIMESTAMP NOT NULL DEFAULT true,
	`f_agent` VARCHAR(255) NOT NULL DEFAULT true,
	`f_comment` VARCHAR(255) NOT NULL DEFAULT true,
	PRIMARY KEY (`f_id`)
);

ALTER TABLE `t_message_info` ADD CONSTRAINT `t_message_info_fk0` FOREIGN KEY (`f_created_user`) REFERENCES `t_user_info`(`f_id`);

