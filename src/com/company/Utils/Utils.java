package com.company.Utils;

public class Utils {

    public static int randInt(int lowLimit, int highLimit)
    {
        // Генерация случайного целого значения в диапазоне [lowLimit, highlimit].
        if (lowLimit > highLimit)
        {
            int temp = lowLimit;
            lowLimit = highLimit;
            highLimit = temp;
        }
        int randomNumber = lowLimit + (int)(Math.random() * (highLimit - lowLimit + 1));
        return randomNumber;
    }

    public static void sleep(long milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
