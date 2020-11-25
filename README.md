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
### 记录

#### 2020-11-8：
* 这个工具经过一个星期的完善大概成了这个结构，编写这个工具的原因主要是之前编写的项目里面的联查语句太多了，每个联查语句过于复杂而且还要
编写对应的对象，所以自己决定编写一个sql工具，主要是个sql语句的动态生成器，生成语句后再使用mybatis编写的通用查询语句进行查询，update、
insert、delete语句主要是上面的步骤，而select会单独优化，就是把联查语句拆分成一条一条的单表查询语句，再使用java的stream api对单表查询
的结果进行连接实现连表查询的效果（该功能还未实现）
* 今日实现功能：
    * 因为编写时没有上传git进行记录，所以大致理一下现在的进度。
    * 之前实现sql语句的动态生成器是使用一个StringBuilder不断的进行字符串连接，拼接sql语句达成动态sql的目的，但使用这种方式要实现的字
    符串拼接方法太多，到现在这个方式已经实现了，但感觉太过繁琐，比较臃肿，所以决定编写一个字符串模板引擎来实现动态生成sql语句，其实编写
    一个字符串模板引擎的想法我早就有了，在之前前端编写时就想实现一个，但前端页面要实现的零散功能太多太杂所以就没弄了，没弄后发现layui
    居然自带模板引擎，就打消了我自己实现的念头，现在动态生成sql又是一个类似的问题，所以这次会尝试这写一写，于是今天就写了一些，匹配语法
    主要用的的正则表达式，再慢慢解析，目前已经实现了一点，但还是有点bug，比如两个表达式不能连在一起写会拼接不上。但大致问题已解决。
* 在这个工具上传前我也想着要上传git进行记录，但发现构建初始结构的时候写的太烂了，上传了也没有意义，还有一方面原因是我这几天过度成迷于
编写某些问题的细节了，导致实现一个功能后跳出来一看发现根本没有意义，就推翻了重写，就浪费了大量时间，这几天也认识到了自己的一些问题，比较
喜欢过度专研某些细节，不知道这是好事好事坏事，反正就浪费了时间提升了编码能力。

#### 2020-11-09:
* 今天将自己实现的模板引擎优化了一定，还是有很多不足，将模板引擎写进sql动态生成工具内的时候我发现了一个问题，我已经忘记了最初实现这个
工具类的目的了，我要实现的功能是将联表查询拆分成多个单表查询，将这多个单表查询的结果根据关联使用java的stream api连接起来实现多表查询
的结果，要实现这个功能我就不能将sql语句写进mybatis的xml文件内，必须自己编写一个动态生成sql的工具，编写动态sql的工具类就必须使用
StringBuilder进行字符串拼接但这样拼接显得格外麻烦，所以我实现了一个模板引擎，但这个模板引擎因为能力问题只实现了最基础的功能，不能完全
代替StringBuilder的字符串拼接，我又要想办法将这两个方式融合，我现在就困在了这里，完全陷入了这个字符串拼接游戏内，而核心的功能都没实现
所以我必须要来跳出来看一看，我要根据我最初要实现的功再做分析。
  
### 2020-11-10:
* 今天思考了一下，因为模板引擎的功能不完善而且连拼接字符串的效率远低于StringBuilder就不用了，这个模板引擎的基础功能已经实现了，就算是
复习了下正则的语法，之间那个SQLStringUtil里面拼接字符串的方法太杂了，所以编写了一个StringUtil工具，将最基础的几个方法编写在这里，SQL
StringUtil就重写，重写了几个方法后发现我写的代码内有一类代码很冗余又很难分离，就是使用instanceof关键字时，如果匹配的话还要强转，所以
就写了个instanceof工具类，我真是工具狂魔，语法如下：
```java
    Instanceof ele = Instanceof.ele("123");

    ele.is(String.class, s -> {
        System.out.println("String => " + s);
    }).isArray(a -> {
        System.out.println("Array => " + a);
    }).isList(l -> {
        System.out.println("List => " + l);
    }).other(o -> {
        System.out.println("other => " + o);
    });
```
* 具体实现在那个类里的每个方法都有说明，今天收获很多。

### 2020-11-11:
* 今日实现功能：
    * 编写SQLStringUtil的拼接where后面条件的方法，根据操作符不同进行不同的拼接。优化InstanceOf工具类实现有返回值
    
### 2020-11-13：
* 今日实现功能：
    * 编写SQLStringUtil拼接查询参数的方法，尝试重写编写这个工具的传参模式。
* 今天在编写代码时终于深刻的理解了一把设计模式的重要性，之前在编写这个工具的传参模式的时候每个类之间的关系都是走一步看一步，只要能实现
就行，但到现在回看自己之前有多傻，果然有些东西你不自己实践实践是学不来的。

