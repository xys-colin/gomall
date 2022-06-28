package cn.qmulin.gomall.thirdparty.component;

import cn.qmulin.gomall.thirdparty.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/28 21:55
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.alicloud.sms")
public class SmsComponent {
    private String host;
    private String path;
    private String appcode;
    private String smsSignId;
    private String templateId;

    public void sendSmsCode(String phone, String code) {
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
