package demo.interceptor;

import java.lang.reflect.Method;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/27 22:26
 * @Version V1.0
 */
//拦截Speak 接口的 speak方法
@MyInter(clazz = Speak.class,method = "speak",args = {})
public class ExampleInterceptor implements MyInterceptor {

  @Override
  public Object doInterceptor(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.println("ExampleInterceptor");
      return method.invoke(proxy,args);
  }
}
