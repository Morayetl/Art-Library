package com.artlibrary.artlibrary.responses;

import java.util.Objects;

public class CaptchaResponse {

	private boolean success;
	private String challenge_ts;
    private String hostname;

    public CaptchaResponse() {
    }

    public CaptchaResponse(boolean success, String challenge_ts, String hostname) {
        this.success = success;
        this.challenge_ts = challenge_ts;
        this.hostname = hostname;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallenge_ts() {
        return this.challenge_ts;
    }

    public void setChallenge_ts(String challenge_ts) {
        this.challenge_ts = challenge_ts;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public CaptchaResponse success(boolean success) {
        this.success = success;
        return this;
    }

    public CaptchaResponse challenge_ts(String challenge_ts) {
        this.challenge_ts = challenge_ts;
        return this;
    }

    public CaptchaResponse hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CaptchaResponse)) {
            return false;
        }
        CaptchaResponse captchaResponse = (CaptchaResponse) o;
        return success == captchaResponse.success && Objects.equals(challenge_ts, captchaResponse.challenge_ts) && Objects.equals(hostname, captchaResponse.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, challenge_ts, hostname);
    }

    @Override
    public String toString() {
        return "{" +
            " success='" + isSuccess() + "'" +
            ", challenge_ts='" + getChallenge_ts() + "'" +
            ", hostname='" + getHostname() + "'" +
            "}";
    }
    
}