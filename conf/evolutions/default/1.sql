# --- !Ups

create sequence todo_id_seq;
create table todo (
  id                  bigint not null default nextval('todo_id_seq'),
  content             varchar(255) not null,
  primary key (id)
);

# --- !Downs

drop table todo;
drop sequence todo_id_seq;