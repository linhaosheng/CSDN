package com.example.administrator.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断当前手机是否联网
 */
public class NetUtil
{
    /**
     * 检查当前手机网络
     * @param context
     * @return
     */
	public static boolean checkNet(Context context)
	{

		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false)
		{
			return false;
		}
		return true;
	}

    /**
     * 检查是否是wifi连接
     * @param context
     * @return
     */
	public static boolean isWIFIConnected(Context context)
	{
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}

	public static boolean isMOBILEConnected(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}
}
