# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table message (
  id                        varchar(255) not null,
  title                     varchar(255),
  content                   varchar(255),
  author                    integer,
  timestamp                 timestamp,
  constraint pk_message primary key (id))
;

create table comment (
  id                        varchar(255) not null,
  content                   varchar(255),
  author                    integer,
  message                   integer,
  timestamp                 integer,
  constraint pk_comment primary key (id))
;

create table user (
  id                        varchar(255) not null AUTO_INCREMENT,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  subscription_date         varchar(255),
  access                    integer,
  constraint pk_user primary key (id))
;

create sequence message_seq;

create sequence comment_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists message;

drop table if exists comment;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists message_seq;

drop sequence if exists comment_seq;

drop sequence if exists user_seq;

