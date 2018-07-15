package pers.yf.xnote.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yf.spring.cloud.ext.auth.UserDetail;
import pers.yf.xnote.util.FileManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("img")
public class ImgFileService {
    @Value("${file.upload.path}")
    private String uploadPath;

    FileManager fileManager=new FileManager();

    @RequestMapping("/code/upload")
    public String uploadImg(String code, UserDetail detail){
        fileManager.setUploadPath(uploadPath);
        File file =fileManager.saveCodeImg(code);
        return file.getName();
    }

    @RequestMapping("{fileName}.{suffix}")
    public void readImg(@PathVariable("fileName") String fileName, @PathVariable("suffix") String suffix,
                        HttpServletResponse response) throws IOException {
        if(this.fileManager==null){
            fileManager=new FileManager();
            fileManager.setUploadPath(uploadPath);
        }
//		response.setContentType("image/*");
        fileManager.readFile(fileName+"."+suffix, response);
    }
}
