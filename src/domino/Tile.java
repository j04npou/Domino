package domino;

public class Tile {
    private int tileDotsLeft;
    private int tileDotsRight;

    public Tile(int tileDotsLeft, int tileDotsRight) {
        this.setTileDotsLeft(tileDotsLeft);
        this.setTileDotsRight(tileDotsRight);
    }

    public int getTileDotsLeft() {
        return tileDotsLeft;
    }

    public void setTileDotsLeft(int tileDotsLeft) {
        this.tileDotsLeft = tileDotsLeft;
    }

    public int getTileDotsRight() {
        return tileDotsRight;
    }

    public void setTileDotsRight(int tileDotsRight) {
        this.tileDotsRight = tileDotsRight;
    }

    @Override
    public String toString() {
        return tileDotsLeft + ":" + tileDotsRight;
    }
}
