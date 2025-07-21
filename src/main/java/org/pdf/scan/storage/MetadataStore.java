package org.pdf.scan.storage;

import org.pdf.scan.model.MetadataRecord;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MetadataStore {
  private ConcurrentHashMap<String, MetadataRecord> store = new ConcurrentHashMap<>();

  public void put(MetadataRecord record) {
    store.put(record.getSha256(), record);
  }

  public MetadataRecord get(String sha256) {
    return store.get(sha256);
  }

  public boolean contains(String sha256) {
    return store.containsKey(sha256);
  }
}
