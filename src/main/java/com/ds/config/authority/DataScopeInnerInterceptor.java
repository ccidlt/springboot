package com.ds.config.authority;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.SneakyThrows;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 *数据范围权限拦截器，配合@DataScope注解使用
 */
public class DataScopeInnerInterceptor implements InnerInterceptor {

    @Autowired
    private UserAuthInterface authInterface;


    @SneakyThrows
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {

        //获取执行方法的位置
        String namespace = ms.getId();
        //获取mapper名称
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        //获取方法名
        String methodName = namespace.substring(namespace.lastIndexOf(".") + 1);
        //获取当前mapper 的方法
        Method[] methods = Class.forName(className).getMethods();
        for (Method m : methods) {
            if (Objects.equals(m.getName(), methodName)) {
                //获取注解  来判断是不是要处理sql
                if(!m.isAnnotationPresent(DataScope.class)){
                    continue;
                }
                DataScope dataScope = m.getAnnotation(DataScope.class);
                if (Objects.isNull(dataScope) || Objects.equals("", dataScope.authKey())) {
                    //没有数据权限相关枚举，直接跳过
                    continue;
                }
                UserAuth auth = authInterface.getUserAuth(dataScope.authKey());
                //去除特殊字符
                String originalSql = boundSql.getSql().replaceAll("\r|\n|`", "");
                if (!Objects.isNull(auth)) {
                    //根据用户权限拼接sql
                    String newSql = getInExpressionByAuth(auth, dataScope, originalSql);
                    //通过反射修改sql语句
                    Field field = boundSql.getClass().getDeclaredField("sql");
                    field.setAccessible(true);
                    field.set(boundSql, newSql);
                }
            }
        }
    }


    /**
     * 根据权限，拼接SQL
     */
    public static String getInExpressionByAuth(UserAuth auth, DataScope dataScope, String sql) {

        //如果是admin账户，直接返回null，不修改原sql
        if (!Objects.isNull(auth.getAll()) && auth.getAll()) {
            return null;
        }
        //如果是无权限，拼接1=2
        if (!Objects.isNull(auth.getNone()) && auth.getNone()) {
            return addWhereCondition(sql, "1=2");
        }
        return addWhereCondition(sql, getCondition(dataScope.comTableName(), dataScope.comFieldName(), auth.getIds()));
    }

    /**
     * 生成where 条件字符串
     *
     * @param tableName 表名
     * @param fieldName 字段名
     * @param ids       值
     * @return
     */
    private static String getCondition(String tableName, String fieldName, List<Integer> ids) {
        return tableName + "." + fieldName + " in (" + StringUtils.join(ids, ",") + ")";
    }

    /**
     * 在原有的sql中增加新的where条件
     *
     * @param sql       原sql
     * @param condition 新的and条件
     * @return 新的sql
     */
    public static String addWhereCondition(String sql, String condition) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            final Expression expression = plainSelect.getWhere();
            final Expression envCondition = CCJSqlParserUtil.parseCondExpression(condition);
            if (Objects.isNull(expression)) {
                plainSelect.setWhere(envCondition);
            } else {
                AndExpression andExpression = new AndExpression(expression, envCondition);
                plainSelect.setWhere(andExpression);
            }
            return plainSelect.toString();
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
}
