package demo.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/26 22:30
 * @Version V1.0
 */
//代理类要实现 InvocationHandler 接口
public class Man  implements InvocationHandler  {

  private DoAction speak;

  public Man(DoAction speak) {
    this.speak = speak;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //method.getDeclaringClass() 这里拿到实现的接口名
    // 如果method在多个实现的接口中都这个方法定义，则拿到的是实现的接口中的第一个
    System.out.println("============="+method.getDeclaringClass());
    return method.invoke(speak, args);
  }

}
