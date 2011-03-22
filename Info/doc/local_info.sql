
 CREATE TABLE `info` (
  `id` bigint(11) NOT NULL default '0',
  `type` varchar(20) default '' COMMENT '类型',
  `title` varchar(255) default '' COMMENT '标题',
  `source_url` varchar(255) default '' COMMENT '来源地址',
  `source` varchar(255) default '' COMMENT '来源',
  `content` text NOT NULL,
  `add_time` datetime default NULL,
  `edit_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息表'


create table time_info (
id bigint(11)  not null ,
title varchar(255) default '' comment '标题，与微博一样很少字',
type varchar(20) default '' comment '类型',
add_time datetime not null,
start_time datetime ,
end_time datetime,
primary key (id)
)Engine =Innodb default charset =utf8 comment ='时间管理表';

create table  test(
 id int(11) not null auto_increment,
 info varchar(255) default '',
 primary key(id)
)engine=Innodb default charset=utf8



-- ----------------------------
-- Table structure for job_task
-- ----------------------------
CREATE TABLE `job_task` (
  `TASK_ID` int(11) NOT NULL comment '任务的主键', 
  `TASK_CODE` varchar(255) default NULL comment '任务编码 ，这里启动任务的时候，作为qz任务的名称',
  `TASK_TYPE` varchar(255) default NULL comment '任务类型，这里启动任务的时候，作为qz任务的分组',
  `TASK_IMPL_CLASS` varchar(255) default NULL comment '任务的类',
  `TASK_EXPRESS` varchar(50) default NULL comment '任务执行表达式',
  `STATE_DATE` datetime default NULL comment '任务更新时间，这里在任务运行中，如果需要使运行中的任务修改立马生效，需要把这个字段的值设置大于当前时间。',
  `STATE` varchar(2) default NULL COMMENT '任务状态  U 使用中 O已结束',
  `PARMS` varchar(500) default NULL comment '任务初始化参数，任务运行的时候，可以从JobDataMap对象中获取该字段的值。',
  `REMARK` varchar(2000) default NULL,
  `CREATE_DATE` datetime default NULL comment '创建日期，没有什么实际意义。',
  PRIMARY KEY  (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf-8;


-- ----------------------------
-- Table structure for task_log
-- ----------------------------
CREATE TABLE `task_log` (
  `TASK_LOG_ID` int(8) NOT NULL comment '任务日志编号',
  `TASK_ID` int(8) default NULL comment '任务编号 ',
  `STATE` varchar(2) default NULL COMMENT '执行状态 O结束，R运行中，E异常结束 ，如果是E 的，说明异常了，异常信息会放在REMARKS字段中 ',
  `START_DATE` datetime default NULL comment '执行开始时间',
  `FINISH_DATE` datetime default NULL comment '执行结束时间',
  `REMARKS` varchar(2000) default NULL comment '备注',
  PRIMARY KEY  (`TASK_LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf-8;
