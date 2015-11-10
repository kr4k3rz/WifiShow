package com.example.kr4k3rz.wifishow;

/**
 * Created by kr4k3rz on 11/5/15.
 */
public class Networks {
    String ssid;
    String psk;
    String key_mgmt;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getKey_mgmt() {
        return key_mgmt;
    }

    public void setKey_mgmt(String key_mgmt) {
        this.key_mgmt = key_mgmt;
    }
}
