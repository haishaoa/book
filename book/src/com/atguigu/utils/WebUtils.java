package com.atguigu.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author haishao
 * @create 2020-05-17 15:48
 * @discript :
 *
 * Dao层
 * Service层
 * Web层
 * 使用Map比使用req耦合度低
 */
public class WebUtils {
    public static <T> T copyParamToBean(Map value, T bean){
        try {
            System.out.println("注入之前:" + bean);
            BeanUtils.populate(bean,value);
            System.out.println("注入之后:" + bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static int paserInt(String intstr,int defaultvalue){
        try {
            return Integer.parseInt(intstr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultvalue;
    }
}




















