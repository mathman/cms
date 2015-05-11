# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        varchar(255) not null,
  content                   varchar(255),
  author                    integer,
  message                   integer,
  timestamp                 integer,
  constraint pk_comment primary key (id))
;

create table message (
  id                        varchar(255) not null,
  title                     varchar(255),
  content                   varchar(255),
  author                    integer,
  timestamp                 datetime,
  constraint pk_message primary key (id))
;

create table user (
  id                        varchar(255) not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  subscription_date         varchar(255),
  access                    integer,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comment;

drop table message;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

