# Spring boot入门篇

## 什么是spring boot

---

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。用我的话来理解，就是**spring boot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，spring boot整合了所有的框架**（请注意加粗的内容，这是重点）。

## **使用**spring boot**有什么好处**
---

我们需要搭建一个spring web项目的时候需要怎么做呢？

1）配置web.xml，加载spring和spring mvc

2）配置数据库连接、配置spring事务

3）配置加载配置文件的读取，开启注解

4）配置日志文件

...

配置完成之后部署tomcat 调试

...



如果使用spring boot呢？

极少的配置就能搭建一套web项目或者微服务！！！



## 快速入门
---

### maven项目构建

1. 访问http://start.spring.io
2. ...



### 项目结构

- src/main/java  程序开发以及主程序入口
- src/main/resources 配置文件
- src/test/java  测试程序



root package结构：com.example.myproject

```java
com
  +- example
    +- myproject
      +- Application.java
      |
      +- repository
      |  +- CustomerRepository.java
      |
  	  +- bean
      |  +- Customer.java
  	  |
      +- service
      |  +- CustomerService.java
      |
      +- controller
      |  +- CustomerController.java
      |
```

1、Application.java 建议放到根目录下面,主要用于做一些框架配置，也是程序的主启动类

2、repository层主要是数据访问层（Repository）

3、bean目录主要是实体类代码

4、service 层主要是业务类代码

5、controller 负责页面访问控制

### 引入web模块

1、pom.xml中添加支持web的支持：

```xml
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

pom.xml文件中默认有两个模块：

spring-boot-starter：核心模块，包括自动配置支持、日志和YAML；

spring-boot-starter-test：测试模块，包括JUnit、Hamcrest、Mockito。

2、编写controller内容

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
```

@RestController的意思就是controller里面的方法都以json格式输出，不用再写什么jackjson配置的了！

3、启动主程序，打开浏览器访问http://localhost:8080/hello，就可以看到效果了，有木有很简单！



## web开发
---

spring boot web开发非常的简单，其中包括常用的json输出、filters、property、log等

### json 接口开发

在以前的spring 开发的时候需要我们提供json接口的时候需要做那些配置呢

1. 添加 jackjson 等相关jar包
2. 配置spring controller扫描
3. 对接的方法添加@ResponseBody

spring boot如何做呢，只需要类添加 ` @RestController ` 即可，默认类中的方法都会以json的格式返回

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/getUser")
    public User getUser() {
    	User user=new User();
    	user.setUserName("xxx");
    	user.setPassWord("xxxx");
        return user;
    }
}
```

如果我们需要使用页面开发只要使用` @Controller` ，下面会结合模板来说明



### 自定义Filter

我们常常在项目中会使用filters用于录调用日志、排除有XSS威胁的字符、执行权限验证等等。Spring Boot自动添加了OrderedCharacterEncodingFilter和HiddenHttpMethodFilter，并且我们可以自定义Filter。



两个步骤：

1. 实现Filter接口，实现Filter方法
2. 添加`@Configuration` 注解，将自定义Filter加入过滤链

```java
@Configuration
public class WebConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    
    public class MyFilter implements Filter {
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
		}

		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			HttpServletRequest request = (HttpServletRequest) srequest;
			System.out.println("this is MyFilter,url :"+request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
		}
    }
}
```



此外还可以使用`@WebFilter`注解定义过滤器信息

```java
@Configuration
@WebFilter(urlPatterns = {"/*"})
@Order(value = 1) //此注解为定义过滤器的优先级
public class WebAnnotationFilter implements Filter{
	
