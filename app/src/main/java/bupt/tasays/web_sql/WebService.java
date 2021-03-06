package bupt.tasays.web_sql;

/**
 * Created by root on 18-3-10.
 */
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

public class WebService {
    // IP地址
    private static String IP = "39.107.121.30:8080";
    private static boolean tenPassed=false;
    //把TOMCATURL改为你的服务器地址

    /**
     * 通过Get方式获取HTTP服务器数据
     *
     * @return
     */
    public static String executeHttpGet(String url,String account, String password) {
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/Server/";
            path = path + url + "?account=" + account + "&password=" + password;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000); // 设置超时时间
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }

    public static String executeGetPersonalInfo(String url,String account){
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/Server/";
            path = path + url + "?account=" + account;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000); // 设置超时时间
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }

    public static String executePostPersonalInfo(String url,String account,String infoString){
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/Server/";
            path = path + url + "?" + infoString;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000); // 设置超时时间
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }

    public static String executeRegister(String url,String account,String password,String phonenum){
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/Server/";
            path = path + url + "?account=" + account+
                    "&password="+password+
                    "&phonenum="+phonenum;

            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000); // 设置超时时间
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }

    public static String executeGetIDs(String content,String type) {
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path = "http://" + IP + "/Server/TestLet?content="+URLEncoder.encode(content,"UTF-8")+
                    "&type="+ type;
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(30000); // 设置超时时间
            conn.setReadTimeout(30000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//                    Thread.sleep(10000);}
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                    finally {
//                        tenPassed=true;
//                    }
//                }
//            }).start();
//
//            while(!tenPassed);
            if (conn.getResponseCode()== 200) {
                is = conn.getInputStream();
                String buffer;
                buffer=parseInfo(is);
                Log.w(TAG, "executeGetIDs: "+buffer );
                return buffer;
            }
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}