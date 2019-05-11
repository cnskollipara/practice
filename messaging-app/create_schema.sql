USE messaging;
CREATE TABLE t_message_info (
  f_id int(11) NOT NULL AUTO_INCREMENT,
  f_info text NOT NULL,
  f_created_user int(11) NOT NULL,
  f_is_public tinyint(1) NOT NULL DEFAULT '0',
  f_created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  f_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  f_agent varchar(255) NOT NULL DEFAULT 'system',
  f_comment varchar(255) DEFAULT NULL
);

CREATE TABLE t_user_info (
  f_id int(11) NOT NULL AUTO_INCREMENT,
  f_name varchar(100) NOT NULL,
  f_email varchar(100) NOT NULL,
  f_mobile varchar(10) NOT NULL,
  f_password varchar(100) NOT NULL,
  f_is_active tinyint(1) NOT NULL DEFAULT '1',
  f_created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  f_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  f_agent varchar(100) NOT NULL DEFAULT 'system',
  f_comment varchar(100) NOT NULL,
  f_ext_id varchar(40) NOT NULL
);


INSERT INTO t_user_info (f_name, f_email, f_mobile, f_password, f_ext_id) VALUES
('test', 'test01@yopmail.com', '9999900000', md5('test'), uuid());

ALTER TABLE t_message_info
  ADD PRIMARY KEY (f_id),
  ADD KEY t_message_info_fk0 (f_created_user);

ALTER TABLE t_user_info
  ADD PRIMARY KEY (f_id),
  ADD UNIQUE KEY f_email (f_email),
  ADD UNIQUE KEY f_mobile (f_mobile);

ALTER TABLE t_message_info
  ADD CONSTRAINT t_message_info_fk0 FOREIGN KEY (f_created_user) REFERENCES t_user_info (f_id);
COMMIT;

