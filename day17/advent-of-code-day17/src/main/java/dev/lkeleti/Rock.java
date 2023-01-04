package dev.lkeleti;

public class Rock {
    private static int nextShape = 0;
    private String[] shape;
    private int posY;
    private final int height;

    public Rock(int posY) {
        this.posY = posY;
        shape = generate();
        height = shape.length;
    }

    public String[] getShape() {
        return shape;
    }

    public int getHeight() {
        return height;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public static String[] generate() {
        String[] shape;
        switch (nextShape) {
            case 0:
                shape = new String[]{"  #### "};
            break;
            case 1:
                shape =  new String[]{"   #   ",
                                    "  ###  ",
                                    "   #   "};
            break;
            case 2:
                shape =  new String[]{"  ###  ",
                                      "    #  ",
                                      "    #  "};
            break;
            case 3:
                shape =  new String[]{"  #    ",
                                      "  #    ",
                                      "  #    ",
                                      "  #    "};
            break;
            default:
                shape =  new String[]{"  ##   ",
                                      "  ##   "};
            break;
        }
        if (nextShape == 4) {
            nextShape = 0;
        }
        else {
            nextShape++;
        }
        return shape;
    }

    public boolean isCollide(Rock otherRock) {
        if (posY < 0) {
            return false;
        }

        if (posY > otherRock.getPosY() + otherRock.getHeight() ||
                posY + height < otherRock.getPosY()) {
            return false;
        }

        int minY = Math.min(posY, otherRock.getPosY());
        int maxY = Math.max(posY+height, otherRock.getPosY()+ otherRock.getHeight());

        for (int i = minY; i < maxY; i++) {
            if (posY >= i && posY <= i) {
                int otherIndex = i - otherRock.getPosY() ;
                if (otherIndex >= 0 && otherIndex < otherRock.getHeight()) {
                    String thisRow = shape[i -posY];
                    String otherRow = otherRock.getShape()[i -otherRock.getPosY()];

                    for ( int j = 0; j < 7; j++) {
                        if (thisRow.charAt(j) == '#' && otherRow.charAt(j) == '#') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canMoveSide(char direction) {
        int position = 6;
        if (direction == '<') {
            position = 0;
        }

        for (int i = 0; i < height; i++) {
            if (shape[i].charAt(position) != ' ') {
                return false;
            }
        }
        return true;
    }

    public void moveSide(char direction) {

        String[] newShape = new String[height];
        for (int i = 0; i < height; i++) {
            if (direction == '<') {
                newShape[i] = shape[i].substring(1,7) + " ";
            }
            else {
                newShape[i] = " " + shape[i].substring(0,6);
            }
        }
        shape = newShape;
    }
}
