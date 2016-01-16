package com.example.administrator.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/11/13.
 */
public class DataUtil {
    /**
     * 通过传入url链接访问网络，获取网页的html数据
     *
     * @param urlstr
     * @return
     * @throws CommonExecption
     */
    public static String doGet(String urlstr, String uncode) throws CommonExecption {
        StringBuffer sb = new StringBuffer();
        try {
       /*     URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
         */

            HttpClient client = new HttpClient();
            GetMethod getMethod = new GetMethod(urlstr);


            getMethod.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            getMethod.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            getMethod.addRequestHeader("Host","www.csdn.net");
            getMethod.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:43.0) Gecko/20100101 Firefox/43.0");
            getMethod.addRequestHeader("Connection","keep-alive");
             int state= client.executeMethod(getMethod);
            if (state== 200) {
                InputStream is = getMethod.getResponseBodyAsStream();
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    sb.append(new String(buf, 0, len, uncode));
                }
                is.close();
            } else {
                throw new CommonExecption("访问网络失败");
            }
        } catch (Exception e) {
            throw new CommonExecption("访问网络失败");
        }
        return sb.toString();
    }
}
