package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by alexey on 22.04.17.
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    private static final String STORAGE_PATH = "storage";
    private static final String FOLDER_PATTERN = "ddMMyyyy";
    private static final String IMPORTS_PATH = "imports";
    private final File storage = new File(STORAGE_PATH, IMPORTS_PATH);

    @Override
    public File storeFile(MultipartFile file) throws IOException {
        File f = createDailyFolder();
        byte[] bytes = file.getBytes();
        Path path = Paths.get(f.getAbsolutePath(), System.nanoTime() + file.getOriginalFilename());
        Files.write(path, bytes);
        return path.toFile();
    }

    private File createDailyFolder() {
        String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FOLDER_PATTERN));
        File todaysFolder = new File(todaysDate);
        if (!todaysFolder.exists()) {
            todaysFolder.mkdir();
        }
        return todaysFolder;
    }

    @PostConstruct
    public void init() {
        if (!storage.exists()) {
            storage.mkdirs();
        }
    }
}
