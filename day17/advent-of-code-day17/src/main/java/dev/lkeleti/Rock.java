package dev.lkeleti;

public class Rock {
    private static int nextShape = 0;

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
}
