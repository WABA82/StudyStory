select * from study;
select nick from user_table where id = 'kim111';
select id from user_table where nick = '������';
select * from user_table;

update user_table set nick='������111' where id='kim111';
commit;
insert into alarm(A_NUM, CATEGORY, SUBJECT, CONTENT, INPUT_DATE,  READ_FLAG, ID)
values(A_CODE, '���͵�', '���� ��û�� �ֽ��ϴ�.', '���� ���͵� ���� ��û�� �ϼ̽��ϴ�.', SYSDATE, 'N', ( select id from user_table where nick = '������') );

select * from alarm;
