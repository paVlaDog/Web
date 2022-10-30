package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
        private void action(Map<String, Object> view, HttpServletRequest request) {
            createState(request);
            view.put("state", request.getSession().getAttribute("state"));
        }

        private void onMove(Map<String, Object> view, HttpServletRequest request) {
            HttpSession session = request.getSession();
            createState(request);
            for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
                if (e.getKey().length() >= 7 && e.getKey().substring(0, 5).equals("cell_")) {
                    int row = Integer.parseInt(e.getKey().substring(5, 6));
                    int col = Integer.parseInt(e.getKey().substring(6, 7));
                    State state = (State) session.getAttribute("state");
                    state.doMove(row, col);
                }
            }
            action(view, request);
        }

        private void newGame(Map<String, Object> view, HttpServletRequest request) {
            HttpSession session = request.getSession();
            session.setAttribute("state", new State());
            action(view, request);
        }

        private void createState(HttpServletRequest request) {
            HttpSession session = request.getSession();
            if (session.getAttribute("state") == null) {
                session.setAttribute("state", new State());
            }
        }

    public static class State {
        private final int size = 9;
        private final String[][] cells;
        private boolean crossesMove;
        private String phase;
        private final int needToWin = 5;
        private int numberOfTurn = 1;

        public State() {
            this.cells = new String[size][size];
            this.crossesMove = false;
            this.phase = "RUNNING";
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
