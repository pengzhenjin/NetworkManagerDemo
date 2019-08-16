package com.pzj.network.manager.networkmanager.annotation;

import com.pzj.network.manager.networkmanager.type.NetworkType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 16:44
 * @Description: 网络注解
 */
@Target(ElementType.METHOD) // 表示这个注解作用在方法之上
@Retention(RetentionPolicy.RUNTIME) // 表示jvm在运行时，通过反射获取注解的值
public @interface Network {
  NetworkType networkType() default NetworkType.AUTO;
}
