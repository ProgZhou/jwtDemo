## Token
> 一个springboot + jwt简单登录验证demo
> 参考文章：
> 理论部分 -- https://www.baobao555.tech/posts/4cc42459/
> 代码部分 -- https://blog.csdn.net/qq_43948583/article/details/104437752?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_default&utm_relevant_index=1
### 基于Token的访问
+ 客户端使用用户名和密码请求登录
+ 服务器对输入内容进行校验，若账号和密码匹配则验证通过，登录成功，并生成一个token值，将其保存到数据库，并返回给客户端
+ 客户端收到token后可以把它存储起来，比如放到cookie中
+ 客户端每次向服务端请求资源时需要携带服务端签发的token，以后每次请求服务器时都携带该token（放在响应头里），提交给服务器进行校验
+ 服务端收到请求，然后去验证客户端请求里面带着的token，如果验证成功，就向客户端返回请求数据
> 注意：用户每进行一次登录，登录成功后服务器都会更新一个token新值返回给客户端
### Token访问的优势
+ **支持跨域访问**：cookie是无法跨域的，而token由于没有用到cookie(前提是将token放到请求头中)，所以跨域后不会存在信息丢失问题 
+ **无状态**：token机制在服务端不需要存储session信息，因为token自身包含了所有登录用户的信息，所以可以减轻服务端压力 
+ **更适用CDN**：可以通过内容分发网络请求服务端的所有资料 
+ **更适用于移动端**：当客户端是非浏览器平台时，cookie是不被支持的，此时采用token认证方式会简单很多 
+ **无需考虑CSRF**：由于不再依赖cookie，所以采用token认证方式不会发生CSRF，所以也就无需考虑CSRF的防御
### JWT简介
JWT是token的一种实现方式，全称为Json Web Token
通俗地说，**JWT的本质就是一个字符串**，它是将用户信息保存到一个Json字符串中，然后进行编码后得到一个JWT token，并且这个JWT token带有签名信息，接收后可以校验是否被篡改，所以可以用于在各方之间安全地将信息作为Json对象传输。
### JWT的工作流程
+ 首先，前端通过Web表单将自己的用户名和密码发送到后端的接口，这个过程一般是一个POST请求。建议的方式是通过SSL加密的传输(HTTPS)，从而避免敏感信息被嗅探
+ 后端核对用户名和密码成功后，将包含用户信息的数据作为JWT的Payload，将其与JWT Header分别进行Base64编码拼接后签名，形成一个JWT Token，形成的JWT Token就是一个如同lll.zzz.xxx的字符串
+ 后端将JWT Token字符串作为登录成功的结果返回给前端。前端可以将返回的结果保存在浏览器中，退出登录时删除保存的JWT Token即可
+ 前端在每次请求时将JWT Token放入HTTP请求头中的Authorization属性中(解决XSS和XSRF问题)
+ 后端检查前端传过来的JWT Token，验证其有效性，比如检查签名是否正确、是否过期、token的接收方是否是自己等等
+ 验证通过后，后端解析出JWT Token中包含的用户信息，进行其他逻辑操作(一般是根据用户信息得到权限等)，返回结果

### JWT结构
#### 1. Headers
JWT头是一个描述JWT元数据的JSON对象
alg属性表示签名使用的算法，默认为HMAC SHA256（写为HS256）
typ属性表示令牌的类型，JWT令牌统一写为JWT。
最后，使用Base64 URL算法将上述JSON对象转换为字符串保存
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```
#### 2. Payload
有效载荷部分，是JWT的主体内容部分，也是一个JSON对象，包含需要传递的数据。 JWT指定七个默认字段供选择
```
iss：发行人
exp：到期时间
sub：主题
aud：用户
nbf：在此之前不可用
iat：发布时间
jti：JWT ID用于标识该JWT
```
除以上默认字段外，我们还可以自定义私有字段，一般会把包含用户信息的数据放到payload中
```json
{
  "sub": "1234567890",
  "name": "Helen",
  "admin": true
}
```
> 默认情况下JWT是未加密的，因为只是采用base64算法，拿到JWT字符串后可以转换回原本的JSON数据，任何人都可以解读其内容，因此不要构建隐私信息字段，比如用户的密码一定不能保存到JWT中，以防止信息泄露。JWT只是适合在网络中传输一些非敏感的信息
#### 3. Signature
签名哈希部分是对上面两部分数据签名，需要使用base64编码后的header和payload数据，通过指定的算法生成哈希，以确保数据不会被篡改。首先，需要指定一个密钥（secret）。该密码仅仅为保存在服务器中，并且不能向用户公开。
