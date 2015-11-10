package com.example.kr4k3rz.wifishow;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.spazedog.lib.rootfw4.utils.io.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String wifi_info = "";
    ArrayList<Networks> array = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LinearLayoutManager layoutManager;
    boolean doubleBackToExitpressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        read_WPA_supplicant(); //get root access reads wpa_supplicant.conf file
        array = readWifi(wifi_info); //reads information of ssid,psk & key_mgmt


        adapter = new MyAdapter(array);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitpressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitpressedOnce = true;
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitpressedOnce = false;
            }
        }, 2000);

    }

    private void read_WPA_supplicant() {
        try {
            FileReader reader = new FileReader("/data/misc/wifi/wpa_supplicant.conf");  //reads wpa_supplicant.conf file
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while (null != (line = buffer.readLine())) {
                wifi_info += line + "\t";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Networks> readWifi(String wifi_info) {
        ArrayList<Networks> networks = new ArrayList<>();
        Pattern p = Pattern.compile("\\{([^{}]*)\\}"); //reads between {}
        Matcher m = p.matcher(wifi_info);
        while (m.find()) {
            Networks network = new Networks();
            if (!m.group(1).contains("psk"))
                network.setPsk("\" \"");


            String[] f = m.group(1).split("\t");
            for (int i = 0; i < f.length; i++) {

                Log.i("info", f[i] + "   " + i);
                if (f[i].contains("ssid")) {
                    String[] ssid = f[i].split("=");
                    network.setSsid(ssid[1]);

                }

                if (f[i].contains("key_mgmt")) {
                    String[] key_mgmt = f[i].split("=");
                    network.setKey_mgmt(key_mgmt[1]);

                }
                if (f[i].contains("psk")) {
                    String[] psk = f[i].split("=");
                    network.setPsk(psk[1]);
                }

            }


            networks.add(network);
            Log.i("info", "-----------------------");
            Log.i("result", m.group(1));
            Log.i("result", "-----------------------------------------");

        }

        for (int i = 0; i < networks.size(); i++) {
            Log.i("good", networks.get(i).getSsid());

            Log.i("good", networks.get(i).getPsk());
            //  Log.i("good", "----------------------------");
            Log.i("good", networks.get(i).getKey_mgmt());
            Log.i("good", "----------------------------");
        }

        return networks;
    }

}
