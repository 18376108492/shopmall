package com.itdan.shopmall.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DynamicDataSourceHolder {
    //获取日志
    private static Logger logger=
            LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //线程安全模式
    private static  ThreadLocal<String> contextHolder=new ThreadLocal<>();

    //模式
    public static  final String DB_MASTER="master";
    public static  final String DB_SLAVE="slave";

    /**
     * 获取数据源
     * @return
     */
    public static String getDbType(){
          String db=contextHolder.get();
          if(db==null){
              //如果db为空，将db设为master因为master支持写也支持读
                  db=DB_MASTER;
          }
          return  db;
    }

    /**
     *设置数据源
     * @param str
     */
    public static  void setDbType(String str){
        logger.debug("所使用的数据源为："+str);
        contextHolder.set(str);
    }

    /**
     * 清洗数据源
     */
    public  static  void clearDbType(){
       contextHolder.remove();
    }
}
