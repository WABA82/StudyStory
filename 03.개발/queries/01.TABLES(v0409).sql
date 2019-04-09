/* ���� */
DROP TABLE user_table
	CASCADE CONSTRAINTS;

/* �������� */
DROP TABLE notice
	CASCADE CONSTRAINTS;

/* ���ǻ��� */
DROP TABLE question
	CASCADE CONSTRAINTS;

/* ���͵� */
DROP TABLE study
	CASCADE CONSTRAINTS;

/* ���͵� ���� */
DROP TABLE study_notice
	CASCADE CONSTRAINTS;

/* ���� */
DROP TABLE homework
	CASCADE CONSTRAINTS;

/* ���͵� ��� */
DROP TABLE s_comment
	CASCADE CONSTRAINTS;

/* ���͵� ������ */
DROP TABLE member
	CASCADE CONSTRAINTS;

/* ���͵� ��û�� */
DROP TABLE join
	CASCADE CONSTRAINTS;

/* �˸� */
DROP TABLE alarm
	CASCADE CONSTRAINTS;

/* �����ϴ� ���͵� */
DROP TABLE fav_study
	CASCADE CONSTRAINTS;

/* ���͵� ���� ��� */
DROP TABLE sn_comment
	CASCADE CONSTRAINTS;

/* ������ ���� */
DROP TABLE admin_user
	CASCADE CONSTRAINTS;

/* ���� */
CREATE TABLE user_table (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	nick VARCHAR2(30) NOT NULL, /* �г��� */
	name VARCHAR2(30) NOT NULL, /* �̸� */
	pass VARCHAR2(100) NOT NULL, /* ��й�ȣ */
	zipcode VARCHAR2(6) NOT NULL, /* �����ȣ */
	addr1 VARCHAR2(150) NOT NULL, /* �ּ�1 */
	addr2 VARCHAR2(150) NOT NULL, /* �ּ�2 */
	tel VARCHAR2(13) NOT NULL, /* ����ó */
	email VARCHAR2(100) NOT NULL, /* �̸��� */
	question CHAR(1) NOT NULL, /* �������� */
	answer VARCHAR2(100) NOT NULL, /* ������ */
	img VARCHAR2(100) DEFAULT 'no_user_img.png', /* �̹��� */
	introduce VARCHAR2(300), /* �Ұ��� */
	deactivation CHAR(1), /* Ż�𿩺� */
	reg_date DATE DEFAULT SYSDATE /* ������ */
);

CREATE UNIQUE INDEX PK_user_table
	ON user_table (
		id ASC
	);

CREATE UNIQUE INDEX UIX_user_table
	ON user_table (
		nick ASC,
		email ASC
	);

ALTER TABLE user_table
	ADD
		CONSTRAINT PK_user_table
		PRIMARY KEY (
			id
		);

ALTER TABLE user_table
	ADD
		CONSTRAINT UK_user_table
		UNIQUE (
			nick,
			email
		);

/* �������� */
CREATE TABLE notice (
	n_num CHAR(8) NOT NULL, /* �������� ��ȣ */
	subject VARCHAR2(300) NOT NULL, /* ���� */
	content CLOB NOT NULL, /* ���� */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	view_cnt NUMBER DEFAULT 0 /* ��ȸ�� */
);

CREATE UNIQUE INDEX PK_notice
	ON notice (
		n_num ASC
	);

ALTER TABLE notice
	ADD
		CONSTRAINT PK_notice
		PRIMARY KEY (
			n_num
		);

/* ���ǻ��� */
CREATE TABLE question (
	q_num CHAR(8) NOT NULL, /* ���� ��ȣ */
	subject VARCHAR2(300) NOT NULL, /* ���� */
	category VARCHAR2(12) NOT NULL, /* �з� */
	content CLOB NOT NULL, /* ���ǳ��� */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	answer_flag CHAR(1) DEFAULT 'N', /* �亯���� */
	answer_content CLOB, /* �亯���� */
	answer_date DATE, /* �亯�� */
	id VARCHAR2(15) NOT NULL /* ���̵� */
);

CREATE UNIQUE INDEX PK_question
	ON question (
		q_num ASC
	);

ALTER TABLE question
	ADD
		CONSTRAINT PK_question
		PRIMARY KEY (
			q_num
		);

