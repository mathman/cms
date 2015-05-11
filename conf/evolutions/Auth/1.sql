# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table realmlist (
  name                      varchar(255),
  address                   varchar(255))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table realmlist;

SET FOREIGN_KEY_CHECKS=1;

