package com.provectus.taxmanagement.service.impl;

import com.provectus.taxmanagement.service.StorageService;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 22.04.17.
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    private static final String STORAGE_PATH = "storage";
    private static final String FOLDER_PATTERN = "ddMMyyyy";
    private static final String IMPORTS_PATH = "imports";
    private final File storage = new File(STORAGE_PATH, IMPORTS_PATH);
    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Override
    public File storeFile(MultipartFile file) throws IOException {
        File f = createDailyFolder();
        byte[] bytes = file.getBytes();
        Path path = Paths.get(f.getAbsolutePath(), System.nanoTime() + file.getOriginalFilename());
        Files.write(path, bytes);
        return path.toFile();
    }

    @Override
    public List<File> storeFiles(MultipartFile[] files) throws IOException {
        List<File> f = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            File file = storeFile(multipartFile);
            f.add(file);
        }
        return f;
    }

    @Override
    public File saveWorkbook(Workbook wb, String fileName) {
        File f = createDailyFolder();
        File file = new File(f, System.nanoTime() + fileName);

        try (OutputStream fileOut = new FileOutputStream(file)) {
            wb.write(fileOut);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return file;
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
