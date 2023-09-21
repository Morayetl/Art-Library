package com.artlibrary.artlibrary.settings;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSettings {

    @Value("${google.recaptcha.key.site}")
    private String site;
    @Value("${google.recaptcha.key.secret}")
    private String secret;

    public CaptchaSettings() {
    }

    public CaptchaSettings(String site, String secret) {
        this.site = site;
        this.secret = secret;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public CaptchaSettings site(String site) {
        this.site = site;
        return this;
    }

    public CaptchaSettings secret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CaptchaSettings)) {
            return false;
        }
        CaptchaSettings captchaSettings = (CaptchaSettings) o;
        return Objects.equals(site, captchaSettings.site) && Objects.equals(secret, captchaSettings.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site, secret);
    }

    @Override
    public String toString() {
        return "{" +
            " site='" + getSite() + "'" +
            ", secret='" + getSecret() + "'" +
            "}";
    }

}