### 2020-11-19：
* 因为之前意识到自己设计的工具结构有问题，所以这几天就去看了看设计模式和一点多线程的东西，在看的设计模式的时候，有些模式感觉用过，只是
没有设计模式那么优雅而已，在看的时候，我就想到了我的代码里面的那些位置可以使用到这些，实在忍不住了就过来改代码了。
* 在设计StringUtil时，我用的是一个StringBuilder进行所有的字符串拼接操作，用完一次就把这个StringBuilder清空，但我的项目是web项目，是
多线程的，这种方式在多线程下会出很大的问题，所以我就开始想如可解决这个问题，开始想到的是将每个拼接字符串的方法加锁，当一个线程拼接完并
清空StringBuilder后就接着下一个，但这种方式想想效率就不行，所以目前想实现的方式是编写一个StringBuilderFactory专门获取StringBuilder
这个factory里面有一个StringBuilder数组，在多线程环境下，每个线程通过StringBuilderFactory获取StringBuilder对象时如果StringBuilder
数组内有空闲的StringBuilder时就返回空闲的，没有就创建一个并返回，这样每个线程的StringBuilder对象都不一样，就避免了后面出现的问题。
* 目前这个StringBuilderFactory已经编写了一点，在测试的时候 线程少的情况下可以达到理想的效果，但线程多了后就又乱了，目前我的还没学到
juc，这个factory尽量使用synchronized完成，锻炼锻炼我多线程和锁的能力，也为以后学juc做铺垫。

### 2020-11-20：
* 今天继续编写StringBuilderFactory，在空闲的时候我就想，这个工厂在获取StringBuilder的时候是根据这个对象的长度是否为0判断这个对象是否
空闲的，但如果我在操作这个StringBuilder的时候刚好需要清空一下，而这个刚好被工厂判断为空闲又刚好是空闲数组的第一个那不就又出问题了，在
想到这个bug时我就立马想到了一个解决方案：使用适配器模式将StringBuilder适配一下不就行了，这样还可以把拼接sql所需要的StringBuilder的功
能就封装在这个适配器内，所以我就开始编写这个StringBuilderAdapter，StringBuilder对象使用构造器传入，在这个适配器内就可以判断是否可用
了，在编写完这个适配器类并运行后我突然发现居然把昨天线程多了后StringBuilder混乱的问题解决了，我都还没分析那个bug的原因就解决了，真是
惊喜。
* 编写完后我我做了如下几轮测试：
    * 模拟线程少的情况（通过）
    * 模拟线程多的情况（通过）
    * 模拟线程由少到多的情况（通过）
    * 模拟线程由多到少的情况（通过）
* 可以说是非常完美了，但我在测试模拟线程由多到少的情况想到了两个隐性的异常
    * 一个是在线程多的情况下，工厂里面的集合只会越来越大（表示对象创建的越来越多）需要编写一个自动清除里面对象的功能。
    * 第二个跟就是我在编写获取空闲的StringBuilder集合内的元素是只返回空闲集合内的第一个，第二个线程进来后也是同样的步骤，这样效率就非
    常低了，所以我就跟该了一下获取的步骤，在获取到空闲StringBuilder集合后后面的线程都根据一个index获取空闲的对象，这功能实现后我又测
    试了一下，感觉跟之前的方式没什么区别，创建的对象也是浮动的，这也可能是我编写的问题，
### 2020-11-21：
* 今天把我之前实现的在多线程环境下对象的创建的方法整理了一下，发现这可以抽离一下实现一个通用的，于是我就编写了一个DynamicObjectPool
  这个对象跟前面那个StringBuilderFactory是一样的，池这个概念相对于工厂这个概念更符合我所要实现的功能一些，而里面的具体对象集合使用的是
  Reusable接口，继承这个接口后要实现几个方法对应里面获取对象时的具体操作，这样分配了一下后这个就通用了，只要实现了Reusable接口并实现了
  对应方法的类都可以在多线程情况下动态创建了。

* 在昨天还有个问题未解决，就是优化了跟原来的没什么区别，今天看了一下，果然了是我编写出了问题，有一个地方没锁到，但今天没将那个问题更改
  ，而是在编写DynamicObjectPool的时候顺便将重写了一遍，感觉这次编写的方式大概是目前为止的最好的方式了，过程也没出什么大问题，于是使用
  昨天的方式进行测试并对比了一下：

  |       情况\方式        |        昨天的方式        |                        今天的方式                        |
  | :--------------------: | :----------------------: | :------------------------------------------------------: |
  |    模拟线程少的情况    |       创建对象较少       | 创建的对象的多少跟开启的线程个数一样（这个感觉负优化了） |
  |    模拟线程多的情况    | 创建对象的多少每次都一样 |                           一样                           |
  | 模拟线程由少到多的情况 |  创建对象的多少浮动较大  |   浮动较小，创建的对象较少（这个算是优化比较明显的了）   |
  | 模拟线程由多到少的情况 |  创建对象的多少浮动较大  |          依然浮动较大，但创建对象少的情况会更少          |

* 测试情况大致是上面这样

* 在模拟线程少的情况下出现这个结果刚开始蛮奇怪，怎么就负优化了，但想了想发现事情不简单，可能是我锁的太精准了，导致cpu在多线程环境下也发挥出了它原来的性能，这也不算坏吧。

* 获取对象功能完善后接下了就是自动清除里面对象的功能。

