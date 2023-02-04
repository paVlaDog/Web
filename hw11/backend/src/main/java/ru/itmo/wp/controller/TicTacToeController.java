package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.MoveCredentials;
import ru.itmo.wp.form.TicTacToeCredentials;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/1")
public class TicTacToeController {
    private State state;
    private int size = 3;
    private int needToWin = 3;

    public TicTacToeController(){};

    @GetMapping("state")
    public State getState() {
        return state;
    }

    @PostMapping("createBoard")
    public void createBoard(@RequestBody TicTacToeCredentials ticTacToeCredentials) {
        size = Integer.parseInt(ticTacToeCredentials.getSize());
        needToWin = Integer.parseInt(ticTacToeCredentials.getNeedToWin());
        state = new State(size, needToWin);
    }

    @PostMapping("move")
    public int onMove(@RequestBody @Valid MoveCredentials moveCredentials) {
        state = state != null ? state : new State(size, needToWin);
        try {
            state.doMove(Integer.parseInt(moveCredentials.getRow()),
                    Integer.parseInt(moveCredentials.getCol()));
        } catch (NumberFormatException e) {
            //
        }
        return 0;
    }

    @PostMapping("newGame")
    public void newGame(@RequestBody @Valid MoveCredentials moveCredentials) {
        state = null;
    }


    public static class State {
        private final int size;
        private final String[][] cells;
        private boolean crossesMove;
        private String phase;
        private final int needToWin;
        private int numberOfTurn = 1;

        public State(int size, int needToWin) {
            this.cells = new String[size][size];
            this.crossesMove = false;
            this.phase = "RUNNING";
            this.size = size;
            this.needToWin = needToWin;
        }

        public int getSize() {
            return size;
        }

        public String[][] getCells() {
            return cells;
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        public String getPhase() {
            return phase;
        }

        public void doMove(int row, int col) {
            if (row >= 0 && row < size && col >= 0 && col < size && cells[row][col] == null) {
                cells[row][col] = crossesMove ? "X" : "0";
                crossesMove = !crossesMove;
                if (checkedWin(row, col, cells[row][col])) {
                    phase = cells[row][col].equals("X") ? "WON_X" : "WON_O";
                }
                if (numberOfTurn == size*size) {
                    phase = "DRAW";
                }
                numberOfTurn++;
            }
        }

        public boolean checkedWin(int row, int col, String player) {
            if (lengthOfWay(row, col, player, 1, 0) >= needToWin ||
                    lengthOfWay(row, col, player, 0, 1) >= needToWin ||
                    lengthOfWay(row, col, player, 1, 1) >= needToWin ||
                    lengthOfWay(row, col, player, 1, -1) >= needToWin) {
                return true;
            } else {
                return false;
            }
        }

        public int lengthOfWay(int row, int col, String player, int dRow, int dCol) {
            int len = 1;
            for (int shift = 1; shift < size; shift++) {
                if (row + dRow * shift >= 0 && row + dRow * shift <= size - 1 &&
                        col + dCol * shift >= 0 && col + dCol * shift <= size - 1 &&
                        cells[row + dRow * shift][col + dCol * shift] != null &&
                        cells[row + dRow * shift][col + dCol * shift].equals(player)) len++;
                else break;
            }
            for (int shift = -1; shift > -size; shift--) {
                if (row + dRow * shift >= 0 && row + dRow * shift <= size - 1 &&
                        col + dCol * shift >= 0 && col + dCol * shift <= size - 1 &&
                        cells[row + dRow * shift][col + dCol * shift] != null &&
                        cells[row + dRow * shift][col + dCol * shift].equals(player)) len++;
                else break;
            }
            System.out.println("len: " + len);
            return len;
        }
    }
}
