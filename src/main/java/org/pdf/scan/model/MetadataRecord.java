package org.pdf.scan.model;

import java.time.Instant;
import java.util.Date;

public class MetadataRecord {
  private String sha256;
  private String pdfVersion;
  private String producer;
  private String author;
  private Date createdDate;
  private Date updatedDate;
  private Instant submissionDate;
  private boolean processingComplete;

  // Getters and setters

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  public String getPdfVersion() {
    return pdfVersion;
  }

  public void setPdfVersion(String pdfVersion) {
    this.pdfVersion = pdfVersion;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Instant getSubmissionDate() {
    return submissionDate;
  }

  public void setSubmissionDate(Instant submissionDate) {
    this.submissionDate = submissionDate;
  }

  public boolean isProcessingComplete() {
    return processingComplete;
  }

  public void setProcessingComplete(boolean processingComplete) {
    this.processingComplete = processingComplete;
  }
}
