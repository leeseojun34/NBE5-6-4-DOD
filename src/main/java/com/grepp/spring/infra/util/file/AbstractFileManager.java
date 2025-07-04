package com.grepp.spring.infra.util.file;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractFileManager {

    public List<FileDto> upload(List<MultipartFile> files, String depth) throws IOException {
        List<FileDto> fileDtos = new ArrayList<>();
        
        if (files.isEmpty() || files.getFirst().isEmpty()) {
            return fileDtos;
        }
        
        String savePath = createSavePath(depth);
        
        for (MultipartFile file : files) {
            String originFileName = file.getOriginalFilename();
            String renameFileName = generateRenameFileName(originFileName);
            FileDto fileDto = new FileDto(originFileName, renameFileName, depth, savePath);
            fileDtos.add(fileDto);
            uploadFile(file, fileDto);
        }
        
        return fileDtos;
    }
    
    protected abstract void uploadFile(MultipartFile file, FileDto fileDto) throws IOException;
    
    protected String generateRenameFileName(String originFileName) {
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        return UUID.randomUUID() + ext;
    }
    
    protected String createSavePath(String depth) {
        LocalDate now = LocalDate.now();
        return depth + "/" +
                   now.getYear() + "/" +
                   now.getMonth() + "/" +
                   now.getDayOfMonth() + "/";
    }
    
}