	private static Logger logger = LoggerFactory.getLogger(WebAnnotationFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		logger.info("请求url为：{}", request.getRequestURL());
		chain.doFilter(srequest, sresponse);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}

```



### 自定义Property

在web开发的过程中，我经常需要自定义一些配置文件，如何使用呢

1、 配置在application.properties中

```properties
com.title=培训
com.description=spring boot培训
```

2、自定义配置类

```java
@Component
public class SpringBootProperties {
	@Value("${com.title}")
	private String title;
	@Value("${com.description}")
	private String description;
}
```



### log配置

配置输出的地址和输出级别

```properties
logging.path=/user/local/log
logging.level.com.favorites=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=ERROR
```

path为本机的log地址，`logging.level ` 后面可以根据包路径配置不同资源的log级别



### 数据库操作

在这里我重点讲述mysql、spring data jpa的使用，其中mysql就不用说了大家很熟悉，jpa是利用Hibernate生成各种自动化的sql，如果只是简单的增删改查，基本上不用手写了，spring内部已经帮大家封装实现了。

下面简单介绍一下如何在spring boot中使用

#### 1. 添加jar包

```xml
<!-- 添加oracle驱动-->
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc6</artifactId>
    <version>11.2.0.1.0</version>
    <scope>runtime</scope>
</dependency>

<!-- 添加mysql驱动，使用什么数据库用什么驱动-->
<dependency>
  	<groupId>mysql</groupId>
  	<artifactId>mysql-connector-java</artifactId>
  	<version>5.1.27</version>
  	<scope>runtime</scope>
</dependency>

<!-- 添加jpa依赖 -->
<dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- 添加jdbc依赖-->
<dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

#### 2、在application.properties中添加配置文件

mysql配置：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql= true
```

oracle配置：

```properties
spring.datasource.url=jdbc:oracle:thin:@192.168.90.250:1521:workflow
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.dbcp.driverClassName=oracle.jdbc.driver.OracleDriver

spring.jpa.properties.hibernate.hbm2ddl.auto=update
```



其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构,有四个值：

1. create： 每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
2. create-drop ：每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
3. update：最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
4. validate ：每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。

`dialect` 主要是指定生成表名的存储引擎为InneoDB
`show-sql` 是否打印出自动生产的SQL，方便调试的时候查看

#### 3.使用方式

jpa使用方式：

```java
/**
 * 继承JpaRepository，两个泛型值为：实体（表）、实体（表）主键类型
 */
public interface UserRepository extends JpaRepository<User, Long> {}
```

jdbc使用方式：

```java
/**
 * 1、类上标注@Repository数据访问层注解
 * 2、使用@Autowired注解注入JdbcTemplate
 */
@Repository
public class JdbcDaoImpl{
	@Autowired
	private JdbcTemplate jdbcTemplate;
}
```



### 集成jsp模板

1、添加jsp依赖：

```xml
<!--jsp页面使用jstl标签 -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>

