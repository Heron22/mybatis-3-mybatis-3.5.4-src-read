package demo.interceptor;

import java.lang.reflect.Method;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/27 22:33
 * @Version V1.0
 */
//拦截Speak 接口的 talk方法
@MyInter(clazz = Speak.class,method = "talk",args = {})
public class SecondIterceptor implements  MyInterceptor{

  @Override
  public Object doInterceptor(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("SecondIterceptor");
    return method.invoke(proxy,args);
  }
}
