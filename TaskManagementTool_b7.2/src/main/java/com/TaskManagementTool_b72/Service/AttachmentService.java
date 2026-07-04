package com.TaskManagementTool_b72.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagementTool_b72.Entity.Attachment;
import com.TaskManagementTool_b72.Repository.AttachmentRepository;
import com.cloudinary.Cloudinary;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    @Autowired
    private Cloudinary clodinary;



    public Attachment upload(Long issueId,MultipartFile file,String uploadedBy) {
        validateFile(file);
        try {

            Map<String,Object>uploadOption= new HashMap<>();

            uploadOption.put("resource_type", "auto");

            Map uploadResult= clodinary.uploader().upload(file.getBytes(), uploadOption);

            Attachment att = new Attachment();

            att.setIssueId(issueId);
            att.setFileName(file.getOriginalFilename());

            String contentType=file.getContentType();
            if(contentType==null) {
                throw new RuntimeException("Unable to determine file_contentType");
            }

            att.setFileContentType(contentType);

            att.setSizeBytes(file.getSize());
            att.setCloudinaryId(uploadResult.get("public_id").toString());
            att.setStoragePath(uploadResult.get("secure_url").toString());
            att.setUploadedBy(uploadedBy);

            return attachmentRepo.save(att);

        } catch (Exception e) {
            throw new RuntimeException("/file upload failed");
        }
    }

    public List<Attachment>getFileByIssueId(Long ssueId){

        return attachmentRepo.findByIssueId(ssueId);
    }

    public Attachment getFileById(Long id) {
        return attachmentRepo.findById(id).orElseThrow(()-> new RuntimeException("File not found"));
    }


    public void delete(Long id) {

        Attachment attach= getFileById(id);

        try {

            Map<String,Object>option= new HashMap<>();
            option.put("resource_type", "auto");

            clodinary.uploader().destroy(attach.getCloudinaryId(), option);
            attachmentRepo.delete(attach);

        } catch (Exception e) {
            throw new RuntimeException("delete failed",e);
        }
    }



    private void validateFile(MultipartFile file) {

        if(file.isEmpty()) {
            throw new RuntimeException("file can not be empty");
        }

        long MAX =5*1024*1024;

        if(file.getSize()>MAX) {
            throw new RuntimeException("Max file size is 5 mb");
        }

        List<String>allowed= Arrays.asList("image/png","image/jpeg","application/pdf","text/plain","text/doc");

        if(!allowed.contains(file.getContentType())) {
            throw new RuntimeException("Invalid file format");
        }
    }
}

