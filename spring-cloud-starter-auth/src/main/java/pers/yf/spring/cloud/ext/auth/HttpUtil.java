package pers.yf.spring.cloud.ext.auth;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtil {

    /**
     * 以post的形式向url提交数据
     *
     * @param url
     * @param param 参数
     * @return
     */
    public static String post(String url, Map<String, String> param) {
        HttpPost httppost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).
                setConnectionRequestTimeout(10000).setSocketTimeout(50000).build();
        httppost.setConfig(requestConfig);
        String responseString = null;
        try {
            // 创建名/值组列表
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entries = param.entrySet();
            for (Entry<String, String> entry : entries) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, "gbk");
            uefEntity.setContentType("application/x-www-form-urlencoded; charset=gbk");
            uefEntity.setContentEncoding("gbk");
            httppost.setEntity(uefEntity);
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httppost);
            //获取请求的状态值
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                BufferedInputStream bis = new BufferedInputStream(
                        response.getEntity().getContent());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte, 0, strByte.length, "gbk");
                bos.close();
                bis.close();
            }
            return responseString;
        } catch (Exception e) {
            httppost.releaseConnection();
            throw new RuntimeException(e);
        }

    }
}
