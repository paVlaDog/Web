package ru.itmo.wp.form;

import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicTacToeCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 3)
    private String size;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 3)
    private String needToWin;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNeedToWin() {
        return needToWin;
    }

    public void setNeedToWin(String needToWin) {
        this.needToWin = needToWin;
    }
}