		<!--用于编译jsp -->
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
			<scope>provided</scope>
		</dependency>
```

2、在application.porperties中添加jsp配置

```properties
# 定义访问jsp前后缀
spring.mvc.view.prefix = /WEB-INF/views/
spring.mvc.view.suffix = .jsp
```



静态文件目录：

```java
src
  +- main
    +- resources
  	  +- static
        +- css
        +- fonts
        +- js
        +- image
```

jsp文件目录：

```java
src
  +- main
    +- webapp
  	  +- WEB-INF
  		+- views
  		  +- index.jsp
```

### 开发场景



#### ajax方式

1、controller接口代码

```java
@RequestMapping(value = "/web/create", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
```

2、ajax代码

```javascript
<script type="text/javascript">
		function save() {
			$.ajax({
	  			type : "POST",
	  			url : "/web/create",
	  			contentType : "application/json; charset=utf-8",
				data : JSON.stringify(data),
				success : function(msg) {
					console.log(msg);
				},
				error:function(){
					alert("error");
				}
	  		});
		}
	</script>
```

#### submit提交/页面跳转

1、controller接口代码

```java
	@RequestMapping(value = "/interactive/forward")
	public String saveUser(User user, Model model) {
		model.addAttribute("user", user);
		return "interactive/forward";
	}
```

2、表单代码

```html
<div>
  <form	 action="/interactive/forward"	method="post">
    <input type="text" class="form-control" id="name" name="name"
						placeholder="请输入姓名" value="${user.name }">
    <input type="text" class="form-control" id="age" name="age"
						placeholder="请输入性别" value="${user.age }">
    <input type="text" class="form-control" id="sex" name="sex"
						placeholder="请输入性别" value="${user.sex }">
    <button type="submit" class="btn btn-default">保存</button>
  </form>
</div>

```



#### 上传文件

1、controller接口代码

```java
	@RequestMapping(value = "/upload/file")
	@ResponseBody
	public String upload(@RequestParam MultipartFile file) throws IOException {
		InputStream fileStream = file.getInputStream();
        long size = file.getSize();
        return String.valueOf(size);
	}
```

`MultipartFile`是Spring上传文件的封装类，包含了文件的二进制流和文件属性等信息，在配置文件中也可对相关属性进行配置，基本的配置信息如下：

- `spring.http.multipart.enabled=true` #默认支持文件上传.
- `spring.http.multipart.file-size-threshold=0` #支持文件写入磁盘.
- `spring.http.multipart.location= `# 上传文件的临时目录
- `spring.http.multipart.max-file-size=1Mb` # 最大支持文件大小
- `spring.http.multipart.max-request-size=10Mb` # 最大支持请求大小



2 、html代码

```html
	<form method="POST" enctype="multipart/form-data" action="/upload/file">
        <div>
            <input type="file" name="file" class="file" data-preview-file-type="text""/>
            <button type="submit">导入</button>
        </div>
    </form>
```



可能会遇到文件大于10M出现连接重置的问题，解决办法如下：

修改主启动类：

```java
@SpringBootApplication
public class FileUploadWebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FileUploadWebApplication.class, args);
    }

    //Tomcat large file upload connection reset
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }
}
```



## 项目部署

---

项目部署分为两种方式，简单的小程序直接打成jar包启动即可。模块比较多的项目还是需要打成war包放入tomcat等容器中启动。

#### 直接打成jar包方式部署

1、maven命令`mvn clean install`打成jar包

2、启动命令：`java -jar xxx.jar --xxx.xxx=xxx`。其中--xxx.xxx=xxx为参数设置，比如当需要设置启动端口时,可以用java -jar xxx.jar --server.port=8100启动。



#### 打成war包启动方式，以放入tomcat容器中启动为例

1、 pom.xml中加入tomcat依赖

```xml
<!--配置外部tomcat-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-tomcat</artifactId>
   <scope>provided</scope>
</dependency>
```

2、 将pom.xml的首部的jar改成war

```xml
<groupId>com.xxx</groupId>
<artifactId>xxx</artifactId>
<version>0.0.1-SNAPSHOT</version>
<packaging>war</packaging>
<!--<packaging>jar</packaging>-->
```

3、修改主启动类

```java
@SpringBootApplication
public class SpringWebApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(YiyongApplication.class, args);
    }
}
```

4、执行maven命令`mvn clean install`打成war包

5、放入tomcat中的webapp下，修改为ROOT.war。然后启动startup.bat（windows）或者startup.sh（linux）



## 扩展

---

### 业务类常用工具类

基本上常用的工具类不是apache的就是google的。有兴趣可以多多学习使用工具类，提高开发效率

```xml
<!-- FileUtils、 IOUtils、 FilenameUtils -->
<dependency>
	<groupId>commons-io</groupId>
  	<artifactId>commons-io</artifactId>
  	<version>2.5</version>
</dependency>

<!-- StringUtils -->
<dependency>
	<groupId>commons-lang</groupId>
  	<artifactId>commons-lang</artifactId>
  	<version>2.6</version>
</dependency>

<!-- google提供的Collections功能 -->
<dependency>
	<groupId>com.google.guava</groupId>
  	<artifactId>guava</artifactId>
  	<version>21.0</version>
</dependency>

<!-- lombok注解，也就是实体中用到的减少代码量的注解 -->
<dependency>
	<groupId>org.projectlombok</groupId>
  	<artifactId>lombok</artifactId>
  	<version>1.16.10</version>
</dependency>
```



### 开发环境的调试

热启动在正常开发项目中已经很常见了吧，虽然平时开发web项目过程中，改动项目启重启总是报错；但springBoot对调试支持很好，修改之后可以实时生效，需要添加以下的配置：

```xml
<dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-devtools</artifactId>
  	<optional>true</optional>
</dependency>
```

