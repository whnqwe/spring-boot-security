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


#### CSRF代码

> WebSecurityConfigurerAdapter.java
> CookieCsrfTokenRepository
> CsrfFilter
```java
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).requireCsrfProtectionMatcher(
                httpServletRequest -> httpServletRequest.getRequestURI().startsWith("/login")
        );
    }
}
```

### CSP

> CSP指的是内容安全策略，为了缓解很大一部分潜在的跨站脚本问题，浏览器的扩展程序系统引入了内容安全策略（CSP）的一般概念。这将引入一些相当严格的策略，会使扩展程序在默认情况下更加安全，开发者可以创建并强制应用一些规则，管理网站允许加载的内容。

> https://www.cnblogs.com/lmh2072005/p/6044542.html

> https://www.w3.org/TR/CSP2/
```java
 http.headers().contentSecurityPolicy("script-src https://code.jquery.com/");
```

### X-Frames-Options 

> 使用 X-Frame-Options 防止被iframe 造成跨域iframe

```java
 // 禁止跨域加载
http.headers().frameOptions().deny();
//同域可加载
http.headers().frameOptions().sameOrigin();
// 实现白名单方式
http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(new AllowFromStrategy() {
    @Override
    public String getAllowFromValue(HttpServletRequest request) {
        return "zhangspace.cn";
    }
}));
```

### XSS
> 跨站脚本（英语：Cross-site scripting，通常简称为：XSS）是一种网站应用程序的安全 漏洞攻击，是代码注入的一种。它允许恶意用户将代码注入到网页上，其他用户在观看网页时就 会受到影响。这类攻击通常包含了HTML以及用户端脚本语言。

> XSS攻击通常指的是通过利用网页开发时留下的漏洞，通过巧妙的方法注入恶意指令代码到网页， 使用户加载并执行攻击者恶意制造的网页程序。这些恶意网页程序通常是JavaScript， 但实际上也可以包括Java，VBScript，ActiveX，Flash或者甚至是普通的HTML。攻击成功后， 攻击者可能得到更高的权限（如执行一些操作）、私密网页内容、会话和cookie等各种内容。

```java
 // XSS header
http.headers().xssProtection().block(true);
```
从页面的方式解决
```thymeleafexpressions
${jsCode} : <div th:text="${jsCode}"></div>

${htmlCode} : <div th:utext="${htmlCode}"></div>
```



## 服务端安全




## Spring Boot Security整合

