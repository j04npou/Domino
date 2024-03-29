package domino;

import java.io.Serializable;

public class Tile implements Serializable {
    private int tileDotsLeft;
    private int tileDotsRight;
    protected final String[][] tilesH = {
            { "\uD83C\uDC31", "\uD83C\uDC32", "\uD83C\uDC33", "\uD83C\uDC34", "\uD83C\uDC35", "\uD83C\uDC36", "\uD83C\uDC37" },
            { "\uD83C\uDC38", "\uD83C\uDC39", "\uD83C\uDC3A", "\uD83C\uDC3B", "\uD83C\uDC3C", "\uD83C\uDC3D", "\uD83C\uDC3E" },
            { "\uD83C\uDC3F", "\uD83C\uDC40", "\uD83C\uDC41", "\uD83C\uDC42", "\uD83C\uDC43", "\uD83C\uDC44", "\uD83C\uDC45" },
            { "\uD83C\uDC46", "\uD83C\uDC47", "\uD83C\uDC48", "\uD83C\uDC49", "\uD83C\uDC4A", "\uD83C\uDC4B", "\uD83C\uDC4C" },
            { "\uD83C\uDC4D", "\uD83C\uDC4E", "\uD83C\uDC4F", "\uD83C\uDC50", "\uD83C\uDC51", "\uD83C\uDC52", "\uD83C\uDC53" },
            { "\uD83C\uDC54", "\uD83C\uDC55", "\uD83C\uDC56", "\uD83C\uDC57", "\uD83C\uDC58", "\uD83C\uDC59", "\uD83C\uDC5A" },
            { "\uD83C\uDC5B", "\uD83C\uDC5C", "\uD83C\uDC5D", "\uD83C\uDC5E", "\uD83C\uDC5F", "\uD83C\uDC60", "\uD83C\uDC61" }
    };
    protected final String[][] tilesV = {
            { "\uD83C\uDC63", "\uD83C\uDC64", "\uD83C\uDC65", "\uD83C\uDC66", "\uD83C\uDC67", "\uD83C\uDC68", "\uD83C\uDC69" },
            { "\uD83C\uDC6A", "\uD83C\uDC6B", "\uD83C\uDC6C", "\uD83C\uDC6D", "\uD83C\uDC6E", "\uD83C\uDC6F", "\uD83C\uDC70" },
            { "\uD83C\uDC71", "\uD83C\uDC72", "\uD83C\uDC73", "\uD83C\uDC74", "\uD83C\uDC75", "\uD83C\uDC76", "\uD83C\uDC77" },
            { "\uD83C\uDC78", "\uD83C\uDC79", "\uD83C\uDC7A", "\uD83C\uDC7B", "\uD83C\uDC7C", "\uD83C\uDC7D", "\uD83C\uDC7E" },
            { "\uD83C\uDC7F", "\uD83C\uDC80", "\uD83C\uDC81", "\uD83C\uDC82", "\uD83C\uDC83", "\uD83C\uDC84", "\uD83C\uDC85" },
            { "\uD83C\uDC86", "\uD83C\uDC87", "\uD83C\uDC88", "\uD83C\uDC89", "\uD83C\uDC8A", "\uD83C\uDC8B", "\uD83C\uDC8C" },
            { "\uD83C\uDC8D", "\uD83C\uDC8E", "\uD83C\uDC8F", "\uD83C\uDC90", "\uD83C\uDC91", "\uD83C\uDC92", "\uD83C\uDC93" }
    };

    public Tile(int tileDotsLeft, int tileDotsRight) {
        this.setTileDotsLeft(tileDotsLeft);
        this.setTileDotsRight(tileDotsRight);
    }

    protected int getTileDotsLeft() {
        return tileDotsLeft;
    }

    protected void setTileDotsLeft(int tileDotsLeft) {
        this.tileDotsLeft = tileDotsLeft;
    }

    protected int getTileDotsRight() {
        return tileDotsRight;
    }

    protected void setTileDotsRight(int tileDotsRight) {
        this.tileDotsRight = tileDotsRight;
    }

    @Override
    public String toString() {
        if (tileDotsLeft == tileDotsRight)
            return tilesV[tileDotsLeft][tileDotsRight];
        else
            return tilesH[tileDotsLeft][tileDotsRight];
    }

    protected String showHtile() {
        return tilesH[tileDotsLeft][tileDotsRight];
    }
}