### 2020-11-22：
* 今天尝试了实现自动清除里面对象的功能。最开始的想法是在get方法前面监听一下看每两个线程之间进入的时间是否相隔较长，是则开始清除，过程中
如果有线程进来了就从新开始，但在这种多线程环境下每个线程都有可能执行到这些方法，不加锁就又乱套了，加锁又显得麻烦，毕竟这不是主要的代码
所有就想到了在守护线程内执行这些操作，在测试时，我想实现一个功能，就是每个线程进入时守护线程都能获取到这些线程的name并输出，在实现时就
遇到了麻烦，因为守护线程内的代码就守护线程一个线程执行，所有没必要加锁，但线程的name每时每刻都在变，执行时守护线程一个name都没输出，在
遇到这个问题后我准备换个思路。守护线程循环wait，如果有线程notifyAll叫醒守护线程时守护线程就输出name，这个方法虽然好，但一运行就出问题了，
控制台报了一个IllegalMonitorStateException，好像这两个方法要在特定的情况才能使用，原谅我多线程没学好，找了半天都没解决这个问题，所以我
就去补多线程的知识了，在看到volatile关键字时，有一个作用叫保证线程的可见性，这个作用感觉跟我第一种方式有点关系，在加上volatile关键字后，
运行后的结果虽然没输出全部线程的name但也输出了大部分了name，这个很好理解，volatile关键字保证线程的可见性，说明守护线程可以准确的知道
name的值被修改并获取值，但在输出时，因为没有加锁就不能保证实际输出的name和我想要输出的name是不是同一个了，这个小功能给我要实现的功能提供
了很多思路，比如守护线程监听线程进来时的毫秒数与上一个线程进来时的毫秒数进行对比，超过一定的时间后再执行对应的清空操作，因为真实的情况
下，没有必要监听每两个线程进来时的时间，这个时间间隔通常时多少纳秒，所以只用监听一下每一波线程进来时的时间间隔就行了，接下来就轮到我开始
操作了

### 2020-11-23：
* 今天绕了一个非常大的圈，在实现自动清除里面对象的功能，我昨天的想法是：监听一下每一波线程进来时的时间间隔的毫秒数进行对比，超过一定的
时间后再执行对应的清空操作。在此基础上我就想要精准的记录每个线程进入时间，所以我发现了一个方法`System.nanoTime()`就是获取1970-1-1至今
的纳秒，更精准，可以获取每个线程进入时的准确时间，在一通操作后，我发现我有白费了半天，我都说了时记录线程一波一波的时间，但我现在却在纠结
这个纳秒，不过这些时间不算浪费，我了解到了一个时间单位工具`TimeUnit`，这里面大部分是时间间的转换，还有线程sleep的方式。过后我就换了个
思路使用定时操作，隔一段时间查看当前的毫秒数是否被修改，没有则执行清除操作，我就想到了java里面有个定时执行执行操作的类，类似js的setTimeout
百度后发现是Timer和TimerTask配合使用，又做一通操作后发现不行，因为Timer执行事件不能像守护线程一样用户线程全部结束后结束。在最后我还是
使用守护线程执行延时操作，真是绕了一大圈。
* 现在遇到的问题是：怎么记录到一波线程的最后一个线程进入时的时间戳
### 2020-11-24：
* 今天这个多线程分配对象的工具的核心功能终于算是大致完成，主要核心功能为：

    1. 在多线程环境下分配对象

       * 一个线程进入后获取对象并使用，如果这个对象用完后可以回收给下一个需要创建这个对象的线程使用，这样如果有大量的线程需要使用这个对象，只需要创建少量的对象再根据每个线程进入时的时机进行分配就行了。

       * 这个对象一般是工具对象，像StringBuilder之类的，以此保证这个对象在多线程环境下的安全性。

    2. 根据固定的模式将对象池内的对象进行清理

       * 因为对象池内的对象不会自动回收，如果超大量的线程进入后，创建的对象数也会不少，所以需要将对象进行回收。

* 今天主要是实现了第二个功能，在昨天绕了那么一大群后我分析了一下，我昨天主要在纠结线程进来时的毫秒或纳秒数，并想准确的获取到这个数值后进行操作，正因为这些时间不好准确的获取，所以导致我卡了那么半天，在昨天睡觉时我想到了一个场景，在一些商场或餐厅的门都是自动门，这些自动门的开门模式都是有人来了自动开门，如果后面接着有人了就不关，隔几秒后没人的话就关了。这模式不是跟我要实现的功能一样吗，我之前还想怎么记录到一波线程的最后一个线程进入时的时间戳，但现在看来根本不用什么时间戳了，根据这个模式就能解决，如果有线程来就不清理对象池，没有就开始清理，在清理的过程中如果有线程来了就打断，这就只需要一个布尔值就行了，所以今天根据这个方式，再加上了亿点点就实现了。原来活得时间长了，问题的答案都在生活里啊，我悟了(⊙ˍ⊙)

* 明天可以开始更该下这个工具的结构了，现在这个完全是个静态工具，可以改成每个对象都可以获取对象池。

### 2020-11-25：

* 今天将这个工具简单的封装成了一个工具对象，并使用对应的builder创建

