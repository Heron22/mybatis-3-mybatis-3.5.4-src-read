package mytest;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/26 17:59
 * @Version V1.0
 */
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
public class TestPlugin implements Interceptor {
  private String username ;

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    System.out.println("插件拦截器增强逻辑");
    Object proceed = invocation.proceed(); //执行原来的业务逻辑
    System.out.println("插件拦截器增强逻辑");
    return proceed;
  }

  @Override
  public void setProperties(Properties properties) {
    username = properties.getProperty("username");
  }
}
