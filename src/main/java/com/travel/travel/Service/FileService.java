package com.travel.travel.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid=UUID.randomUUID();

        String extension = originalFileName.substring(originalFileName
                .lastIndexOf("."));

        String saveFileName=uuid.toString()+extension;
        String fileUploadFullUrl=uploadPath+"/"+saveFileName;
        FileOutputStream fos=new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return saveFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            boolean deleted = deleteFile.delete();
            if (deleted) {
                log.info("파일을 삭제하였습니다.");
            } else {
                log.warning("파일 삭제에 실패했습니다.");
                throw new Exception("파일 삭제에 실패했습니다: " + filePath);
            }
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }


}