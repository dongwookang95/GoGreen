-- CATEGORY RELATED

CREATE TABLE categories(
  id SERIAL PRIMARY KEY,
  description varchar (15),
  created_date date,
  last_modified_date date
);

CREATE TABLE subcategories(
  id SERIAL PRIMARY KEY,
  description varchar (30),
  category_id bigint,
  created_date date,
  last_modified_date date,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE subcategories_attributes(
  subcategory_id bigint,
  attribute_id bigint,
  FOREIGN KEY (attribute_id) REFERENCES attributes(id),
  FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE attributes(
  id SERIAL PRIMARY KEY,
  description varchar (30),
  attribute_type varchar (15),
  created_date date,
  last_modified_date date
);

INSERT INTO categories(description, created_date, last_modified_date) VALUES ('Food', NOW(), NOW());
INSERT INTO categories(description, created_date, last_modified_date) VALUES ('Transport', NOW(), NOW());
INSERT INTO categories(description, created_date, last_modified_date) VALUES ('Energy', NOW(), NOW());

INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Vegetarian Meal', 1, NOW(), NOW());
INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Local Produce', 1, NOW(), NOW());
INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Public Transport', 2, NOW(), NOW());
INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Travel By Bike', 2, NOW(), NOW());
INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Solar Panels', 3, NOW(), NOW());
INSERT INTO subcategories(description, category_id, created_date, last_modified_date) VALUES ('Lower Temperature', 3, NOW(), NOW());

INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('numberOfMeals', 'int', NOW(), NOW());

INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('hours', 'double', NOW(), NOW());
INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('degrees', 'double', NOW(), NOW());
INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('watt', 'int', NOW(), NOW());
INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('numberOfSolarPanels', 'int', NOW(), NOW());

INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('kilometers', 'double', NOW(), NOW());
INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('transportTypeInstead', 'String', NOW(), NOW());
INSERT INTO attributes(description, attribute_type, created_date, last_modified_date) VALUES ('transportTypeActual', 'String', NOW(), NOW());


INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (1,1);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (2,1);

INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (3,6);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (3,7);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (3,8);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (4,6);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (4,7);

INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (5,2);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (5,4);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (5,5);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (6,2);
INSERT INTO subcategories_attributes(subcategory_id, attribute_id) VALUES (6,3);