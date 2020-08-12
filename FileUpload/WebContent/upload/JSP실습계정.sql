select * from t_member;

desc t_member;

insert into t_member(id,pwd,name,email,JOINDATE)values('asa','1234','ȫ�浿','admin@naver.com','20/05/25');

select * from t_member order by joinDate desc;

commit;