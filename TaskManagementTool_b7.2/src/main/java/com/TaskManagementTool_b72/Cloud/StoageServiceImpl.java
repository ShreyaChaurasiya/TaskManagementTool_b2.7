package com.TaskManagementTool_b72.Cloud;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StoageServiceImpl {

    private final Path baseDirs;

    public StoageServiceImpl(Environment env) {

        String dir = env.getProperty(
                "attachments.storage.local.base_dir",
                "uploads");

        this.baseDirs = Paths.get(dir).toAbsolutePath();

        try {
            Files.createDirectories(baseDirs);
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String store(MultipartFile file, String folder) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String ext = "";
        int i = fileName.lastIndexOf(".");

        if (i >= 0) {
            ext = fileName.substring(i);
        }

        String key = folder + "/" + UUID.randomUUID() + ext;

        Path target = baseDirs.resolve(key);

        try {

            Files.createDirectories(target.getParent());

            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING);

            return target.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public byte[] read(String storagePath) {

        try {
            return Files.readAllBytes(Paths.get(storagePath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }
}

