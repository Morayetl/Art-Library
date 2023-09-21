package com.artlibrary.artlibrary.responses;

import java.util.List;
import java.util.Objects;

public class FileMetaDataResponse {
    private String id;
    private String type;
    private String title;
    private String src;
    private String description;
    private String created_by;
    private List<String> tags;


    private String created;
    private String modified;
    private Boolean published;
    private String slug;
    private double size;
    private Integer originalImageHeight;
    private Integer originalImageWidth;
    private Integer views;

    public FileMetaDataResponse() {
    }

    public FileMetaDataResponse(String id, String type, String title, String src, String description, String created_by, List<String> tags, String created, String modified, Boolean published, String slug, double size, Integer originalImageHeight, Integer originalImageWidth, Integer views) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.src = src;
        this.description = description;
        this.created_by = created_by;
        this.tags = tags;
        this.created = created;
        this.modified = modified;
        this.published = published;
        this.slug = slug;
        this.size = size;
        this.originalImageHeight = originalImageHeight;
        this.originalImageWidth = originalImageWidth;
        this.views = views;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return this.modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Boolean isPublished() {
        return this.published;
    }

    public Boolean getPublished() {
        return this.published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Integer getOriginalImageHeight() {
        return this.originalImageHeight;
    }

    public void setOriginalImageHeight(Integer originalImageHeight) {
        this.originalImageHeight = originalImageHeight;
    }

    public Integer getOriginalImageWidth() {
        return this.originalImageWidth;
    }

    public void setOriginalImageWidth(Integer originalImageWidth) {
        this.originalImageWidth = originalImageWidth;
    }

    public Integer getViews() {
        return this.views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public FileMetaDataResponse id(String id) {
        this.id = id;
        return this;
    }

    public FileMetaDataResponse type(String type) {
        this.type = type;
        return this;
    }

    public FileMetaDataResponse title(String title) {
        this.title = title;
        return this;
    }

    public FileMetaDataResponse src(String src) {
        this.src = src;
        return this;
    }

    public FileMetaDataResponse description(String description) {
        this.description = description;
        return this;
    }

    public FileMetaDataResponse created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public FileMetaDataResponse tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public FileMetaDataResponse created(String created) {
        this.created = created;
        return this;
    }

    public FileMetaDataResponse modified(String modified) {
        this.modified = modified;
        return this;
    }

    public FileMetaDataResponse published(Boolean published) {
        this.published = published;
        return this;
    }

    public FileMetaDataResponse slug(String slug) {
        this.slug = slug;
        return this;
    }

    public FileMetaDataResponse size(double size) {
        this.size = size;
        return this;
    }

    public FileMetaDataResponse originalImageHeight(Integer originalImageHeight) {
        this.originalImageHeight = originalImageHeight;
        return this;
    }

    public FileMetaDataResponse originalImageWidth(Integer originalImageWidth) {
        this.originalImageWidth = originalImageWidth;
        return this;
    }

    public FileMetaDataResponse views(Integer views) {
        this.views = views;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FileMetaDataResponse)) {
            return false;
        }
        FileMetaDataResponse fileMetaDataResponse = (FileMetaDataResponse) o;
        return Objects.equals(id, fileMetaDataResponse.id) && Objects.equals(type, fileMetaDataResponse.type) && Objects.equals(title, fileMetaDataResponse.title) && Objects.equals(src, fileMetaDataResponse.src) && Objects.equals(description, fileMetaDataResponse.description) && Objects.equals(created_by, fileMetaDataResponse.created_by) && Objects.equals(tags, fileMetaDataResponse.tags) && Objects.equals(created, fileMetaDataResponse.created) && Objects.equals(modified, fileMetaDataResponse.modified) && Objects.equals(published, fileMetaDataResponse.published) && Objects.equals(slug, fileMetaDataResponse.slug) && size == fileMetaDataResponse.size && Objects.equals(originalImageHeight, fileMetaDataResponse.originalImageHeight) && Objects.equals(originalImageWidth, fileMetaDataResponse.originalImageWidth) && Objects.equals(views, fileMetaDataResponse.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, title, src, description, created_by, tags, created, modified, published, slug, size, originalImageHeight, originalImageWidth, views);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", src='" + getSrc() + "'" +
            ", description='" + getDescription() + "'" +
            ", created_by='" + getCreated_by() + "'" +
            ", tags='" + getTags() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", published='" + isPublished() + "'" +
            ", slug='" + getSlug() + "'" +
            ", size='" + getSize() + "'" +
            ", originalImageHeight='" + getOriginalImageHeight() + "'" +
            ", originalImageWidth='" + getOriginalImageWidth() + "'" +
            ", views='" + getViews() + "'" +
            "}";
    }
    
}