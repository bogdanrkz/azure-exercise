CREATE TABLE user (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  user_name  VARCHAR(255),
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE user_name (user_name)
)
  ENGINE = InnoDB;