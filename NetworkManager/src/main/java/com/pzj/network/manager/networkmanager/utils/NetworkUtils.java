package com.pzj.network.manager.networkmanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.pzj.network.manager.networkmanager.NetworkManager;
import com.pzj.network.manager.networkmanager.type.NetworkType;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 15:34
 * @Description: 网络工具类
 */
public class NetworkUtils {

  /**
   * 网络是否可用
   */
  @SuppressLint("MissingPermission")
  public static boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) NetworkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager == null) {
      return false;
    }
    NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
    if (infos != null) {
      for (NetworkInfo anInfo : infos) {
        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 获取当前网络类型：-1：没有网络；1：wifi网络；2：wap网络；3：net网络
   */
  @SuppressLint("MissingPermission")
  public static NetworkType getNetworkType() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) NetworkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager == null) {
      return NetworkType.NONE;
    }
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo == null) {
      return NetworkType.NONE;
    }
    int type = networkInfo.getType();
    if (type == ConnectivityManager.TYPE_MOBILE) {
      if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
        return NetworkType.CMNET;
      } else {
        return NetworkType.CMWAP;
      }
    } else if (type == ConnectivityManager.TYPE_WIFI) {
      return NetworkType.WIFI;
    }
    return NetworkType.NONE;
  }
}
