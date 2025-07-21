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

## Follow-up Questions and Answers:
### 1. Choice of the backing database?
  For simplicity and speed in this challenge, I chose an in-memory map. 
  For production, a NoSQL database like Redis (fast, supports TTL) or a relational DB like PostgreSQL could be used. 
  Redis enables fast access and TTL for cleanup, PostgreSQL offers strong consistency and querying capabilities.

### 2. Ways to secure your database from external access?
  Use firewall rules limiting access to specific IP addresses or VPCs.
  Enable authentication with strong credentials.
  Encrypt connections using TLS.
  Use network isolation like VPNs or private subnets.
  Implement role-based access control (RBAC).
  Regularly audit access logs.

### 3. Counting API hits in the past 24 hours?
  Use a time-series data store or analytics tool (e.g., Redis, Prometheus). Increment a counter with timestamps (e.g., using Redis sorted sets) and query/count hits within the last 24 hours.

### 4. Visualizing API demand in the past 24 hours?
  Integrate with monitoring and dashboard tools like Grafana, Kibana, or Datadog, which visualize request rates over time.

### 5. Scaling to meet increased demand?
  Scale horizontally by adding more instances behind a load balancer.
  Use stateless services so instances can be added/removed easily.
  Use caching layers to reduce DB load.
  Employ auto-scaling policies in cloud environments.
  
### 6. Tools to know when running low on disk space?
  Cloud provider monitoring tools (AWS CloudWatch, Azure Monitor)
  OS-level tools (like Nagios, Zabbix, Prometheus node_exporter)
  Container orchestration monitoring (Kubernetes metrics).

### 7. Preventing DDoS attacks?
  Use rate limiting and throttling.
  Employ Web Application Firewalls (WAF).
  Use cloud provider DDoS protection services (e.g., AWS Shield).
  Use IP blacklisting and traffic scrubbing services.

### 8. Devices to receive notifications anywhere on stack?
  Smartphones (push notifications, SMS).
  Email.
  ChatOps tools (Slack, Microsoft Teams).
  Pager devices / PagerDuty.

### 9. Determining what happened on receiving "Error Occurred!" notification?
  Check logs for error details.
  Correlate error timestamp with request logs and metrics.
  Use tracing systems (like Jaeger, Zipkin) to trace errors.
  Review monitoring dashboards and alerts.

### 10. How to fix the above error?
  Identify root cause from logs/traces.
  Apply code fixes or configuration changes.
  Roll out patches and test.
  Enhance error handling and monitoring to catch earlier.

### 11. Guarantee 100% uptime during rolling updates?
  Use blue-green or canary deployments.
  Load balancers remove instances under update from traffic pool before restart.
  Deploy and test on subset, then gradually scale.
  Use health checks to ensure only healthy instances serve traffic.

### 12. Faster results when DB is far away?
  Use caching layers near the application (e.g., Redis cache).
  Use CDN where applicable.
  Implement read replicas closer to the app for read-heavy queries.
  Asynchronous data fetch and return cached data with eventual consistency.

### 13. Rebuilding DB if it suddenly dies?
  Use regular backups stored remotely.
  Have replicas or snapshots for point-in-time recovery.
  Use automated failover systems.
  Restore backup to new instance and re-sync data if needed.

### 14. Rejecting bogus requests by robots?
  Implement input validation and return 404 or 403 for invalid endpoints.
  Use rate limiting.
  Use CAPTCHA or challenge-response for front-ends.
  Bot detection and filtering at load balancer or WAF.
  
### 15. What to check if customers get no response?
  Server status and logs.
  Network connectivity.
  Rate limiting or WAF blocking.
  Error or crash on backend.
  DB health and availability.

### 16. Running low on disk space with 100% uptime?
  Clean up logs and temporary files automatically.
  Archive or move old data to external storage.
  Add external or network-attached storage dynamically.
  Implement storage quotas and alerts to act before critical levels.

### 17. Store surge requests without dropping them?
  Use message queues (Kafka, RabbitMQ, AWS SQS) to buffer requests.
  Design APIs to asynchronously push requests to queues.
  Process queue consumers at own pace.

### 18. Tools to check server reachability?
  ping to check basic network connectivity.
  traceroute to see the path packets take.
  telnet or nc (netcat) to verify specific port availability.

### 19. Allow requests only from few servers/clients?
  Implement IP whitelisting at firewall or application load balancer.
  Use mutual TLS authentication for clients.
  Use API keys and validate on backend.

### 20. Ensure service on downed server doesn’t receive requests?
  Use health checks integrated with load balancer to remove unhealthy instances.
  Use orchestration tools (Kubernetes) with readiness probes.

### 21. Make traffic secure and confidential?
  Use HTTPS/TLS for all communications.
  Employ secure protocols and encryption for DB connections.
  Regularly update certs and security patches.
  Use VPNs or private connections where applicable.

