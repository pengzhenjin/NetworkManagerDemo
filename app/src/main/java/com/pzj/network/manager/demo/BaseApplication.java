package com.pzj.network.manager.demo;

import android.app.Application;
import com.pzj.network.manager.networkmanager.NetworkManager;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 15:47
 * @Description:
 */
public class BaseApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    NetworkManager.getInstance().init(this);
  }
}
