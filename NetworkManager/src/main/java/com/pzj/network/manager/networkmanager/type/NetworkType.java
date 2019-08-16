package com.pzj.network.manager.networkmanager.type;

/**
 * @Author: PengZhenjin
 * @Date: 2019/8/16 9:02
 * @Description: 网络类型
 */
public enum NetworkType {
  // 有网络，包括：wifi、gprs
  AUTO,

  // wifi网络
  WIFI,

  // 主要用于pc、笔记本电脑、pad上网设备
  CMNET,

  // 手机上网
  CMWAP,

  // 没有网络
  NONE;
}
