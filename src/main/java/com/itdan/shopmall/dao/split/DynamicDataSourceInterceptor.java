package com.itdan.shopmall.dao.split;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * 拦截器
 */
@Intercepts({
    @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class,RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static final  String REGEX=
            ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
    private static Logger logger=
            LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws  Throwable{
        //判断是否为事务事件
      boolean synchroniztionActive=TransactionSynchronizationManager.isActualTransactionActive();
        Object[]o=invocation.getArgs();//获取增删改查的参数
        MappedStatement ms= (MappedStatement) o[0];//MappedStatement对象可以判断是什么类型的操作
        String lookupKey=DynamicDataSourceHolder.DB_MASTER;//lookupKey用来决定用那台服务的（主还是从）
      if(synchroniztionActive!=true){
          //读方法
            if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //selectKey为自增id查询主键（SELECT_INSERT_ID（））方法,使用主库
                if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                  lookupKey=DynamicDataSourceHolder.DB_MASTER;
                }else {
                    BoundSql boundSql=ms.getSqlSource().getBoundSql(o[1]);//获取sql语句
                    //将sql语句中所有的字表符回车啊转换为空格
                    String sql=boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]"," ");
                     if(sql.matches(REGEX)){//判断其为增删改中的什么类型
                         lookupKey=DynamicDataSourceHolder.DB_MASTER;//主库来写
                     }else {
                         lookupKey=DynamicDataSourceHolder.DB_SLAVE;//从库来读
                     }
                }
            }
      }else {
          lookupKey=DynamicDataSourceHolder.DB_MASTER;
      }
        logger.debug("设置方法[{}]user[{}]Stratery,SqlCommonType[{}]"
                +ms.getId(),lookupKey,ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    @Override
    //在mybatis中Executor是用来做一切增删改查的操作，
    // 当我们拦截到该对象时，我们会把它包在Plugin中，如果不是则不做拦截，返回本体
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }else{
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
