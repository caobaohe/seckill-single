
-- 建库、表
CREATE database db_ssm_seckill;
USE db_ssm_seckill;

CREATE TABLE tb_seckill (
	seckill_id BIGINT NOT NULL auto_increment COMMENT '主键',
	NAME VARCHAR (120) NOT NULL COMMENT '名称',
	number INT NOT NULL COMMENT '库存数量',
	start_time TIMESTAMP NOT NULL COMMENT '开始时间',
	end_time TIMESTAMP NOT NULL COMMENT '结束时间',
	create_time TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (seckill_id),
	KEY idx_start_time (start_time),
	KEY idx_end_time (end_time),
	KEY idx_create_time (create_time)
) ENGINE = INNODB auto_increment = 1000 DEFAULT charset='utf8' COMMENT '秒杀表';

INSERT INTO tb_seckill (
	NAME,
	number,
	start_time,
	end_tiem
)
VALUES
	(
		'5000元秒杀iphone7',
		50,
		'2016-10-21 00:00:00',
		'2016-10-22 00:10:00'
	),
	(
		'6000元秒杀iphone7 plus',
		50,
		'2016-10-22 00:00:00',
		'2016-10-22 00:10:00'
	),
	(
		'3000元秒杀iphone6',
		150,
		'2016-10-22 00:00:00',
		'2016-10-22 00:10:00'
	),
	(
		'4000元秒杀iphone6 plus',
		100,
		'2016-10-21 00:00:00',
		'2016-10-22 00:10:00'
	);

CREATE TABLE tb_seckill_succeed (
	seckill_id BIGINT NOT NULL COMMENT '秒杀id',
	user_id BIGINT NOT NULL COMMENT '用户id',
	create_time TIMESTAMP NOT NULL COMMENT '创建时间',
	state TINYINT NOT NULL DEFAULT '-1' COMMENT '状态：-1无效，0成功，1已付款',
	PRIMARY KEY (seckill_id, user_id),
	KEY idx_create_time (create_time)
) ENGINE = INNODB DEFAULT CHARSET = 'utf-8' COMMENT '秒杀成功明细表';



