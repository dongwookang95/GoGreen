-- USER RELATED

CREATE TABLE users(
  username varchar (15) PRIMARY KEY,
  password varchar (1000) NOT NULL,
  role varchar (20),
  enabled boolean NOT NULL DEFAULT FALSE,
);

CREATE TABLE friends(
  my_username varchar (15),
  friend_username varchar (15)
);

CREATE TABLE friend_requests(
  my_username varchar (15),
  friend_username varchar (15)
);

CREATE TABLE friends(
  my_username varchar (15),
  friend_username varchar (15)
);

CREATE TABLE achievements(
  id SERIAL PRIMARY KEY,
  category_id bigint,
  badge_id bigint,
  username varchar (15),
  created_date date,
  last_modified_date date,
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (badge_id) REFERENCES badges(id)
);

CREATE TABLE badges(
  id SERIAL PRIMARY KEY,
  name varchar (30),
  created_date date,
  last_modified_date date
);

INSERT INTO badges(name, created_date, last_modified_date) VALUES ('bronze', NOW(), NOW());
INSERT INTO badges(name, created_date, last_modified_date) VALUES ('silver', NOW(), NOW());
INSERT INTO badges(name, created_date, last_modified_date) VALUES ('gold', NOW(), NOW());