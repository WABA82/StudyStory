-- �ð� ���� �������� ���.
select * from study;
select * from STUDY ORDER BY INPUT_DATEselect * from FAV_STUDY;

-- ���� �������� ����� ����Ʈ. - �ֽż�����
select s.s_num, s.study_name, s.loc, s.category, s.img, s.recruitment, s.input_date, u.nick, u.img user_img, row_number() over( order by s.input_date desc) r_num
from study s, user_table u
where s.id=u.id and accept_flag='Y' and s.deactivation='N' and delete_flag='N';

-- �˻� �������� ����� ����Ʈ - �ֽż�����
select s_num, study_name, loc, category, img, recruitment, TO_CHAR(input_date, 'yyyy/mm/dd') input_date, nick, user_img
from   (select s.s_num, s.study_name, s.loc, s.category, s.img, s.recruitment, s.input_date, u.nick, u.img user_img, row_number() over( order by s.input_date desc) r_num
         from study s, user_table u
         where s.id=u.id and accept_flag='Y' and s.deactivation='N' and delete_flag='N')
where r_num between 1 and 9;

-- �˻� �������� ����� ����Ʈ - �α������.
select s_num, study_name, loc, category, img, recruitment, TO_CHAR(input_date, 'yyyy/mm/dd') input_date, nick, user_img
from   (select s.s_num, s.study_name, s.loc, s.category, s.img, s.recruitment, s.input_date, u.nick, u.img user_img, row_number() over( order by s.input_date desc) r_num
         from study s, user_table u
         where s.id=u.id and accept_flag='Y' and s.deactivation='N' and delete_flag='N')
where r_num between 1 and 9;

