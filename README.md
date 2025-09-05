# 项目说明

本项目是一个开源的提供给WebUI自动化测试框架封装时调试用的网页demo

**最低兼容JDK8**

## 调试网页说明

包含两个简单页面：

1. **注册登录页**
   
   ![](docs/image/login-register-page.png)
   
   访问地址：`http://127.0.0.1:8080/login`

2. **登录态检查页**
   
   ![](docs/image/cookie-check-page.png)
   
   访问地址：`http://127.0.0.1:8080/cookie_check`

#### 特点：

- ✅ **一键部署**：`java -jar` 即可运行（无需Tomcat/数据库）

- ⚠️ **数据临时性**：每次运行清空上次数据（需重新注册）
