package com.pzj.network.manager.networkmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pzj.network.manager.networkmanager.annotation.Network;
import com.pzj.network.manager.networkmanager.bean.MethodInfo;
import com.pzj.network.manager.networkmanager.type.NetworkType;
import com.pzj.network.manager.networkmanager.utils.NetworkUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 16:50
 * @Description: 网络状态广播接收器
 */
public class NetworkStateReceiver extends BroadcastReceiver {
  private NetworkType networkType;

  // key：activity或fragment  value：该容器的所有订阅监听网络方法的集合
  private Map<Object, List<MethodInfo>> networkMap;

  public NetworkStateReceiver() {
    this.networkType = NetworkType.NONE;
    this.networkMap = new HashMap<>();
  }

  @Override public void onReceive(Context context, Intent intent) {
    if (intent == null || intent.getAction() == null) {
      Log.e(Constants.LOG_TAG, "广播接收异常......");
      return;
    }

    // 处理广播事件
    if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
      networkType = NetworkUtils.getNetworkType(); // 赋值网络类型
      if (NetworkUtils.isNetworkAvailable()) {
        Log.i(Constants.LOG_TAG, "网络连接成功，类型为：" + networkType);
      } else {
        Log.i(Constants.LOG_TAG, "网络连接失败");
      }
      post(networkType);
    }
  }

  /**
   * 网络匹配
   */
  private void post(NetworkType networkType) {
    if (networkMap.isEmpty()) {
      return;
    }
    Set<Object> objects = networkMap.keySet();
    for (Object obj : objects) {
      List<MethodInfo> methodInfos = networkMap.get(obj);
      if (methodInfos != null) {
        for (MethodInfo methodInfo : methodInfos) {
          // 参数匹配
          if (methodInfo.getParameterType().isAssignableFrom(networkType.getClass())) {
            switch (methodInfo.getNetworkType()) {
              case AUTO:
                invoke(methodInfo, obj, networkType);
                break;
              case WIFI:
                if (networkType == NetworkType.WIFI || networkType == NetworkType.NONE) {
                  invoke(methodInfo, obj, networkType);
                }
                break;
              case CMNET:
              case CMWAP:
                if (networkType == NetworkType.CMNET || networkType == NetworkType.CMWAP || networkType == NetworkType.NONE) {
                  invoke(methodInfo, obj, networkType);
                }
                break;
            }
          }
        }
      }
    }
  }

  /**
   * 执行方法
   */
  private void invoke(MethodInfo methodInfo, Object obj, NetworkType networkType) {
    try {
      methodInfo.getMethod().invoke(obj, networkType);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /**
   * 注册
   */
  public void register(Object object) {
    List<MethodInfo> methodList = networkMap.get(object);
    if (methodList == null) {
      methodList = findAnnotationMethod(object); // 订阅方法的收集
      networkMap.put(object, methodList);
    }
  }

  /**
   * 找到注解方法
   */
  private List<MethodInfo> findAnnotationMethod(Object object) {
    List<MethodInfo> methodList = new ArrayList<>();
    Class<?> clazz = object.getClass();
    Method[] methods = clazz.getMethods();
    // 订阅方法的收集
    for (Method method : methods) {
      Network networkAnnotation = method.getAnnotation(Network.class);
      if (networkAnnotation == null) {
        continue;
      }

      // 获取方法的返回值，校验一
      //method.getGenericReturnType();

      // 获取方法的参数，校验二
      Class<?>[] parameterTypes = method.getParameterTypes();
      if (parameterTypes.length != 1) {
        throw new RuntimeException(method.getName() + "方法的参数有且只有一个");
      }

      MethodInfo methodInfo = new MethodInfo(parameterTypes[0], networkAnnotation.networkType(), method);
      methodList.add(methodInfo);
    }
    return methodList;
  }
}
