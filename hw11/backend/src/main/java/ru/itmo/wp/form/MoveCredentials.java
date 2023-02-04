package ru.itmo.wp.form;

import ru.itmo.wp.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MoveCredentials {
    @NotEmpty
    private String row;

    @NotEmpty
    private String col;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }
}
