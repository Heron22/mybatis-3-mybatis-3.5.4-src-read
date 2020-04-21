package demo.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/26 22:32
 * @Version V1.0
 */
public class Main  {

  public static void main(String[] args) throws Exception{
    DoAction action = new DoAction();
    Class clazz = action.getClass();
    //只能拿到这个类直接实现的所有接口
    Class[] interfaces = clazz.getInterfaces();
    ClassLoader classLoader = clazz.getClassLoader();

    Speak proxy = (Speak)Proxy.newProxyInstance(classLoader, interfaces, new Man(action));

    proxy.jump();

  }


}
