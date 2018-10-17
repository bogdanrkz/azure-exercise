CREATE TABLE person (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  user_name  VARCHAR(255),
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE user_name (user_name)
)
  ENGINE = InnoDB;


INSERT INTO person VALUES (1, "j.heining@azureexercise.onmicrosoft.com", "Jan", "Heining");
INSERT INTO person VALUES (2, "j.lennon@azureexercise.onmicrosoft.com", "John", "Lennon");