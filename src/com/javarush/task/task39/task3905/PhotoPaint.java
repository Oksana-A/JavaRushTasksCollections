package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        boolean flag = false;
        if (x >= 0 && y >= 0 && desiredColor != null) {
            if (image.length > y && image[y].length > x) {
                flag = image[y][x] != desiredColor;
                if (flag)
                    image[y][x] = desiredColor;
//                    for (int i = 0; i < image.length; i++) {
//                        for (int k = 0; k <image[i].length; k++) {
//                            image[i][k] = desiredColor;
//                        }
//                    }
            }
        }
        return flag;
    }
}
