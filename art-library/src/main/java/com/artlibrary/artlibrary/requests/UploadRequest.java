package com.artlibrary.artlibrary.requests;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class UploadRequest{
    public MultipartFile file;
    @NotNull
    @NotEmpty
    public String title;
    @NotNull
    @NotEmpty
    public String description;
    public String tags;

    public UploadRequest() {
    }

    public UploadRequest(MultipartFile file, String title, String description, String tags) {
        this.file = file;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public UploadRequest file(MultipartFile file) {
        this.file = file;
        return this;
    }

    public UploadRequest title(String title) {
        this.title = title;
        return this;
    }

    public UploadRequest description(String description) {
        this.description = description;
        return this;
    }

    public UploadRequest tags(String tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UploadRequest)) {
            return false;
        }
        UploadRequest uploadRequest = (UploadRequest) o;
        return Objects.equals(file, uploadRequest.file) && Objects.equals(title, uploadRequest.title) && Objects.equals(description, uploadRequest.description) && Objects.equals(tags, uploadRequest.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, title, description, tags);
    }

    @Override
    public String toString() {
        return "{" +
            " file='" + getFile() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}