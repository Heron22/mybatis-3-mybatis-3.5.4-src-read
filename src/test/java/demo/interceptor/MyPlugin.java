package demo.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/27 10:59
 * @Version V1.0
 */
//代理对象需要用拦截器增加被代理的类，所以需持有被代理对象的引用和拦截器引用
public class MyPlugin implements InvocationHandler {

  //被代理的类
  private final Object target;
  //使用增强的拦截器
  private final MyInterceptor myInterceptor;
  //拦截的接口类型
//  private Map<Class, Set<Method>> signMap = new HashMap<>();
  private Class clazz;
  private Method meth;

  public MyPlugin(Object target, MyInterceptor myInterceptor ) throws Exception{
    this.target = target;
    this.myInterceptor = myInterceptor;
    //获取拦截器中的注解
    MyInter annotation =  myInterceptor.getClass().getAnnotation(MyInter.class);
    Class cla = annotation.clazz();
    //获取到注解中标记要拦截的接口和对应的方法
    this.clazz = cla ;
    this.meth = cla.getMethod(annotation.method(),annotation.args());
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //反射获取声明方法的类，判断声明的类和调用的方法是不是拦截器注解中要拦截的方法
    Class cla = method.getDeclaringClass();
    if (meth.equals(method) && clazz.equals(cla)) {
      return myInterceptor.doInterceptor(target,method,args);
    }
    return method.invoke(target,args);
  }

  //代理所有的方法
  public static Object plugin(Object target , MyInterceptor interceptor)throws Exception{
    Object proxy = Proxy.newProxyInstance(
      target.getClass().getClassLoader(),
      target.getClass().getInterfaces(),
      new MyPlugin(target, interceptor));
      return proxy ;
  }

}
