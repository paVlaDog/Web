package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage extends Page {
        private int size = 3;
        private int needToWin = 3;

        protected void action(HttpServletRequest request, Map<String, Object> view) {
            view.put("state", request.getSession().getAttribute("state"));
        }

        @Json
        protected void userState(HttpServletRequest request, Map<String, Object> view) {
            size = Integer.parseInt(request.getParameter("size"));
            needToWin = Integer.parseInt(request.getParameter("needToWin"));
            createState(request, size, needToWin);
            view.put("state", request.getSession().getAttribute("state"));
            throw new RedirectException("/ticTacToe");
        }

        @Json
        private void onMove(HttpServletRequest request, Map<String, Object> view) {
            HttpSession session = request.getSession();
            createState(request, size, needToWin);
            try {
                String name = request.getParameter("name");
                String value = request.getParameter("value");
                if (name != null && value != null && name.length() >= 7 && name.substring(0, 5).equals("cell_")) {
                    int row = -1, col = -1;
                    for (int i = 5; i < name.length(); i++) {
                        if (name.charAt(i) == '-') {
                            row = Integer.parseInt(name.substring(5, i));
                            col = Integer.parseInt(name.substring(i + 1, name.length()));
                            break;
                        }
                    }
                    State state = (State) session.getAttribute("state");
                    state.doMove(row, col);
                } else {
                    setMessage("Incorrect input!");
                }
            } catch (NumberFormatException e2) {
                setMessage("Incorrect input!");
                throw new RedirectException("/ticTacToe");
            }
            action(request, view);
        }

        private void newGame(HttpServletRequest request, Map<String, Object> view) {
            if (request.getSession().getAttribute("state") != null) {
                request.getSession().removeAttribute("state");
            }
            action(request, view);
        }

        private void createState(HttpServletRequest request, int size, int needToWin) {
            HttpSession session = request.getSession();
            if (session.getAttribute("state") == null) {
                session.setAttribute("state", new State(size, needToWin));
            }
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
