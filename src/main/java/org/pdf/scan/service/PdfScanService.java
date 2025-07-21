package org.pdf.scan.service;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.pdf.scan.model.MetadataRecord;
import org.pdf.scan.storage.MetadataStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
public class PdfScanService {

  @Autowired
  private MetadataStore metadataStore;

  public boolean isPdfFile(String contentType) {
    return contentType != null && contentType.equalsIgnoreCase("application/pdf");
  }

  public String calculateSha256(byte[] bytes) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = md.digest(bytes);
    return DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
  }

  public MetadataRecord startScan(byte[] fileBytes, String sha256) {
    if (!metadataStore.contains(sha256)) {
      MetadataRecord record = new MetadataRecord();
      record.setSha256(sha256);
      record.setProcessingComplete(false);
      record.setSubmissionDate(Instant.now());
      metadataStore.put(record);
      processMetadataAsync(fileBytes, sha256);
      return record;
    } else {
      return metadataStore.get(sha256);
    }
  }

  @Async
  public CompletableFuture<Void> processMetadataAsync(byte[] fileBytes, String sha256) {
    try (PDDocument document = PDDocument.load(fileBytes)) {
      MetadataRecord record = metadataStore.get(sha256);
      PDDocumentInformation info = document.getDocumentInformation();

      record.setPdfVersion(String.valueOf(document.getVersion()));
      record.setProducer(info.getProducer());
      record.setAuthor(info.getAuthor());
      record.setCreatedDate(info.getCreationDate() != null ? info.getCreationDate().getTime() : null);
      record.setUpdatedDate(info.getModificationDate() != null ? info.getModificationDate().getTime() : null);
      record.setProcessingComplete(true);

      metadataStore.put(record);

    } catch (IOException e) {
      e.printStackTrace();
      // Optionally update record with error info
    }
    return CompletableFuture.completedFuture(null);
  }

  public MetadataRecord lookup(String sha256) {
    return metadataStore.get(sha256);
  }
}
