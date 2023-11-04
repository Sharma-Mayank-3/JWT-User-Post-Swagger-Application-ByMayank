# JWT-User-Post-Swagger-Application-ByMayank

Demo Project for JWT.

# Entities 
1. User Entity
```properties
1. With Create getById and getAll options.
2. userId, userName, userPassword(encoded), userAge.
```

2. Post Entity
```properties
1. With Create, getByid, getAll, getByUserId options.
2. postId, postDescription, userId(one to many mapping, one user can do multiple post).
```

# Authentication vs Autherization
```properties
Authentication : user is valid user or not, userName and password.

Autherization : For above user, what actions or which api the user can hit. 
```

# Spring Security with JWT
# Steps 

1. Include Spring security dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
with only this above security dependency one can do form based login 
from web using userName and password defined in application.properties file.

NOTE : From postman go for basic auth in autherization section. 
```properties
# userName, Password and Role already defined in application.properties file.
#spring.security.user.name=mayank
#spring.security.user.password=mayank
#spring.security.user.roles=USER,ADMIN
```

NOTE : We are looking for JWT not form based login so comment the above properties in application.properties file and 

2. Create a Role Entity.

# Role Entity
```properties
1. roleId, roleName, userEntity(many to many)
2. In UserEntity class make fetch = FetchType.EAGER(many to many mapping)
```
3. Include JWT Dependencies
```xml
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

        <!-- https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api -->
<dependency>
 <groupId>javax.xml.bind</groupId>
 <artifactId>jaxb-api</artifactId>
 <version>2.3.1</version>
</dependency>
```

4. Create a package security
```properties
1. create class CustomUserDetailsService.
implement this class with UserDeatilService and see the changes.
implement UserEntity class with UserService class and see the changes.
```
a. CustomUserDetailsService class 


b. UserEntity class 


c. Create 3 more classes in security package.
```properties
1. JwtAuthentationEntryPoint
2. JwtTokenHelper
3. JwtAuthentationFilter
```

5. Create 2 classes in DTO
```properties
1. JwtAuthRequest
2. JwtAuthResponse
```
6. Create a JWT Exception class also include the JWT exception in the GlobalException class.


7. Create a AuthController class


8. Create SecurityConfig Class
```properties
create 4 beans inside this class 
1. SecurityFilterChain
2. AuthenticationManager
3. DaoAuthenticationProvider
4. PasswordEncoder
```

9. Run the Project, Now in DB Create 2 Roles in Role Table.
```properties
1. ROLE_ADMIN
2. ROLE_NORMAL
```

10. Create a RoleBaseController and create 3 api's 
```properties
1. api-1 can be accessed by Normal user
2. api-2 can be accessed by Admin user
3. api-3 can be accessed by All without autheticate

Note : This can be achieved by SecurityConfig securityFilterChaining Method.

admin can access admin
normal can access normal
```

11. One more way of defining which api can be access by which user
```properties
@EnableMethodSecurity in SpringSecurityConfig class

@PreAuthorize("hasAuthority('ROLE_ADMIN')") define on top of method which you want Admin to access

@PreAuthorize("hasAuthority('ROLE_NORMAL')") define on top of method which you want Normal to access
```


# Swagger Configuration
1. Include dependency 
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

2. Create a class SwaggerConfig

3. Permit all swagger api in security config
```properties
public static final String[] URL = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

swagger url : http://localhost:8081/swagger-ui/index.html
```

# NOTE : We don't want the password to come in response to dto
```properties
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
in UserEntityDto class on password field.
```

NOTE : 2 Users I have.
```properties
userName : user-6
password : admin

userName : user-7
password : normal
```

```properties
user-6 has both admin and normal user role.
now it can access admin, normal authorized api's.
```

NOTE : Reference Link
https://www.learncodewithdurgesh.com/blogs/jwt-authentication-with-spring-boot-31



Note : If above jwt dependency doesn't work then 
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>


     <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

With latest jwt pom dependency there might be a error in jwthelper class in getAllClaims, so use below code, it might help.
```properties
private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
```




