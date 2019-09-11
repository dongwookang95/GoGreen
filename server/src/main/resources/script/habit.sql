-- HABIT RELATED

CREATE TABLE vegetarian_meals(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  number_of_meals int,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE local_produces(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  number_of_meals int,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE solar_panels(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  hours real,
  watt int,
  number_of_solar_panels int,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE lower_temperatures(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  hours real,
  degrees real,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE public_transports(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  transport_type_instead varchar (15),
  transport_type_actual varchar (15),
  kilometers real,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE travel_by_bikes(
  id SERIAL PRIMARY KEY,
  username varchar (15) NOT NULL,
  category_id bigint,
  subcategory_id bigint,
  transport_type_instead varchar (15),
  kilometers real,
  amount real,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (username) REFERENCES users(username),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);
