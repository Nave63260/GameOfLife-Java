package Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private List<List<Square>> squares;


    public Grid(int size) {
        this.squares = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            this.squares.add(new ArrayList<>());

            for (int y = 0; y < size; y++) {
                this.squares.get(i).add(new Square());
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (List<Square> iSquare : squares) {
            s = s.concat(iSquare.toString()).concat("\n");
        }

        return s;
    }

    public Square getAt(int i, int y) {
        return this.squares.get(i).get(y);
    }

    public void setSquares(List<List<Square>> squares) {
        this.squares = squares;
    }

    public void setAt(int i, int y, int value) {
        this.squares.get(i).get(y).setIsOn(value);
    }

    public int size() {
        return this.squares.size();
    }

    public boolean isIn(int i, int y) {
        return i < this.size() && y < this.size() && i >= 0 && y >= 0;
    }

    public Grid getNeighborsAsGrid(int i, int y) {
        Grid returnGrid = new Grid(3);

        Map<Integer, Integer> posToIYPos = new HashMap<>();
        posToIYPos.put(0,-1);
        posToIYPos.put(1,0);
        posToIYPos.put(2,1);

        for (int pos1 = 0; pos1 < 3; pos1++) {
            for (int pos2 = 0; pos2 < 3; pos2++) {
                if ((pos1 == 1 && pos2 == 1) || !this.isIn(i+posToIYPos.get(pos1), y+posToIYPos.get(pos2))) {
                    returnGrid.setAt(pos1, pos2, -1);
                } else {
                    returnGrid.setAt(pos1, pos2, this.getAt(i+posToIYPos.get(pos1), y+posToIYPos.get(pos2)).getIsOn());
                }
            }
        }

        return returnGrid;
    }

    public List<Integer> getNeighborsAsInts(int i, int y) {
        Grid grid = this.getNeighborsAsGrid(i, y);
        List<Integer> returnList = new ArrayList<>();

        for (int pos1 = 0; pos1 < grid.size(); pos1++) {
            for (int pos2 = 0; pos2 < grid.size(); pos2++) {
                if (grid.getAt(pos1, pos2).getIsOn() != -1) returnList.add(grid.getAt(pos1, pos2).getIsOn());
            }
        }

        return returnList;
    }

    public Map<Integer, Integer> getOccurrences(int i, int y) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(0, 0);

        for (Integer val : this.getNeighborsAsInts(i, y)) {
            if (val == 0) map.put(0, map.get(0)+1);
            else if (val == 1) map.put(1, map.get(1)+1);
        }

        return map;
    }

    public int update(int i, int y) {
        Map<Integer, Integer> occurrences = this.getOccurrences(i, y);
        int update = -1;

        if (this.getAt(i, y).getIsOn() == 1 && (occurrences.get(1) < 2 || occurrences.get(1) > 3)) {
            update = 0;
        } else if (this.getAt(i, y).getIsOn() == 0 && occurrences.get(1) == 3) {
            update = 1;
        }

        return update;
    }

    public void update(List<List<Integer>> updates) {
        for (List<Integer> i : updates) {
            this.setAt(i.get(0), i.get(1), i.get(2));
        }
    }

    public void simulate(int steps) {
        List<List<Integer>> tmp = new ArrayList<>();
        while (steps > 0) {
            for (int i = 0; i < this.size(); i++) {
                for (int y = 0; y < this.size(); y++) {
                    int u = this.update(i, y);
                    if (u != -1) {
                        List<Integer> cur = new ArrayList<>();
                        cur.add(i);
                        cur.add(y);
                        cur.add(u);
                        tmp.add(cur);
                    }
                }
            }
            update(tmp);
            steps--;
        }
    }
}
