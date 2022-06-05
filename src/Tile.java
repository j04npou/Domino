public class Tile {
    private int tileDotsLeft;
    private int tileDotsRight;
    private boolean isLeft;

    public Tile(int tileDotsLeft, int tileDotsRigth, boolean isLeft) {
        this.setTileDotsLeft(tileDotsLeft);
        this.setTileDotsRight(tileDotsRigth);
        this.setLeft(isLeft);
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

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    @Override
    public String toString() {
        return tileDotsLeft + ":" + tileDotsRight;
    }
}
