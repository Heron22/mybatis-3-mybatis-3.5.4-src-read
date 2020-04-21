package demo.reflect;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/26 22:28
 * @Version V1.0
 */
//被代理类要实现接口
public class DoAction implements  Jump,Speak{

  public void work(){
    System.out.println("abstract work");
  }

  @Override
  public void jump() {
    System.out.println("Man jump");
  }

  @Override
  public void speak() {
    System.out.println("Man speak");
  }
}
