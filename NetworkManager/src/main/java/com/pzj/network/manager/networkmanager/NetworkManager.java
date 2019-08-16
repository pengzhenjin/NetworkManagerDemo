package com.pzj.network.manager.networkmanager;

import android.app.Application;
import android.content.IntentFilter;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 9:00
 * @Description: 网络管理器
 */
public class NetworkManager {
  private static volatile NetworkManager instance;
  private Application application;
  private NetworkStateReceiver receiver;

  public NetworkManager() {
    this.receiver = new NetworkStateReceiver();
  }

  public static NetworkManager getInstance() {
    if (instance == null) {
      synchronized (NetworkManager.class) {
        if (instance == null) {
          instance = new NetworkManager();
        }
      }
    }
    return instance;
  }

  /**
   * 初始化
   */
  public void init(Application application) {
    this.application = application;
    // 动态注册广播
    IntentFilter filter = new IntentFilter();
    filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
    this.application.registerReceiver(receiver, filter);
  }

  /**
   * 获取Application
   */
  public Application getApplication() {
    if (this.application == null) {
      throw new RuntimeException("application is null");
    }
    return this.application;
  }

  /**
   * 注册
   */
  public void register(Object object) {
    this.receiver.register(object);
  }
}
