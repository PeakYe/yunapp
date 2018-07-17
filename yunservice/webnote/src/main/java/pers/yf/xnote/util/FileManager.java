package pers.yf.xnote.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManager {
	//上传路径
	public String uploadPath="/";
	//禁止上传的文件
	public final String excludeFileName="^.(exe|sh|jsp|html|js)$";
	
	public String genNewFileName(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	//获取/随机路径
	public String getPath(String fileNamePrefix,String suffix){
		StringBuilder builder=new StringBuilder();
		builder.append(uploadPath).append("/");
		int i=1;
		for(char c:fileNamePrefix.toCharArray()){
			builder.append(c);
			if(i%3==0){
				builder.append("/");
				if(i>6){
					break;
				}
			}
			i++;
		}
		builder.append(fileNamePrefix).append(".").append(suffix);
		return builder.toString();
	}
	
	
	// fileName
	public String saveFile(MultipartFile file) throws IllegalStateException, IOException{
         if(file != null){  
             String fileName=file.getOriginalFilename();
             //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
             String suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());
             //
             if(Pattern.matches(excludeFileName, suffix)){
            	 return null;
             }
             String newFileName=genNewFileName();
             File localfile=createFile( suffix);
             file.transferTo(localfile);  
             return newFileName+suffix;
         }
         return null;
	}
	
	/**
	 * 
	 * @param suffix .txt
	 * @return
	 */
	public File createFile(String suffix){
		 String newFileName=genNewFileName();
         File localfile=new File(getPath(newFileName,suffix));
         while(localfile.exists()){
        	 localfile=new File(getPath(newFileName,suffix));
         }
         localfile.getParentFile().mkdirs();
        return localfile;
	}
	
	public List<String> saveFiles(HttpServletRequest request) throws IllegalStateException, IOException{
		List<String> fileNames=new LinkedList<>();
		 //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){  
            	 MultipartFile file = multiRequest.getFile(iter.next());
            	 String filename=saveFile(file);
            	 if(filename!=null){
            		 fileNames.add(filename);
            	 }
            }  
        } 
        return fileNames;
	}
	
	public File saveCodeImg(String imgData){
		try {
			String suffix=imgData.substring(11, imgData.indexOf(";"));
			String data=imgData.substring(imgData.indexOf("base64,")+7, imgData.length());
			byte[] bs=Base64.getDecoder().decode(data);
			File file= createFile(suffix);
			FileOutputStream os = new FileOutputStream(file);
			os.write(bs);
			os.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void readFile(String fileName,HttpServletResponse response) throws IOException{
		OutputStream outputStream = response.getOutputStream();
		String[] param=fileName.split("\\.");
		File file=new File(getPath(param[0], param[1]));
		if(file.exists()){
			@SuppressWarnings("resource")
			FileInputStream fileInputStream=new FileInputStream(file);
			byte[] bytes=new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
			outputStream.write(bytes);
			outputStream.flush();
		}else{
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
}
