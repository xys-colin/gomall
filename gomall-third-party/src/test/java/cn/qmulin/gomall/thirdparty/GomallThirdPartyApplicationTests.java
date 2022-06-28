package cn.qmulin.gomall.thirdparty;

import cn.qmulin.gomall.thirdparty.component.SmsComponent;
import cn.qmulin.gomall.thirdparty.utils.HttpUtils;
import com.aliyun.oss.OSSClient;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class GomallThirdPartyApplicationTests {
    @Resource
    OSSClient ossClient;
    @Autowired
    SmsComponent smsComponent;

    @Test
    public void contextLoads() throws FileNotFoundException {
        FileInputStream file = new FileInputStream("C:\\Users\\xys-colin\\Desktop\\senmusicPic\\bb.jpg");
        ossClient.putObject("qmulin", "bb.jpg", file);
        ossClient.shutdown();
        System.out.println("上传完成");
    }

    @Test
    public void sendMessage() {
        smsComponent.sendSmsCode("13875909768","12345");
    }

}
