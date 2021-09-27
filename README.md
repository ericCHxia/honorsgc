## 环境变量

|名称|描述|
|---|---|
|DB_URL|数据库地址|
|DB_USERNAME|数据库名称|
|DB_PASSWORD|数据库密码|
|LOG_PATH|日志目录|
|LOG_LEVEL|日志级别|
|FORMAT_SQL|格式化打印的SQL|
|SHOW_SQL|在日志中打印SQL|
|IMAGE_PATH|上传图片的地址|
|IMAGE_MAX_SIZE|图片的最大大小|

## 源工程数据迁移注意事项
### 清除重复数据
- 清除重复注册
- 手动调整出勤记录使得(gttid-count)唯一
- 手动调整个人出勤记录使得(gttid-count-usrid)唯一
- 向个人出勤记录添加指向出勤记录的外键recid
- 删除出勤记录的count字段
- 删除个人出勤记录字段
- 删除个人出勤记录的tim字段
- 维护外键

## 未完成事宜

- [x] Oauth2验证
- [x] 共同体编辑器markdown化 
- [x] 文章标签管理
- [ ] 文章标签名称唯一化
- [ ] 文章标签编辑
- [x] 文章编辑器markdown化
- [x] 文章删除
- [ ] 文章管理隐藏
- [ ] 文章管理批量删除
- [x] 共同体类型管理
- [x] 共同体类型添加
- [ ] 共同体类型搜索
- [ ] 共同体人员管理
- [ ] 单共同体打卡信息导出
- [x] 共同体打卡 
- [x] 共同体打卡记录删除
- [x] 共同体打卡信息导出（按时间筛选）
- [x] 共同体批量删除 
- [x] 共同体批量隐藏 
- [x] 清除掉批量删除和批量隐藏之间的空隙 
- [x] 添加用户管理界面 
- [x] 用户修改界面
- [ ] 用户密码重置
- [x] 用户信息保存
- [x] 用户管理界面搜索
- [x] 添加共同体类型管理界面
- [x] 我要参与我要指导 
- [x] 获取真实登录ip
- [x] login?error message为null
- [ ] 图片异步上传（可浏览）
- [ ] 删除记录确认框
- [ ] 封面为空的时候替代图
- [ ] 编写用户删除触发器，使得标签被删除时所有者改变
- [ ] 图片管理数据表
- [ ] 共同体全局管理