package org.pdf.scan.model;

import java.time.Instant;
import java.util.Date;

public class MetadataResponse {
    public String sha256;
    public String pdfVersion;
    public String producer;
    public String author;
    public Date createdDate;
    public Date updatedDate;
    public Instant submissionDate;

    public MetadataResponse(MetadataRecord record) {
      this.sha256 = record.getSha256();
      this.pdfVersion = record.getPdfVersion();
      this.producer = record.getProducer();
      this.author = record.getAuthor();
      this.createdDate = record.getCreatedDate();
      this.updatedDate = record.getUpdatedDate();
      this.submissionDate = record.getSubmissionDate();
    }
}
