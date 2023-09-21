package com.artlibrary.artlibrary.requests;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UploadUpdateRequest{
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String description;
    private List<String> tags;
    private boolean published;

    public UploadUpdateRequest() {
    }

    public UploadUpdateRequest(String title, String description, List<String> tags, boolean published) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.published = published;
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

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isPublished() {
        return this.published;
    }

    public boolean getPublished() {
        return this.published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public UploadUpdateRequest title(String title) {
        this.title = title;
        return this;
    }

    public UploadUpdateRequest description(String description) {
        this.description = description;
        return this;
    }

    public UploadUpdateRequest tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public UploadUpdateRequest published(boolean published) {
        this.published = published;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UploadUpdateRequest)) {
            return false;
        }
        UploadUpdateRequest uploadUpdateRequest = (UploadUpdateRequest) o;
        return Objects.equals(title, uploadUpdateRequest.title) && Objects.equals(description, uploadUpdateRequest.description) && Objects.equals(tags, uploadUpdateRequest.tags) && published == uploadUpdateRequest.published;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tags, published);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            ", published='" + isPublished() + "'" +
            "}";
    }
}