drop table if exists student;
create table student
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    student_id   varchar(32) NOT NULL,
    student_name varchar(32) NOT NULL,
    age          smallint,
    email        varchar(128),
    telephone    varchar(24),
    address      varchar(128),
    status       smallint default 0,
    created_date date,
    created_by   varchar(32)
) DEFAULT CHARSET = utf8 COMMENT ='学生表';

insert into student (student_id, student_name, age, email, telephone, address, status, created_date, created_by)
values ('2019001', 'yangwan', 18, 'test@qq.com', '443231', '上海东方明珠', 1, now(), 'yw')
insert into student (student_id, student_name, age, email, telephone, address, status, created_date, created_by)
values ('2019002', 'lanlan', 18, 'lanlan@qq.com', '321', '上海东浦东', 1, now(), 'lo')

select *
from student;