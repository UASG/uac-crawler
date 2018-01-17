 CREATE TABLE web_crawler_info (
  id bigint not null auto_increment,
  website_url varchar(3000) not null,
  requester_email varchar(255) not null,
  requested_on date,
  num_of_domain_ascii_links int default 0,
  num_of_domain_ascii_utf_links int default 0,
  num_of_domain_links int default 0,
  num_of_ext_ascii_links int default 0,
  num_of_ext_links int default 0,
  num_of_inactive_ascii_links int default 0,
  num_of_inactive_links int default 0,
  ua_compliance_index smallint default 5,
  ascii_email_count int default 0,
  email_count int default 0,
  email_fields_count int default 0,
  time bigint default 0,
  crawled_urls longtext,
  primary key (id)
  ) ENGINE=InnoDB;

   CREATE TABLE user_detail_info (
  id bigint not null auto_increment,
  user_name varchar (255) not null,
  requester_email varchar(255) not null,
  company_name varchar(255) not null,
  contact_number bigint,
  requested_on date,

  primary key (id)
  ) ENGINE=InnoDB;


  create table crawling_url_info (
  id bigint not null auto_increment,
  crawling_url varchar(3000) not null,
  status varchar(255) not null,
  process_start_time date,
  primary key (id)
  ) ENGINE=InnoDB;

  create TABLE  crawled_url (
  id bigint not null auto_increment,
  crawling_url_info_id  bigint not null,
  crawled_url varchar(3000) not null,
   primary key (id),
   foreign key (crawling_url_info_id) references crawling_url_info (id)
  ) ENGINE=InnoDB;

