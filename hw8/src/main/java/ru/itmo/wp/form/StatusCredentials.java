package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class StatusCredentials {
    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    @NotEmpty
    private String userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String content) {
        this.status = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