/* ���͵� */
CREATE TABLE study (
	s_num CHAR(8) NOT NULL, /* ���͵� ��ȣ */
	study_name VARCHAR2(72) NOT NULL, /* ���͵�� */
	loc CHAR(6) NOT NULL, /* ���� */
	category CHAR(6) NOT NULL, /* ���� */
	content CLOB NOT NULL, /* �󼼼��� */
	img VARCHAR2(100) DEFAULT 'no_study_img.png', /* �̹��� */
	recruitment CHAR(1) DEFAULT 'Y', /* �������� */
	accept_flag CHAR(1) DEFAULT 'N', /* �������� */
	input_date DATE DEFAULT SYSDATE, /* ������ */
	deactivation CHAR(1), /* ���Ῡ�� */
	id VARCHAR2(15) NOT NULL, /* ���� ���̵� */
	delete_flag CHAR(1) /* �������� */
);

CREATE UNIQUE INDEX PK_study
	ON study (
		s_num ASC
	);

CREATE UNIQUE INDEX UIX_study
	ON study (
		study_name ASC
	);

ALTER TABLE study
	ADD
		CONSTRAINT PK_study
		PRIMARY KEY (
			s_num
		);

ALTER TABLE study
	ADD
		CONSTRAINT UK_study
		UNIQUE (
			study_name
		);

/* ���͵� ���� */
CREATE TABLE study_notice (
	sn_num CHAR(9) NOT NULL, /* ���͵� ������ȣ */
	subject VARCHAR2(300) NOT NULL, /* ������ */
	meeting_info VARCHAR2(100) NOT NULL, /* ���ӽð� */
	content CLOB NOT NULL, /* �������� */
	addr VARCHAR2(300) NOT NULL, /* �ּ� */
	s_num CHAR(8) NOT NULL, /* ���͵� ��ȣ */
	input_date DATE DEFAULT SYSDATE /* �ۼ��� */
);

CREATE UNIQUE INDEX PK_study_notice
	ON study_notice (
		sn_num ASC
	);

ALTER TABLE study_notice
	ADD
		CONSTRAINT PK_study_notice
		PRIMARY KEY (
			sn_num
		);

/* ���� */
CREATE TABLE homework (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	workload VARCHAR2(300) NOT NULL, /* �������� */
	finish_flag CHAR(1) DEFAULT 'N', /* �ϷῩ�� */
	sn_num CHAR(9) NOT NULL /* ���͵� ������ȣ */
);

CREATE UNIQUE INDEX PK_homework
	ON homework (
		id ASC
	);

ALTER TABLE homework
	ADD
		CONSTRAINT PK_homework
		PRIMARY KEY (
			id
		);

/* ���͵� ��� */
CREATE TABLE s_comment (
	s_num CHAR(8) NOT NULL, /* ���͵� ��ȣ */
	s_comment VARCHAR2(300) NOT NULL, /* ��۳��� */
	input_date DATE DEFAULT SYSDATE, /* ��۵���� */
	id VARCHAR2(15) NOT NULL /* ���̵� */
);

CREATE UNIQUE INDEX PK_s_comment
	ON s_comment (
		s_num ASC
	);

ALTER TABLE s_comment
	ADD
		CONSTRAINT PK_s_comment
		PRIMARY KEY (
			s_num
		);

/* ���͵� ������ */
CREATE TABLE member (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	s_num CHAR(8) NOT NULL /* ���͵� ��ȣ */
);

CREATE UNIQUE INDEX PK_member
	ON member (
		id ASC,
		s_num ASC
	);

ALTER TABLE member
	ADD
		CONSTRAINT PK_member
		PRIMARY KEY (
			id,
			s_num
		);

/* ���͵� ��û�� */
CREATE TABLE join (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	s_num CHAR(8) NOT NULL, /* ���͵� ��ȣ */
	introduce VARCHAR2(300) NOT NULL, /* �ڱ�Ұ� */
	motive VARCHAR2(300) NOT NULL, /* �������� */
	accept_flag CHAR(1) DEFAULT 'N', /* �������� */
	input_date DATE DEFAULT SYSDATE /* ��û�� */
);

CREATE UNIQUE INDEX PK_join
	ON join (
		id ASC,
		s_num ASC
	);

ALTER TABLE join
	ADD
		CONSTRAINT PK_join
		PRIMARY KEY (
			id,
			s_num
		);

/* �˸� */
CREATE TABLE alarm (
	a_num CHAR(8) NOT NULL, /* �˸� ��ȣ */
	category VARCHAR2(12) NOT NULL, /* �з� */
	subject VARCHAR2(300) NOT NULL, /* ���� */
	content VARCHAR2(300) NOT NULL, /* ���� */
	input_date DATE DEFAULT SYSDATE, /* ����� */
	read_flag CHAR(1) DEFAULT 'N', /* �������� */
	id VARCHAR2(15) NOT NULL /* ���̵� */
);

