create sequence hibernate_sequence start 1 increment 1
create table ftr_role (role_id int4 not null, created_at timestamp not null, role_name varchar(255), updated_at timestamp not null, primary key (role_id))
create table ftr_system_parameter (param_id int4 not null, active boolean not null, param_name varchar(255), param_value varchar(255), primary key (param_id))
create table ftr_user (user_id  bigserial not null, active boolean, created_at timestamp not null, deleted boolean, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), updated_at timestamp not null, username varchar(255), primary key (user_id))
create table ftr_user_info_role (user_id int8 not null, role_id int4 not null, primary key (user_id, role_id))
create table ftr_user_login (id int8 not null, client_ip varchar(255), login_time timestamp, logout_time timestamp, user_id int8, primary key (id))
create table workout (workout_id int8 not null, created_at timestamp not null, step int8, user_id int8 not null, primary key (workout_id))
alter table if exists ftr_user_info_role add constraint FK5r5tfa4rqismb3tpaujsfbenl foreign key (role_id) references ftr_role
alter table if exists ftr_user_info_role add constraint FK1x8pvsp9m44x0q8ex1w43homv foreign key (user_id) references ftr_user
alter table if exists ftr_user_login add constraint FKmau74eig1e4ftvkeko3rg3un8 foreign key (user_id) references ftr_user