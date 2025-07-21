# PDF Scanning API

This is a Java Spring Boot application that exposes REST APIs to scan PDF files and asynchronously extract file metadata. It returns a SHA256 hash immediately upon upload, and allows users to lookup extracted metadata by hash.

---

## Project Structure


---

## Class Descriptions

### 1. `PdfScanApiApplication.java`
Location: `src/main/java/org/pdf/scan/`

- Main entry point for the Spring Boot application.
- Annotated with `@EnableAsync` to enable asynchronous processing.

### 2. Controller: `PdfScannerController.java`
Location: `src/main/java/org/pdf/scan/controller/`

- Defines the `/scan` and `/lookup` REST endpoints.
- Handles HTTP file upload and lookup requests.
- Delegates all business logic to `PdfScanService`.
- Returns JSON responses including the SHA256 hash and metadata.

### 3. Service: `PdfScanService.java`
Location: `src/main/java/org/pdf/scan/service/`

- Contains business logic for:
  - Validating PDF content type.
  - Calculating SHA256 hash synchronously.
  - Starting asynchronous metadata extraction using Apache PDFBox.
  - Looking up metadata by SHA256 hash.
- Uses Spring’s `@Async` for background processing.

### 4. Model: `MetadataRecord.java`
Location: `src/main/java/org/pdf/scan/model/`

- Plain Java object (POJO) representing metadata for a scanned PDF file.
- Fields include hash, PDF version, producer, author, created & updated dates, submission timestamp, and processing status.
- Includes getters and setters for all properties.

### 5. Storage: `MetadataStore.java`
Location: `src/main/java/org/pdf/scan/storage/`

- In-memory thread-safe `ConcurrentHashMap` storing PDF metadata keyed by SHA256.
- Provides methods to put, get, and check for records.
- Acts as a simple mock database for demonstration purposes.

---

## Running the Application

- Build the project using Maven:  
  ```bash
  mvn clean package

- Run using Spring Boot Maven plugin:

mvn spring-boot:run

- Run the generated jar:

java -jar target/pdf-scan-api-1.0-SNAPSHOT.jar

## API Endpoints

### 1. POST /scan
Accepts a PDF file upload file via multipart form data.
Returns JSON with SHA256 hash if file is PDF; rejects other file types.

### 2. GET /lookup?sha256={hash}
Looks up metadata extracted for the given SHA256 hash.
Returns metadata JSON or status message if processing or not found.

## Dependencies
Spring Boot Starter Web – REST API and multipart file upload support
Spring Boot Starter Task – For async processing (via @EnableAsync)
Apache PDFBox – Reading PDF metadata
JAXB (if using Java 9+) – For SHA256 hex string conversion

## Notes
The current implementation uses in-memory storage; a persistent database can replace MetadataStore for production.
Asynchronous processing decouples immediate hash response from longer metadata extraction work.
PDF metadata extraction is done with Apache PDFBox library.

