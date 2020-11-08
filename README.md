# sqlExecutorTest
一个动态生成SQL语句的小工具

> 在编写SunshineAirline项目时，编写一半业务后发现后端变得越来越臃肿，决定将后端进行优化，先从dao层开始，我的想法是将sql的联表查询语
> 句全部拆分，从而像出了一种方式，把所有表的mapper全部写成通用的增、删、改、查格式，自己编写一个数据库查询工具，把所有的mapper组织起
>来完成联表的操作

### 具体功能
* 使用java语法编写类似sql的语句并直接执行，工具动态生成sql语句，与mybatis结合执行，这个工具的本质就是一个动态生成sql语句的工具

* 语法：

  ```java
  SQL sql = new SQL();
  
  sql.select(all)
      .from(city)
      .where(equal(city.cityName, "Beijing",in(city.cityCode, "PEK", "……"))
      .executeQuery().forEach(System.out::println)
  ```

  
