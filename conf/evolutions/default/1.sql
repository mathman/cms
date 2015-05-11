# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        integer auto_increment not null,
  content                   varchar(255),
  author                    integer,
  message                   integer,
  timestamp                 datetime,
  constraint pk_comment primary key (id))
;

create table message (
  id                        integer auto_increment not null,
  title                     varchar(255),
  content                   varchar(255),
  author                    integer,
  timestamp                 datetime,
  constraint pk_message primary key (id))
;

create table session (
  id                        integer auto_increment not null,
  user                      integer,
  last_login                varchar(255),
  last_ip                   varchar(255),
  constraint pk_session primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  subscription_date         datetime,
  access                    integer,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comment;

drop table message;

drop table session;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

