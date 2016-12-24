package com.smartlott.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 12/24/16.
 */
@Service
public class UploadFileService {


    @Value("${image.store.tmp.folder}")
    private String tempImageStore;
}
