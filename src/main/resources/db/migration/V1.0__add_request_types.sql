create table if not exists REQUEST_TYPE
(
    ID BIGINT identity primary key,
    REQUEST_TYPE VARCHAR(255)
);

insert into REQUEST_TYPE values (1, 'Contract Adjustment');
insert into REQUEST_TYPE values (2, 'Damage Case');
insert into REQUEST_TYPE values (3, 'Complaint');
