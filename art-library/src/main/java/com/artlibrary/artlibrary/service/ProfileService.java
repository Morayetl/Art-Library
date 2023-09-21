package com.artlibrary.artlibrary.service;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Value("${artlibrary.app.mediaUrl}")
    private String mediaUrl;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    public ProfileService() {
    }

    public ProfileService(String mediaUrl, String activeProfile) {
        this.mediaUrl = mediaUrl;
        this.activeProfile = activeProfile;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getActiveProfile() {
        return this.activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public ProfileService mediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public ProfileService activeProfile(String activeProfile) {
        this.activeProfile = activeProfile;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProfileService)) {
            return false;
        }
        ProfileService profileService = (ProfileService) o;
        return Objects.equals(mediaUrl, profileService.mediaUrl) && Objects.equals(activeProfile, profileService.activeProfile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaUrl, activeProfile);
    }

    @Override
    public String toString() {
        return "{" +
            " mediaUrl='" + getMediaUrl() + "'" +
            ", activeProfile='" + getActiveProfile() + "'" +
            "}";
    }

}
