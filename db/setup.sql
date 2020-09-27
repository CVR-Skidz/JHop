CREATE TABLE directory (
    index_name varchar(255) UNIQUE NOT NULL, 
    tag_attribute varchar(50),
    attribute_value varchar(255)
);

CREATE TABLE page (
    url varchar(1024) UNIQUE NOT NULL,
    src varchar(1024),
    title varchar(255)
);

CREATE TABLE term (
    index_name varchar(255) NOT NULL,
    term varchar(255),
    page varchar(1024) REFERENCES page(url)
);