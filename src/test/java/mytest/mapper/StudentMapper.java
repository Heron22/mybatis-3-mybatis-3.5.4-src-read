package mytest.mapper;

import mytest.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/2/26 21:04
 * @Version V1.0
 */
@Mapper
public interface StudentMapper {

    void save(Student student);

    Student getById(@Param("id") Integer id);

    Student findByUsernameAndAge(@Param("username")String username ,@Param("age")Integer age );

    @Insert({"INSERT INTO student (`username`,`address`,`age`, `birthday`) VALUES (#{username},#{address},#{age},#{birthday})"})
    void annoSave(Student student);
}
