-- ----------------------------
-- Claude API 使用记录表
-- ----------------------------
drop table if exists claude_usage;
create table claude_usage (
  id                   bigint(20)      not null auto_increment    comment '主键ID',
  user_id              bigint(20)      not null                   comment '用户ID',
  user_name            varchar(30)     not null                   comment '用户名',
  api_key              varchar(100)    not null                   comment 'API Key',
  model_name           varchar(50)     not null                   comment '模型名称',
  request_type         varchar(20)     not null                   comment '请求类型',
  input_tokens         bigint(20)      default 0                  comment '输入Token数',
  output_tokens        bigint(20)      default 0                  comment '输出Token数',
  total_tokens         bigint(20)      default 0                  comment '总Token数',
  input_characters     bigint(20)      default 0                  comment '输入字符数',
  output_characters    bigint(20)      default 0                  comment '输出字符数',
  total_characters     bigint(20)      default 0                  comment '总字符数',
  cost                 decimal(10,6)   default 0.000000           comment '费用（美元）',
  status               varchar(20)     not null                   comment '请求状态',
  error_message        text                                       comment '错误信息',
  request_time         datetime        not null                   comment '请求时间',
  response_time        datetime                                   comment '响应时间',
  response_time_ms     bigint(20)      default 0                  comment '响应时长（毫秒）',
  ip_address           varchar(128)                               comment 'IP地址',
  user_agent           varchar(500)                               comment '用户代理',
  session_id           varchar(100)                               comment '会话ID',
  project_name         varchar(100)                               comment '项目名称',
  remark               varchar(500)                               comment '备注',
  create_time          datetime                                   comment '创建时间',
  update_time          datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb comment = 'Claude API 使用记录表';

-- ----------------------------
-- 索引
-- ----------------------------
create index idx_claude_usage_user_id on claude_usage (user_id);
create index idx_claude_usage_model_name on claude_usage (model_name);
create index idx_claude_usage_request_time on claude_usage (request_time);
create index idx_claude_usage_status on claude_usage (status);
create index idx_claude_usage_session_id on claude_usage (session_id);

-- ----------------------------
-- 初始化-菜单数据
-- ----------------------------
-- 父菜单ID
INSERT INTO sys_menu VALUES('2000', 'Claude工具', '0', '4', 'claude',           null, '', 1, 0, 'M', '0', '0', '', 'robot',           'admin', sysdate(), '', null, 'Claude API 工具管理');

-- 子菜单
INSERT INTO sys_menu VALUES('2001', '使用记录', '2000', '1', 'usage', 'tool/claude/usage/index',        '', 1, 0, 'C', '0', '0', 'tool:claude:usage:list',        'list',           'admin', sysdate(), '', null, 'Claude API 使用记录管理');
INSERT INTO sys_menu VALUES('2002', 'API调用', '2000', '2', 'api',   'tool/claude/api/index',          '', 1, 0, 'C', '0', '0', 'tool:claude:api:send',          'api',            'admin', sysdate(), '', null, 'Claude API 调用管理');
INSERT INTO sys_menu VALUES('2003', '使用统计', '2000', '3', 'stats', 'tool/claude/stats/index',        '', 1, 0, 'C', '0', '0', 'tool:claude:stats:view',        'chart',          'admin', sysdate(), '', null, 'Claude API 使用统计');

-- 按钮父菜单ID
INSERT INTO sys_menu VALUES('2004', '使用记录查询', '2001', '1',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:query',        '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2005', '使用记录新增', '2001', '2',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:add',          '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2006', '使用记录修改', '2001', '3',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:edit',         '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2007', '使用记录删除', '2001', '4',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:remove',       '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2008', '使用记录导出', '2001', '5',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:export',       '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2009', '使用记录统计', '2001', '6',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:usage:stats',        '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu VALUES('2010', 'API调用发送', '2002', '1',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:api:send',            '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2011', 'API模型列表', '2002', '2',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:api:models',          '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2012', 'API密钥验证', '2002', '3',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:api:validate',        '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2013', 'Token计算', '2002', '4',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:api:calculate',        '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu VALUES('2014', '统计查看', '2003', '1',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:stats:view',            '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('2015', '统计导出', '2003', '2',  '', '', '', 1, 0, 'F', '0', '0', 'tool:claude:stats:export',          '#', 'admin', sysdate(), '', null, '');