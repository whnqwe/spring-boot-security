# spring-boot-security

> 

## 客户端安全

### CSRF

> 跨站请求伪造（英语：Cross-site request forgery），也被称为one-click attack或者session riding，通常缩写为CSRF 或者XSRF， 是一种挟制用户在当前已登录的Web应用程序上执行非本意的操作的攻击方法。

#### 术语解释

##### CSRF Token
> 服务端为客户端生成令牌，该令牌将用于请求合法性校验，一般通过请求头或请求参数传递到服务端

##### CSRF Token 仓库
> 服务端组件，用于从请求加载或生成 CSRF Token。Spring Security 提供了Cookie 和 HttpSession 两种实现。

##### CSRF 请求校验匹配器
> 服务端组件，用于判断请求是否需要CSRF校验

#### 防攻逻辑

1. 利用 CSRF Token 仓库 将 HTTP 请求获取 CSRF Token（该过程 可以理解为 Web 服务端针对当前请求获取 CSRF Token）。

2. 通过 CSRF Token 校验请求匹配器 来判断当前请求是否需要 CSRF Token 校验。如果需要的话，执行第3步，否则，跳过校验。

3. 先从请求头中获取 CSRF Token 值，如果不存在的话，再向请求参数中 获取。（该过程可以理解为 获取 Web 客户端请求中的 CSRF Token）。如果均未获取的话，将会转向错误页面，并且相应头状态码为：403。

    - 如果 CSRF Token 值 获取到的话，执行第4步。

    将第1步CSRF Token 仓库 获取的 CSRF Token 与 客户端请求中的 CSRF Token 进行比较。

    - 如果两值相同的话，说明 CSRF Token 校验通过
    - 否则，CSRF Token 检验失败，将会转向错误页面，并且相应头状态码为：403。

### CSRF Token 仓库

1. Cookie 类型（默认）

> 框架接口：org.springframework.security.web.csrf.CsrfTokenRepository

> 实现类：org.springframework.security.web.csrf.CookieCsrfTokenRepository

> CSRF Token 存储：客户端，Web浏览器 Cookie

> 有效时间：Web浏览器 会话期间

> 特别注意：Cookie 方式安全系数相对较低

2. HttpSession 类型

> 实现类：org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository

> CSRF Token 存储：服务端，HttpSession（Servlet 容器）

> 有效时间：HttpSession 最大不活动时间间隔（#setMaxInactiveInterval(int) ）

> 特别注意：Servlet 容器需要支持HttpSession复制（分布式HttpSession）






   

## 服务端安全




## Spring Boot Security整合

