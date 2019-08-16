package com.pzj.network.manager.networkmanager.bean;

import com.pzj.network.manager.networkmanager.type.NetworkType;
import java.lang.reflect.Method;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 16:58
 * @Description: 符合要求的网络监听注解方法信息（javabean对象）
 */
public class MethodInfo {
  // 参数类型：NetworkType networkType
  private Class<?> parameterType;

  // 网络类型：networkType = NetworkType.AUTO
  private NetworkType networkType;

  // 需要自行的方法：network();
  private Method method;

  public MethodInfo(Class<?> parameterType, NetworkType networkType, Method method) {
    this.parameterType = parameterType;
    this.networkType = networkType;
    this.method = method;
  }

  public Class<?> getParameterType() {
    return parameterType;
  }

  public void setParameterType(Class<?> parameterType) {
    this.parameterType = parameterType;
  }

  public NetworkType getNetworkType() {
    return networkType;
  }

  public void setNetworkType(NetworkType networkType) {
    this.networkType = networkType;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
