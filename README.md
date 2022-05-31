# CONNECT
An Informal Social Media App for networking and connecting professionals by their interests and aspirations.

## PROJECT VISION

Formal networks within organisations offer little room for emotions, feelings or sharing of personal thoughts. By relying on Connect, professionals can enjoy easy conversations and bond, without the pressures of conforming strictly to generic corporate culture. 

Connect aims to do this by creating a network of professionals, ready to support and bond with each other over common career interests. Connect will encourage users to attend networking events and socials with others on the site, allowing for relationships to strengthen and develop further. 

## ENTITY RELATIONSHIPS

![alt text](https://github.com/LMBroadhurst/backEndProjectGroup5/blob/main/ERD%20Final%20-%20BEP%20(3).jpg)
### Figure 1 - ERD diagram representing Connect's database

A Postgres database was used to store all information associated with Connect, such as user information, comments, posts, interests etc. **Figure 1** showcases how entities were related to one another in Connect's database. How each table relates to another is summarised in the following list:

### Users 
- Users to Comments : one to many
- Users to Posts : one to many 
- Users to Message : one to many
### Posts
- Posts to Comments : one to many 
- Posts to Post Types : one to one 
### Comments
- Comments to Replies : one to many 
### Mappers
- Users to Interest Mapper : one to one
- Interest Mapper to Interest Types : one to one 

As can be seen, most of the database extends out of two central tables, these tables being **Users** and **Posts**.   

**Interest Types** and **Post Types** are tables consisting of only enum data types. **Users** has a many to many relationship with **Interest Types**, so, a mapper table named **Interest Mapper** is used to map a user to his/her particular interest(s). 

## CLASS DIAGRAMS

![alt text](https://github.com/LMBroadhurst/backEndProjectGroup5/blob/main/Class%20Diagrams%20-%20BEP.jpg)
### Figure 2 - Class diagram representing Connect's model, repo, service, and controller classes

Diagram displaying how connect's classes come together from model through to repository.


## DEPENDENCIES, DATABASE INITIALISATION AND APPLICATION PROPERTIES

### CONNECTING TO A POSTGRES DATABASE

The dependencies required to connect to, and work with, a postgres database using Spring JDBC and Spring JPA  are as follows:
``` java
<dependency>
        <groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
	<version>42.3.6</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>		
```

The application properties required to connect to, and work with, a postgres database are as follows:

```java
spring.datasource.url=jdbc:postgresql://localhost:5432/connectdatabase
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect 
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

The name of the postgres database in this project is "connectdatabase". This database is connected to using line 1 of the application properties snippet above. 

Line 4 of the code snippet will influence how the schema tool manipulates the database schema at application startup. Leaving this setting as "create" should allow for the tables in the schema.sql to be properly initialized in our postgres database for now.

Line 5 of the application properties snippet will specify that the type of databse used in hibernate is Postgres, enabling hibernate to generate the appropriate type of SQL statements.

Lines 6 and 7 will log autogenerated SQL statements for debugging purposes.

### CREATING AND INITIALIZING TABLES USING SCHEMA.SQL AND DATA.SQL

The following data types and tables are created using the schema.sql file automatically on application start-up:

- Enum data type : **interests_career**
- Table : **Interest_Types**
- Table : **Interest_Mapper**
- Enum data type : **posts_type**
- Table : **Post_Types**

The **Posts** table is also altered using the schema.sql file, adding a foreign key referencing the **Post_Types** table.

In order to stop Spring from recreating duplicates of these tables and data types on each application start-up (and in the process, producing an error), the tables and data types are dropped at the start of the file. This was a quick fix to the problem of duplicate values, and may add to the time taken for the application to run.

The tables were then populated using the data.sql file.

The following snippet from application properties is what allows for Spring to run through the schema.sql and data.sql files upon application start-up:

```java
spring.sql.init.mode=always
```

Without this property, script-based initialization is performed only for embedded databases, such as H2. The schema and data scripts would thus not be used for database inialization.

The following snippet from application properties ensures that Hibernate schema creation is performed, then schema.sql is read for any additional schema changes and, finally, data.sql is used for database population.

```java
spring.jpa.defer-datasource-initialization=true
```
### POSTGRES TEST CONTAINERS DEPENDENCIES

The following dependencies were procured from the test container's website, enabling repositories to be tested with a postgres test container running in a docker container:

```java
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <version>1.16.3</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.16.3</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.16.3</version>
    <scope>test</scope>
</dependency>
```
In order to properly run the Comment repository tests, docker must be installed and ran.

## REQUESTS AND ENDPOINTS



## Quirky Behaviours
Please note that a lot of the methods produce clean String outputs rather than the expected JSON format, to improve our presentation. This should of course be removed and replaced with the relevant JSON formatting when being used as a 'true' backend API.

We have removed some features, namely spring security/thymeleaf, friends, and replies to simplify the project. If anything refers to these features please delete/comment them out.


