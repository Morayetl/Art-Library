package com.artlibrary.artlibrary.responses;

import java.util.Objects;

public class CaptchaSiteKeyResponse {
    private String key;

    public CaptchaSiteKeyResponse() {
    }

    public CaptchaSiteKeyResponse(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CaptchaSiteKeyResponse key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CaptchaSiteKeyResponse)) {
            return false;
        }
        CaptchaSiteKeyResponse captchaSiteKeyResponse = (CaptchaSiteKeyResponse) o;
        return Objects.equals(key, captchaSiteKeyResponse.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            "}";
    }
}