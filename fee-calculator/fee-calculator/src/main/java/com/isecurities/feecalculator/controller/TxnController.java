package com.isecurities.feecalculator.controller;

import static com.isecurities.feecalculator.Utils.ALLOWED_FILE_TYPES;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isecurities.feecalculator.bean.TxnInfoStats;
import com.isecurities.feecalculator.service.TxnService;

@RestController
@RequestMapping("/api/txn")
public class TxnController {

    @Value("${input.dir}")
    private String UPLOADED_FOLDER;

    @Autowired
    private TxnService txnService;

    @PostMapping("/load")
    public String loadTxnSource(@RequestParam("files") MultipartFile[] uploadfiles) {
        if (uploadfiles.length < 1) {
            return "Please select a file";
        }
        for ( MultipartFile uploadedFile : uploadfiles) {
            if (uploadedFile.isEmpty()) {
                continue;
            }
            load(uploadedFile);
        }
        return "success";
    }

    private void load(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String[] arr = fileName.split("\\.");
            String fileType = arr[arr.length-1].toLowerCase();
            if (!ALLOWED_FILE_TYPES.contains(fileType)) {
                return;
            }
            byte[] bytes = file.getBytes();
            String uri = UPLOADED_FOLDER + fileName;
            File uploadedFile = new File(UPLOADED_FOLDER);
            boolean isCreated = uploadedFile.mkdirs();
            Path path = Paths.get(uri);
            Files.write(path, bytes);
            txnService.parseAndSave(path, fileType);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/summaryReport")
    public List<TxnInfoStats> getSummaryReport() {
        return txnService.getTxnSummary();
    }
}
