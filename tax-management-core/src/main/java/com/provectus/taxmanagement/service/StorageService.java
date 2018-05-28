package com.provectus.taxmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by alexey on 22.04.17.
 */
public interface StorageService {
    File storeFile(MultipartFile file) throws IOException;
}
