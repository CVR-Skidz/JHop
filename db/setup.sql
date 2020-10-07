CREATE TABLE directory (
    index_name varchar(255) NOT NULL, 
    tag_attribute varchar(50),
    attribute_value varchar(255),
    CONSTRAINT directory_pk PRIMARY KEY (index_name)
);

CREATE TABLE page (
    index_name varchar(255) NOT NULL,
    url varchar(1024) NOT NULL,
    src varchar(1024),
    title varchar(255),
    CONSTRAINT page_pk PRIMARY KEY (url, index_name),
    CONSTRAINT page_index_fk FOREIGN KEY index_name REFERENCES directory(index_name)
);

CREATE TABLE term (
    index_name varchar(255) NOT NULL,
    term varchar(255),
    page varchar(1024),
    frequency numeric,
    CONSTRAINT term_pk PRIMARY KEY (page, term),
    CONSTRAINT term_page_fk FOREIGN KEY (page) REFERENCES page(url),
    CONSTRAINT term_index_fk FOREIGN KEY (index_name) REFERENCES directory(index_name)
);