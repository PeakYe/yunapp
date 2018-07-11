package test.file;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
public class UploadController {


    @RequestMapping("/test/upload")
    public void upload(MultipartFile file) throws IOException {

        file.transferTo(new File("D:/temp/"+ UUID.randomUUID()));
        System.out.println("文件上传成功："+file.getName());
    }
}
