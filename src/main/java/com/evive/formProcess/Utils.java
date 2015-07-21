package com.evive.formProcess;

import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static <T> List<T> SWAP(T x, T y) {
        final List<T> vals = new ArrayList<T>();
        final T tmp = x;
        x = y;
        y = tmp;
        vals.add(x);
        vals.add(y);
        return vals;
    }

    public static <T extends Number> Double EUCLIDEAN_DISTANCE(final T[][] coordinates, final int i) {
        final Double xdiff = double.class.cast(coordinates[i][0]) - double.class.cast(coordinates[i - 1][0]);;
        final Double ydiff = double.class.cast(coordinates[i][1]) - double.class.cast(coordinates[i - 1][1]);;

        return xdiff * xdiff + ydiff * ydiff;
    }

    public static <T extends Number> Double EUCLIDEAN_DISTANCE(final T[] coordinates1, final T[] coordinates2) {
        final Double xdiff = double.class.cast(coordinates1[0]) - double.class.cast(coordinates2[0]);
        final Double ydiff = double.class.cast(coordinates1[1]) - double.class.cast(coordinates2[1]);

        return xdiff * xdiff + ydiff * ydiff;
    }

}
