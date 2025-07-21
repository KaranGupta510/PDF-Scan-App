package org.pdf.scan.controller;

import org.pdf.scan.model.MetadataRecord;
import org.pdf.scan.model.MetadataResponse;
import org.pdf.scan.service.PdfScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@RestController
public class PdfScanController {

  @Autowired
  private PdfScanService pdfScanService;

  @PostMapping("/scan")
  public ResponseEntity<?> scanPdf(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
    if (!pdfScanService.isPdfFile(file.getContentType())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("{\"error\": \"Invalid file type, must be PDF\"}");
    }

    byte[] bytes = file.getBytes();
    String sha256 = pdfScanService.calculateSha256(bytes);

    pdfScanService.startScan(bytes, sha256);
    return ResponseEntity.ok("{\"sha256\": \"" + sha256 + "\"}");
  }

  @GetMapping("/lookup")
  public ResponseEntity<?> lookup(@RequestParam("sha256") String sha256) {
    MetadataRecord record = pdfScanService.lookup(sha256);
    if (record == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("{\"error\": \"Record not found\"}");
    }

    if (!record.isProcessingComplete()) {
      return ResponseEntity.ok("{\"sha256\": \"" + sha256 + "\", \"status\": \"Processing\"}");
    }

    return ResponseEntity.ok(new MetadataResponse(record));
  }


}
