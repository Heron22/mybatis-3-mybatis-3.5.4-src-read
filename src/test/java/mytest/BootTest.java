package mytest;

import mytest.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Date;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/2/26 21:17
 * @Version V1.0
 */
public class BootTest {
  /**
  * 						Executor
   * 					SimpleExecutor
   * 			CachingExecutor
   * 		Plugin1 ---->> invoke
   * 	Plugin2 --->>  invoke
   * Plugin3 --->>  invoke
   *
   *
   * executor.update  ---> plugin3.invoke() --> interceptor3.intercept
            * 				---> plugin2.invoke() --> interceptor2.intercept
            * 				---> plugin1.invoke() --> interceptor1.intercept
            * 				---> cachingExecutor doSomeCacheAction --> simpleExecutor.update
  */

  @Test
  public  void insertTest()throws Exception {
    String resource = "mytest/mybatis-config.xml";
    //TODO 使用封装的资源加载方法进行配置读取
    InputStream inputStream = Resources.getResourceAsStream(resource);
    //TODO 构建会话工厂用于创建会话：会话工厂应该单例存在
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    //TODO 使用会话工厂创建会话操作数据库：工厂模式的体现
    try (SqlSession session = sqlSessionFactory.openSession()) {
      //TODO 获取到对应的操作数据库的方法（接口）的动态代理对象
      StudentMapper mapper = session.getMapper(StudentMapper.class);
      Student student = new Student("李注解","四川省顺德路",29,new Date(System.currentTimeMillis()-3600*1000*24*10));
      mapper.annoSave(student);
//      //TODO 提交事务
      session.commit();
//      Student stu = mapper.findByUsernameAndAge("李四",25);
//      System.out.println("stu="+stu);

    }catch (Exception e){
      e.printStackTrace();
    }
    System.out.println("======over========");
  }

  @Test
  public  void insertSqlSessionManager()throws Exception {
    String resource = "mytest/mybatis-config.xml";
    //TODO 使用封装的资源加载方法进行配置读取
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionManager sqlSessionFactory = SqlSessionManager.newInstance(inputStream);
//    try (SqlSession session = sqlSessionFactory.openSession()) {
      StudentMapper mapper = sqlSessionFactory.getMapper(StudentMapper.class);
      Student stu = mapper.getById(1);
      System.out.println("stu="+stu);
      Student student = new Student("赵六","四川省顺德路",25,new Date(System.currentTimeMillis()-3600*1000*24*10));
      mapper.save(student);
      //TODO 提交事务
//    sqlSessionFactory.commit();
//    }catch (Exception e){
//      e.printStackTrace();
//    }
    System.out.println("======over========");
  }

  public static void main(String[] args) {
    String sql = "SELECT * FROM student WHERE username = ? AND age = ? ";
  }

}
