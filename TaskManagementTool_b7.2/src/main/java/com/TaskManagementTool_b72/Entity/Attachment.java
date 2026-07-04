package com.TaskManagementTool_b72.Entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="attachment")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Attachment {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long issueId;

    private String fileName;
    private String fileContentType;
    private Long sizeBytes;

    @Column(length=2000)
    private String storagePath;

    private String uploadedBy;
    private LocalDateTime createdAt= LocalDateTime.now();
    private String cloudinaryId;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIssueId() {
        return issueId;
    }
    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    public Long getSizeBytes() {
        return sizeBytes;
    }
    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }
    public String getStoragePath() {
        return storagePath;
    }
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
    public String getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getCloudinaryId() {
        return cloudinaryId;
    }
    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }
}