CREATE UNIQUE INDEX PK_alarm
	ON alarm (
		a_num ASC
	);

ALTER TABLE alarm
	ADD
		CONSTRAINT PK_alarm
		PRIMARY KEY (
			a_num
		);

/* �����ϴ� ���͵� */
CREATE TABLE fav_study (
	s_num CHAR(8) NOT NULL, /* ���͵� ��ȣ */
	id VARCHAR2(15) NOT NULL /* ���̵� */
);

CREATE UNIQUE INDEX PK_fav_study
	ON fav_study (
		s_num ASC,
		id ASC
	);

ALTER TABLE fav_study
	ADD
		CONSTRAINT PK_fav_study
		PRIMARY KEY (
			s_num,
			id
		);

/* ���͵� ���� ��� */
CREATE TABLE sn_comment (
	sn_num CHAR(9) NOT NULL, /* ���͵� ������ȣ */
	sn_comment VARCHAR2(300) NOT NULL, /* ��۳��� */
	input_date DATE DEFAULT SYSDATE, /* ��۵���� */
	id VARCHAR2(15) NOT NULL /* ���̵� */
);

CREATE UNIQUE INDEX PK_sn_comment
	ON sn_comment (
		sn_num ASC
	);

ALTER TABLE sn_comment
	ADD
		CONSTRAINT PK_sn_comment
		PRIMARY KEY (
			sn_num
		);

/* ������ ���� */
CREATE TABLE admin_user (
	id VARCHAR2(15) NOT NULL, /* ���̵� */
	pass VARCHAR2(100) NOT NULL, /* ��й�ȣ */
	reg_date DATE DEFAULT SYSDATE /* ����� */
);

CREATE UNIQUE INDEX PK_admin_user
	ON admin_user (
		id ASC
	);

ALTER TABLE admin_user
	ADD
		CONSTRAINT PK_admin_user
		PRIMARY KEY (
			id
		);

ALTER TABLE question
	ADD
		CONSTRAINT FK_user_table_TO_question
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE study
	ADD
		CONSTRAINT FK_user_table_TO_study
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE study_notice
	ADD
		CONSTRAINT FK_study_TO_study_notice
		FOREIGN KEY (
			s_num
		)
		REFERENCES study (
			s_num
		)
		ON DELETE CASCADE;

ALTER TABLE homework
	ADD
		CONSTRAINT FK_user_table_TO_homework
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE homework
	ADD
		CONSTRAINT FK_study_notice_TO_homework
		FOREIGN KEY (
			sn_num
		)
		REFERENCES study_notice (
			sn_num
		)
		ON DELETE CASCADE;

ALTER TABLE s_comment
	ADD
		CONSTRAINT FK_user_table_TO_s_comment
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE s_comment
	ADD
		CONSTRAINT FK_study_TO_s_comment
		FOREIGN KEY (
			s_num
		)
		REFERENCES study (
			s_num
		)
		ON DELETE CASCADE;

ALTER TABLE member
	ADD
		CONSTRAINT FK_study_TO_member
		FOREIGN KEY (
			s_num
		)
		REFERENCES study (
			s_num
		)
		ON DELETE CASCADE;

ALTER TABLE member
	ADD
		CONSTRAINT FK_user_table_TO_member
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE join
	ADD
		CONSTRAINT FK_user_table_TO_join
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE join
	ADD
		CONSTRAINT FK_study_TO_join
		FOREIGN KEY (
			s_num
		)
		REFERENCES study (
			s_num
		)
		ON DELETE CASCADE;

ALTER TABLE alarm
	ADD
		CONSTRAINT FK_user_table_TO_alarm
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE fav_study
	ADD
		CONSTRAINT FK_study_TO_fav_study
		FOREIGN KEY (
			s_num
		)
		REFERENCES study (
			s_num
		)
		ON DELETE CASCADE;

ALTER TABLE fav_study
	ADD
		CONSTRAINT FK_user_table_TO_fav_study
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE sn_comment
	ADD
		CONSTRAINT FK_user_table_TO_sn_comment
		FOREIGN KEY (
			id
		)
		REFERENCES user_table (
			id
		);

ALTER TABLE sn_comment
	ADD
		CONSTRAINT FK_study_notice_TO_sn_comment
		FOREIGN KEY (
			sn_num
		)
		REFERENCES study_notice (
			sn_num
		)
		ON DELETE CASCADE;

COMMIT;