package cn.qmulin.gomall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
@SpringBootTest
public class GomallThirdPartyApplicationTests {
    @Resource
    OSSClient ossClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        FileInputStream file = new FileInputStream("C:\\Users\\xys-colin\\Desktop\\senmusicPic\\bb.jpg");
        ossClient.putObject("qmulin","bb.jpg",file);
        ossClient.shutdown();
        System.out.println("上传完成");
    }

}
