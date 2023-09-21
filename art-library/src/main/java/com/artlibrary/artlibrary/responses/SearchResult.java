package com.artlibrary.artlibrary.responses;

import java.util.List;
import java.util.Objects;

public class SearchResult{
    private String id;
    private String type;
    private String title;
    private String description;
    private String created_by;
    private List<String> tags;
    private String created;
    private String modified;
    private Integer height;
    private Integer width;
    private String slug;
    private String src;
    private double size;

    public SearchResult() {
    }

    public SearchResult(String id, String type, String title, String description, String created_by, List<String> tags, String created, String modified, Integer height, Integer width, String slug, String src, double size) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.created_by = created_by;
        this.tags = tags;
        this.created = created;
        this.modified = modified;
        this.height = height;
        this.width = width;
        this.slug = slug;
        this.src = src;
        this.size = size;
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

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public SearchResult id(String id) {
        this.id = id;
        return this;
    }

    public SearchResult type(String type) {
        this.type = type;
        return this;
    }

    public SearchResult title(String title) {
        this.title = title;
        return this;
    }

    public SearchResult description(String description) {
        this.description = description;
        return this;
    }

    public SearchResult created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public SearchResult tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public SearchResult created(String created) {
        this.created = created;
        return this;
    }

    public SearchResult modified(String modified) {
        this.modified = modified;
        return this;
    }

    public SearchResult height(Integer height) {
        this.height = height;
        return this;
    }

    public SearchResult width(Integer width) {
        this.width = width;
        return this;
    }

    public SearchResult slug(String slug) {
        this.slug = slug;
        return this;
    }

    public SearchResult src(String src) {
        this.src = src;
        return this;
    }

    public SearchResult size(double size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SearchResult)) {
            return false;
        }
        SearchResult searchResult = (SearchResult) o;
        return Objects.equals(id, searchResult.id) && Objects.equals(type, searchResult.type) && Objects.equals(title, searchResult.title) && Objects.equals(description, searchResult.description) && Objects.equals(created_by, searchResult.created_by) && Objects.equals(tags, searchResult.tags) && Objects.equals(created, searchResult.created) && Objects.equals(modified, searchResult.modified) && Objects.equals(height, searchResult.height) && Objects.equals(width, searchResult.width) && Objects.equals(slug, searchResult.slug) && Objects.equals(src, searchResult.src) && size == searchResult.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, title, description, created_by, tags, created, modified, height, width, slug, src, size);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", created_by='" + getCreated_by() + "'" +
            ", tags='" + getTags() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", height='" + getHeight() + "'" +
            ", width='" + getWidth() + "'" +
            ", slug='" + getSlug() + "'" +
            ", src='" + getSrc() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
}