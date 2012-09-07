# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table connection_info (
  id                        bigint not null,
  database                  varchar(255),
  host                      varchar(255),
  port                      integer,
  constraint pk_connection_info primary key (id))
;

create sequence connection_info_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists connection_info;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists connection_info_seq;

