package mytest;

import java.util.Date;

/**
 * @Author HeRong
 * @Description 数据库实体类
 * @Date 2020/2/20 17:47
 * @Version V1.0
 */
public class Student {

  private Integer id;

  private String username;

  private String address;

  private Integer age;

  private Date birthday;

  public Student() {
  }

  public Student(String username, String address, Integer age, Date birthday) {
    this.username = username;
    this.address = address;
    this.age = age;
    this.birthday = birthday;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return "Student{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", address='" + address + '\'' +
      ", age=" + age +
      ", birthday=" + birthday +
      '}';
  }
}
