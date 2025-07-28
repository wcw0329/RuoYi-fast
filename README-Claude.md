# Claude API 用量工具

## 项目简介

Claude API 用量工具是一个基于若依框架开发的 Claude API 使用量监控和管理系统。该工具提供了完整的 API 调用记录、使用量统计、费用计算和权限管理功能。

## 主要功能

### 1. API 调用管理
- **API 调用记录**: 自动记录所有 Claude API 调用
- **实时监控**: 实时监控 API 调用状态和响应时间
- **错误处理**: 完善的错误处理和日志记录
- **会话管理**: 支持多会话管理和会话ID追踪

### 2. 使用量统计
- **用户统计**: 按用户统计使用量
- **时间统计**: 按日期、月份统计使用量
- **模型统计**: 按不同模型统计使用量
- **费用统计**: 详细的费用计算和统计

### 3. 费用管理
- **实时计算**: 根据官方定价实时计算费用
- **多模型支持**: 支持所有 Claude 模型的价格计算
- **费用限制**: 支持用户级别的费用限制
- **成本分析**: 提供详细的成本分析报告

### 4. 权限控制
- **用户权限**: 基于角色的权限控制
- **API 密钥管理**: 安全的 API 密钥存储和验证
- **使用限制**: 支持用户级别的使用量限制
- **审计日志**: 完整的操作审计日志

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.2.13
- **数据库**: MySQL
- **ORM**: MyBatis
- **安全**: Spring Security
- **HTTP客户端**: RestTemplate

### 数据库设计
```sql
-- Claude API 使用记录表
CREATE TABLE claude_usage (
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
);
```

## API 接口

### 1. 使用记录管理
```
GET    /tool/claude/usage/list          # 获取使用记录列表
GET    /tool/claude/usage/{id}          # 获取使用记录详情
POST   /tool/claude/usage               # 新增使用记录
PUT    /tool/claude/usage               # 修改使用记录
DELETE /tool/claude/usage/{ids}         # 删除使用记录
POST   /tool/claude/usage/export        # 导出使用记录
```

### 2. API 调用接口
```
POST   /tool/claude/api/send            # 发送消息到Claude
GET    /tool/claude/api/models          # 获取模型列表
POST   /tool/claude/api/validate        # 验证API密钥
POST   /tool/claude/api/calculate-tokens # 计算Token数量
POST   /tool/claude/api/calculate-cost  # 计算费用
```

### 3. 统计接口
```
GET    /tool/claude/stats/total         # 获取总使用统计
GET    /tool/claude/stats/user/{userId} # 获取用户使用统计
GET    /tool/claude/stats/date          # 按日期获取统计
GET    /tool/claude/stats/model         # 按模型获取统计
GET    /tool/claude/stats/today/{userId} # 获取用户今日使用量
GET    /tool/claude/stats/month/{userId} # 获取用户本月使用量
GET    /tool/claude/stats/comprehensive # 获取综合统计信息
GET    /tool/claude/stats/cost          # 获取费用统计
```

## 配置说明

### 1. 数据库配置
在 `application.yml` 中配置数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ry?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: password
```

### 2. Claude API 配置
在 `application-claude.yml` 中配置 Claude API 相关设置：
```yaml
claude:
  api:
    base-url: https://api.anthropic.com
    version: 2023-06-01
    default-model: claude-3-5-sonnet-20241022
    timeout: 30000
    max-retries: 3
```

### 3. 使用限制配置
```yaml
claude:
  limits:
    daily-token-limit: 1000000
    monthly-token-limit: 10000000
    daily-cost-limit: 10.0
    monthly-cost-limit: 100.0
```

## 安装部署

### 1. 环境要求
- JDK 1.8+
- MySQL 5.7+
- Maven 3.6+

### 2. 数据库初始化
```bash
# 执行数据库脚本
mysql -u root -p < sql/claude_usage.sql
```

### 3. 应用启动
```bash
# 编译项目
mvn clean package

# 启动应用
java -jar target/ruoyi.jar
```

### 4. 访问地址
- 应用地址: http://localhost:8080
- 默认账号: admin/admin123

## 使用指南

### 1. 配置 API 密钥
1. 登录系统后，进入 "Claude工具" -> "API调用"
2. 输入您的 Claude API 密钥
3. 点击 "验证API密钥" 确认密钥有效

### 2. 发送消息
1. 选择要使用的模型
2. 输入消息内容
3. 可选择系统提示词
4. 点击发送按钮

### 3. 查看使用统计
1. 进入 "Claude工具" -> "使用统计"
2. 查看总使用量、用户使用量、费用统计等
3. 支持按时间范围、模型类型进行筛选

### 4. 导出数据
1. 在 "使用记录" 页面
2. 设置查询条件
3. 点击 "导出" 按钮下载 Excel 文件

## 费用计算

### 支持的模型及定价
| 模型名称 | 输入Token价格 | 输出Token价格 |
|---------|-------------|-------------|
| claude-3-5-sonnet-20241022 | $3.00/1M | $15.00/1M |
| claude-3-5-haiku-20241022 | $0.25/1M | $1.25/1M |
| claude-3-opus-20240229 | $15.00/1M | $75.00/1M |
| claude-3-sonnet-20240229 | $3.00/1M | $15.00/1M |
| claude-3-haiku-20240307 | $0.25/1M | $1.25/1M |

### 费用计算公式
```
费用 = (输入Token数 / 1,000,000) × 输入价格 + (输出Token数 / 1,000,000) × 输出价格
```

## 权限说明

### 菜单权限
- `tool:claude:usage:list` - 使用记录查询
- `tool:claude:usage:add` - 使用记录新增
- `tool:claude:usage:edit` - 使用记录修改
- `tool:claude:usage:remove` - 使用记录删除
- `tool:claude:usage:export` - 使用记录导出
- `tool:claude:usage:stats` - 使用记录统计
- `tool:claude:api:send` - API调用发送
- `tool:claude:api:models` - API模型列表
- `tool:claude:api:validate` - API密钥验证
- `tool:claude:api:calculate` - Token计算
- `tool:claude:stats:view` - 统计查看
- `tool:claude:stats:export` - 统计导出

## 常见问题

### 1. API 调用失败
- 检查 API 密钥是否正确
- 确认网络连接正常
- 查看错误日志获取详细信息

### 2. 费用计算不准确
- 确认使用的是最新的定价表
- 检查 Token 计算是否准确
- 验证模型名称是否正确

### 3. 权限不足
- 联系管理员分配相应权限
- 确认用户角色设置正确

## 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 支持基本的 API 调用记录
- 实现使用量统计功能
- 添加费用计算功能
- 完善权限管理系统

## 技术支持

如有问题或建议，请联系：
- 邮箱: support@example.com
- 项目地址: https://github.com/example/claude-usage-tool

## 许可证

本项目基于 MIT 许可证开源。