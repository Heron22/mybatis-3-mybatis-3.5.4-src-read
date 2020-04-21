package demo.interceptor;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/3/27 22:19
 * @Version V1.0
 */
public class Monkey implements Speak {

  public void speak(){
    System.out.printf("Monkey speak");
  }

  public void talk(){
    System.out.printf("Monkey talk");
  }
}
