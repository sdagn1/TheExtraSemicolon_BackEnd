package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileImportRequest {
    private String filename;

    @JsonCreator
    public FileImportRequest(
            @JsonProperty("filename") final String filename) {
        this.filename = filename;

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }


}
