## Spring Security
### Spring Security简介
spring security的核心功能包括：
+ 认证
+ 授权
+ 攻击防护

### Spring Security中的核心概念
+ **AuthenticationManager**：用户认证管理类所有的认证请求都会通过提交一个token给AuthenticationManager的authenticate()方法来实现,具体校验动作会由AuthenticationManager将请求转发给具体的实现类来做。根据实现反馈的结果再调用具体的Handler来给用户以反馈。
+ **AuthenticationProvider**：认证的具体实现类，一个provider是一种认证方式的实现，比如提交的用户名和密码是与通过数据库中查出的user对象做对比实现的，那就有一个DaoProvider
> AuthenticationManager只是一个代理接口，真正的认证就是由AuthenticationProvider来做的。一个AuthenticationManager可以包含多个Provider，每个provider通过实现一个support方法来表示自己支持那种Token的认证。
+ **UserDetailService**：用户认证通过Provider来做，所以Provider需要拿到系统已经保存的认证信息，获取用户信息的接口spring-security抽象成UserDetailService
+ **AuthenticationToken**：所有提交给AuthenticationManager的认证请求都会被封装成一个Token的实现，比如最容易理解的UsernamePasswordAuthenticationToken。
+ **SecurityContext**：当用户通过认证之后，就会为这个用户生成一个唯一的SecurityContext，里面包含用户的认证信息Authentication。通过SecurityContext我们可以获取到用户的标识Principle和授权信息GrantedAuthrity。在系统的任何地方只要通过SecurityHolder.getSecruityContext()就可以获取到SecurityContext。


### 使用Spring Security + jwt登录
**整体流程**
1. 首先写一个JwtUtils类，封装一下有关Jwt的方法，建议声明为abstract类，根据需要创建jwt字符串
2. 常规的Mapper操作数据库，Service写业务逻辑；唯一不同的是Spring Security提供了一个UserDetailsService接口供权限框架使用，需要实现里面的loadUserByUsername根据用户名获取用户信息
3. UserDetailsService接口的loadUserByUsername方法返回一个UserDetails实现类，用于封装一些关于用户权限的信息，需要我们自己写一个类，去实现这个接口
4. 配置Filter，关键的两个Filter：
   + 一是拦截用户登录请求的Filter，继承UsernamePasswordAuthenticationFilter类，从请求中获取用户信息，并且验证登录
   + 另一个是认证权限的Filter，继承BasicAuthenticationFilter，主要用于判断当前用户是否有权限去访问某个资源
5. 编写Security的基本配置类，继承WebSecurityConfigurerAdapter

### 参考博文
https://blog.csdn.net/qq_41463655/article/details/107279778
https://www.jianshu.com/p/d5ce890c67f7  强烈推荐看一看
https://github.com/echisan/springboot-jwt-demo/blob/master/blog_content.md
