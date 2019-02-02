package com.itdan.shopmall.test;

import org.junit.Test;

/**
 * 字符串截取测试
 */
public class StringTest {

    @Test
    public void testSplit () throws Exception{

        String ids="19554,15644,416464,45445";
        String[] id=ids.split(",");
        for (String i:id){
            System.out.println(Long.valueOf(i));
        }
    }
}
