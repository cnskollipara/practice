package com.isecurities.feecalculator.controller;

import com.isecurities.feecalculator.bean.TxnInfoStats;
import com.isecurities.feecalculator.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.isecurities.feecalculator.Utils.ALLOWED_FILE_TYPES;
import static com.isecurities.feecalculator.Utils.CSV;

@RestController
@RequestMapping("/api/txn")
public class TxnController {

    @Value("${dir.input}")
    private String UPLOADED_FOLDER;

    @Autowired
    private TxnService txnService;

    @PostMapping("/read")
    public String readTxnInfo(@RequestParam("files") MultipartFile[] uploadfiles) {
        System.out.printf("UPLOADED_FOLDER : " + UPLOADED_FOLDER);
        if (uploadfiles.length < 1) {
            return "Please select a file";
        }
        for ( MultipartFile uploadedFile : uploadfiles) {
            if (uploadedFile.isEmpty()) {
                continue; //next pls
            }
            readAndLoad(uploadedFile);
        }
        return "success";
    }

    private void readAndLoad(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String[] arr = fileName.split("\\.");
            String fileType = arr[arr.length-1].toLowerCase();
            if (!ALLOWED_FILE_TYPES.contains(fileType)) {
                return;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
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
