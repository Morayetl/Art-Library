package com.artlibrary.artlibrary.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.Set;

import com.artlibrary.artlibrary.enums.EFileType;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Document(collection = "file")
public class UploadFile {
    @Id
    private String id;
    
    private String title;
        
    private Binary file;

    private User owner;

    private EFileType fileType;

    private Set<String> tags;

    public UploadFile() {
    }

    public UploadFile(String title, Binary file, User owner, EFileType fileType, Set<String> tags) {
        this.title = title;
        this.file = file;
        this.owner = owner;
        this.fileType = fileType;
        this.tags = tags;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Binary getImage() {
        return this.file;
    }

    public void setImage(Binary file) {
        this.file = file;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public EFileType getFileType() {
        return this.fileType;
    }

    public void setFileType(EFileType fileType) {
        this.fileType = fileType;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public UploadFile id(String id) {
        this.id = id;
        return this;
    }

    public UploadFile title(String title) {
        this.title = title;
        return this;
    }

    public UploadFile file(Binary file) {
        this.file = file;
        return this;
    }

    public UploadFile owner(User owner) {
        this.owner = owner;
        return this;
    }

    public UploadFile fileType(EFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public UploadFile tags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UploadFile)) {
            return false;
        }
        UploadFile UploadFile = (UploadFile) o;
        return Objects.equals(id, UploadFile.id) && Objects.equals(title, UploadFile.title) && Objects.equals(file, UploadFile.file) && Objects.equals(owner, UploadFile.owner) && Objects.equals(fileType, UploadFile.fileType) && Objects.equals(tags, UploadFile.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, file, owner, fileType, tags);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", file='" + getImage() + "'" +
            ", owner='" + getOwner() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }

}