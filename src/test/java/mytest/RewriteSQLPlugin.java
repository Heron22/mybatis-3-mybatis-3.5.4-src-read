package mytest;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Collections;
import java.util.List;

/**
 * @Author HeRong
 * @Description TODO
 * @Date 2020/4/1 20:53
 * @Version V1.0
 */

//通过插件解析sql语句，将改写sql语句，维护 创建时间和更新时间
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})})
public class RewriteSQLPlugin implements Interceptor {
//  private final SimpleAppendUpdateTimeVisitor visitor = new SimpleAppendUpdateTimeVisitor();
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement statement = (MappedStatement)invocation.getArgs()[0];
    SqlCommandType sqlCommandType = statement.getSqlCommandType();

    //插入语句，维护创建时间和更新时间字段 , 更新语句维护更新时间字段
    if (sqlCommandType != SqlCommandType.INSERT && sqlCommandType != SqlCommandType.UPDATE){
      return invocation.proceed();
    }

    String sql = statement.getBoundSql(invocation.getArgs()[1]).getSql();
    List<SQLStatement> statementList = SQLUtils.parseStatements(sql,JdbcConstants.MYSQL);
    if (statementList != null && statementList.size() > 0){
      for (SQLStatement sm: statementList) {
        sm.getAttributes();
      }
    }

    return null;
  }
}
