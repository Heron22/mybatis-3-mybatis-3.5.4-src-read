/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * @author Clinton Begin
 */
public class Plugin implements InvocationHandler {

  private final Object target;
  private final Interceptor interceptor;
  private final Map<Class<?>, Set<Method>> signatureMap;

  private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
    this.target = target;
    this.interceptor = interceptor;
    this.signatureMap = signatureMap;
  }

  //TODO target是需要代理的对象 ，interceptor是需要添加的拦截器
  public static Object wrap(Object target, Interceptor interceptor) {
    // TODO signatureMap ： 是拦截的接口类型 和 接口中需要拦截方法的集合构成的map映射
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    //TODO 获取到被代理对象中所有需要拦截的接口
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      //如果代理对象实现了需要拦截的接口，则将代理对象返回（代理对象持有target, interceptor, signatureMap）
      // 代理对象调用接口的中的任意方法，都会先执行 Plugin 类的invoke方法
      //proxy相当于实现了interfaces中的所有接口，所以展现出什么样的特性，是由强转决定的
       Object proxy = Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
      return proxy ;
    }
    //没有需要代理的接口，原样返回
    return target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      //获取方法声明的接口，从signatureMap中获取到需要拦截的类的所有方法
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
      //如果获取到了方法集合，并且调用的方法也是需要拦截的方法，则先调用拦截的方法
      if (methods != null && methods.contains(method)) {
        return interceptor.intercept(new Invocation(target, method, args));
      }
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }

  private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
    //获取到自定义插件上的 Intercepts 注解
    Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
    // issue #251
    if (interceptsAnnotation == null) {
      throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
    }
    //获取到Intercepts注解的属性值 Signature[] ，每一个 Signature就是一个需要拦截的接口类型里的一个方法
    Signature[] sigs = interceptsAnnotation.value();
    //封装需要拦截的类和 拦截类中的方法集合的映射： Excutor：[update,query]
    Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
    for (Signature sig : sigs) {
      //每一个拦截的类传建一个set集合
      Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
      try {
        //获取到拦截的方法存放在集合中
        Method method = sig.type().getMethod(sig.method(), sig.args());
        methods.add(method);
      } catch (NoSuchMethodException e) {
        throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
      }
    }
    return signatureMap;
  }

  //TODO type是需要拦截的对象   ，signatureMap ： 是拦截的接口类型 和 接口中需要拦截方法的集合构成的map映射
  private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
    Set<Class<?>> interfaces = new HashSet<>();
    while (type != null) {
      //循环获取需要拦截对象实现的所有接口
      for (Class<?> c : type.getInterfaces()) {
        //判断实现的所有接口中是否有需要拦截的接口，如果有需要拦截的接口，则添加到集合中，返回
        if (signatureMap.containsKey(c)) {
          interfaces.add(c);
        }
      }
      type = type.getSuperclass();
    }
    return interfaces.toArray(new Class<?>[interfaces.size()]);
  }

}
