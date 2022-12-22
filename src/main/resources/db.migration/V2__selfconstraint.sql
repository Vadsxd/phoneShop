alter table subcategory add column if not exists parent_id bigint default null;
alter table if exists subcategory add constraint fk_subcategory_id foreign key (parent_id) references subcategory;
