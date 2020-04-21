package demo.interceptor;

import java.lang.reflect.Method;
import java.util.Properties;

/**
* @Desc  拦截器接口：
 *      拦截功能(处理拦截的方法)，
 *      拦截对象（获取拦截对象）
 *      拦截可能需要参数（获取参数的方法）
* @Author HeRong
* @Date   2020/3/27
* @return
*/
public interface MyInterceptor {
  //默认的设置用户定义的变量，可以不实现，不用传递变量
  default void setPropertis(Properties properties){ }
  //进行拦截的方法，需要实现接口，定义拦截的逻辑，
  Object doInterceptor(Object proxy, Method method, Object[] args)throws Throwable;
}
