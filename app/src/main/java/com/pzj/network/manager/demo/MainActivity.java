package com.pzj.network.manager.demo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.pzj.network.manager.networkmanager.NetworkManager;
import com.pzj.network.manager.networkmanager.annotation.Network;
import com.pzj.network.manager.networkmanager.type.NetworkType;

public class MainActivity extends AppCompatActivity {

  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.tv);
    NetworkManager.getInstance().register(this);
  }

  @Network(networkType = NetworkType.AUTO)
  public void network(NetworkType networkType) {
    switch (networkType) {
      case WIFI:
      case CMNET:
      case CMWAP:
        textView.setText("当前网络类型是：" + networkType.name());
        break;
      case NONE:
        textView.setText("没有网络");
        break;
    }
  }
}
