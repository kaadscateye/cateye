package com.kds.system;

import java.util.List;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
    private static final String TAG = "WifiAdmin";
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    private ScanResult mScanResult;
    private StringBuffer mStringBuffer = new StringBuffer();
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    WifiLock mWifiLock;
    private Context context =null;
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    // 构造器
    public WifiAdmin(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
        this.context=context;
    }

    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public int getWifiState() {
        return mWifiManager.getWifiState();
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);
    }

    public void startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
    }

    /**
     * 得到扫描结果
     */
    public List<ScanResult> getScanResultList() {
        return mWifiManager.getScanResults();
    }
    public String getScanResult() {
    	
        mWifiList = mWifiManager.getScanResults();
        if (mWifiList != null) {
            for (int i = 0; i < mWifiList.size(); i++) {
                mScanResult = mWifiList.get(i);
                mStringBuffer = mStringBuffer.append("NO.").append(i + 1)
                        .append(" :").append(mScanResult.SSID).append("->")
                        .append(mScanResult.BSSID).append("->")
                        .append(mScanResult.capabilities).append("->")
                        .append(mScanResult.frequency).append("->")
                        .append(mScanResult.level).append("->")
                        .append(mScanResult.describeContents()).append("\n\n");
            }
        }
        return mStringBuffer.toString();
    }
    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }
    public String getSsid() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }
    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        if (wcg == null) {
            return;
        }
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        mWifiManager.setWifiEnabled(true);
        Log.e(TAG, "addNetwork: addNetwork result =["+wcgID +"] \t enableNetwork result=[" + b + "]");
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    // 然后是一个实际应用方法，只验证过没有密码的情况：

    public WifiConfiguration CreateWifiInfo(String SSID, String Password,
                                            int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();


        WifiConfiguration tempConfig = this.isExist(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) // WIFICIPHER_NOPASS
        {
//            config.wepKeys[0] = "";
//            config.SSID = "\"" + mScanResult.SSID + "\"";
//            config.BSSID = "\"" + mScanResult.BSSID + "\"";
//            config.status = WifiConfiguration.Status.ENABLED;
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            config.wepTxKeyIndex = 0;
//            config.hiddenSSID = false;
//            config.priority = 100000;

            WifiConfiguration wc = new WifiConfiguration();
            wc.SSID = "\"" + mScanResult.SSID + "\"";
            wc.BSSID =  "\"" + mScanResult.BSSID + "\"";

            //No password. it should be an open network
            wc.status = WifiConfiguration.Status.ENABLED;
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wc.priority = 100000;
            wc.hiddenSSID = false;
            int netId = mWifiManager.addNetwork(wc);

            if (netId == -1)
            {
                Log.e(TAG,"Error connecting to network.");
                return wc;
            }
            mWifiManager.enableNetwork(netId, true);
            mWifiManager.setWifiEnabled(true);

        }

        config.SSID = "\"" + SSID + "\"";
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private int getType(String SSID, String Password) {
        int type = 0;
        mWifiList = mWifiManager.getScanResults();
        if (mWifiList != null) {
            for (int i = 0; i < mWifiList.size(); i++) {
                mScanResult = mWifiList.get(i);
                if (mScanResult.SSID.equals(SSID)) {
                    if (mScanResult.capabilities.contains("OPEN")) {
                        type = 1;
                    } else if (mScanResult.capabilities.contains("WEP")) {
                        type = 2;
                    } else if (mScanResult.capabilities.contains("WPA")
                            || mScanResult.capabilities.contains("WPA2")) {
                        type = 3;
                    } else {
                        type = 1;
                    }
                    Log.e("SSID : " + SSID + "  getType", "加密方式:" + mScanResult.capabilities + "type" + type);
                    break;
                }
            }
        }
        return type;
    }

    public WifiConfiguration CreateWifiInfo_new(String SSID, String Password) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.isExist(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }
        int Type = getType(SSID, Password);
        if (Type == 1) // WIFICIPHER_NOPASS
        {
//            config.SSID = "\"" + mScanResult.SSID + "\"";
//            config.BSSID = "\"" + mScanResult.BSSID + "\"";
//            config.status = WifiConfiguration.Status.ENABLED;
//            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            config.wepTxKeyIndex = 0;

//            WifiConfiguration wc = new WifiConfiguration();
//            wc.SSID = "\"" + mScanResult.SSID + "\"";
//            wc.BSSID =  "\"" + mScanResult.BSSID + "\"";
//
//            //No password. it should be an open network
//            wc.status = WifiConfiguration.Status.ENABLED;
//            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            wc.priority = 100000;
//            wc.hiddenSSID = false;
//            int netId = mWifiManager.addNetwork(wc);
//
//            if (netId == -1)
//            {
//                Log.e(TAG,"Error connecting to network.");
//                return null;
//            }
//            mWifiManager.enableNetwork(netId, true);
//            mWifiManager.setWifiEnabled(true);
        }
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration isExist(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager
                .getConfiguredNetworks();

        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        Log.e(TAG, "mWifiManager.getConfiguredNetworks(): " + SSID + "not exist");
        return null;
    }

}