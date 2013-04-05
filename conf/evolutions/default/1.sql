# --- !Ups

create sequence todo_id_seq;
create table todo (
  id                  bigint not null default nextval('todo_id_seq'),
  title               varchar(255) not null,
  content             text not null,
  primary key (id)
);

# --- !Downs

drop table todo;
drop sequence todo_id_seq;