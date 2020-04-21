package demo.interceptor;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/27 22:21
 * @Version V1.0
 */
public class AppMain {

  public static void main(String[] args) throws Exception {
    Speak speak = new Monkey();
    MyInterceptor instance1 = (MyInterceptor)Class.forName("demo.interceptor.ExampleInterceptor").newInstance();
    MyInterceptor instance2 = (MyInterceptor)Class.forName("demo.interceptor.SecondIterceptor").newInstance();
    Speak plugin1 = (Speak)MyPlugin.plugin(speak, instance1);
    Speak plugin2 = (Speak)MyPlugin.plugin(plugin1, instance2);
    plugin2.talk();
  }
}
