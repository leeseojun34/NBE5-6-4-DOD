package com.grepp.spring.infra.util.file;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.grepp.spring.infra.error.exceptions.CommonException;
import com.grepp.spring.infra.response.ResponseCode;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class GoogleStorageManager extends AbstractFileManager{
    
    @Value("${google.cloud.storage.bucket}")
    private String bucket;
    private final String storageBaseUrl = "https://storage.googleapis.com/";
    
    @Override
    protected void uploadFile(MultipartFile file, FileDto fileDto) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        
        if(file.getOriginalFilename() == null){
            throw new CommonException(ResponseCode.INVALID_FILENAME);
        }
        
        String renameFilename = fileDto.renameFileName();
        BlobId blobId = BlobId.of(bucket, fileDto.depth() + "/" + renameFilename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
    }
    
    @Override
    protected String createSavePath(String depth) {
        return storageBaseUrl + bucket + "/" + depth + "/";
    }
}
