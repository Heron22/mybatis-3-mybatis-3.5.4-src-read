package demo.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyInter {
  Class clazz();  //拦截的接口类型
  String method(); //拦截的方法名
  Class[] args(); //拦截的方法参数类型
}
