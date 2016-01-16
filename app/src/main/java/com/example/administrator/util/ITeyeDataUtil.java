package com.example.administrator.util;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2015/12/26.
 */
public class ITeyeDataUtil {

    /**
     * 返回该链接地址的html数据
     *
     * @param urlStr
     * @return
     * @throws
     */
    public static String doGet1(String urlStr,int currentPage,int newType,int useAgentNum) throws CommonExecption
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            URL url = new URL(urlStr);
          HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            System.out.println("code-----" + conn.getResponseCode());
            //	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");

            /**
             * 更换代理
             */
          String []useAgent=new String[]{"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
                    "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
                    " Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/999"};

            switch(useAgentNum){
                case 14:
                    conn.setRequestProperty("User-Agent", useAgent[0]);
                    break;
                case 15:
                    conn.setRequestProperty("User-Agent", useAgent[1]);
                    break;
                case 16:
                    conn.setRequestProperty("User-Agent", useAgent[2]);
                    break;
                case 17:
                    conn.setRequestProperty("User-Agent", useAgent[3]);
                    break;
            }

            if (conn.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                InputStream is =conn.getInputStream();
                int len = 0;
                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1)
                {
                    sb.append(new String(buf, 0, len, "UTF-8"));
                }
                is.close();
            } else
            {
                throw new CommonExecption("访问网络失败！");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new CommonExecption("访问网络失败！");
        }

        return sb.toString();
    }

    public static String doGet(String urlStr) throws CommonExecption
    {
        StringBuffer sb = new StringBuffer();
        try
        {

            HttpClient client=new HttpClient();
            GetMethod getMethod=new GetMethod(urlStr);

            getMethod.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:43.0) Gecko/20100101 Firefox/43.0");
            getMethod.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            getMethod.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            getMethod.addRequestHeader("Host","www.iteye.com");
            getMethod.addRequestHeader("Connection","keep-alive");
            getMethod.addRequestHeader("Referer","http://www.iteye.com/news");

            client.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
            int state=client.executeMethod(getMethod);

            System.out.println("state2-----"+state);
            if (state==200)
            {
                InputStream is =getMethod.getResponseBodyAsStream();
                BufferedInputStream buff=new BufferedInputStream(is);
                BufferedReader reader=null;
                reader=new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
                String line = "";
                while ((line=reader.readLine())!=null)
                {
                    sb.append(line + "\n");
                }
                is.close();
            } else
            {
                throw new CommonExecption("访问网络失败");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new CommonExecption("访问网络失败");
        }

        return sb.toString();
    }
}
