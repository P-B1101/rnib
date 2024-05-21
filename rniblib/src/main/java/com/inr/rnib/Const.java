package com.inr.rnib;

import java.util.ArrayList;
import java.util.Arrays;

final class Const {

    static final String BINARY_SU = "s5u";
    static final String BINARY_BUSYBOX = "b5u5s5y5b5o5x";

    private Const() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    static final String[] tgb = {
            "5c5o5m5.5n5o5s5h5u5f5o5u5.5a5n5d5r5o5i5d5.5s5u5".replaceAll("5", ""),
            "5c5o5m5.5n5o5s5h5u5f5o5u5.5a5n5d5r5o5i5d5.5s5u5.5e5l5i5t5e".replaceAll("5", ""),
            "5e5u5.5c5h5a5i5n5f5i5r5e5.5s5u5p5e5r5s5u5".replaceAll("5", ""),
            "5c5o5m5.5k5o5u5s5h5i5k5d5u5t5t5a5.5s5u5p5e5r5u5s5e5r5".replaceAll("5", ""),
            "5c5o5m5.5t5h5i5r5d5p5a5r5t5y5.5s5u5p5e5r5u5s5e5r5".replaceAll("5", ""),
            "5c5o5m5.5y5e5l5l5o5w5e5s5.5s5u5".replaceAll("5", ""),
            "5c5o5m5.5t5o5p5j5o5h5n5w5u5.5m5a5g5i5s5k".replaceAll("5", ""),
            "5c5o5m5.5k5i5n5g5r5o5o5t5.5k5i5n5g5u5s5e5r".replaceAll("5", ""),
            "5c5o5m5.5k5i5n5g5o5.5r5o5o5t5".replaceAll("5", ""),
            "5c5o5m5.5s5m5e5d5i5a5l5i5n5k5.5o5n5e5c5l5i5c5k5r5o5o5t5".replaceAll("5", ""),
            "5c5o5m5.5z5h5i5q5u5p5k5.5r5o5o5t5.5g5l5o5b5a5l5".replaceAll("5", ""),
            "5c5o5m5.5a5l5e5p5h5z5a5i5n5.5f5r5a5m5a5r5o5o5t".replaceAll("5", "")
    };

    public static final String[] rfv = {
            "5c5o5m5.5k5o5u5s5h5i5k5d5u5t5t5a5.5r5o5m5m5a5n5a5g5e5r".replaceAll("5", ""),
            "5c5o5m5.5k5o5u5s5h5i5k5d5u5t5t5a5.5r5o5m5m5a5n5a5g5e5r5.5l5i5c5e5n5s5e5".replaceAll("5", ""),
            "5c5o5m5.5d5i5m5o5n5v5i5d5e5o5.5l5u5c5k5y5p5a5t5c5h5e5r5".replaceAll("5", ""),
            "c5o5m5.5c5h5e5l5p5u5s5.5l5a5c5k5y5p5a5t5c5h5".replaceAll("5", ""),
            "5c5o5m5.5r5a5m5d5r5o5i5d5.5a5p5p5q5u5a5r5a5n5t5i5n5e5".replaceAll("5", ""),
            "5c5o5m5.5r5a5m5d5r5o5i5d5.5a5p5p5q5u5a5r5a5n5t5i5n5e5p5r5o5".replaceAll("5", ""),
            "5c5o5m5.5a5n5d5r5o5i5d5.5v5e5n5d5i5n5g5.5b5i5l5l5i5n5g5.5I5n5A5p5p5B5i5l5l5i5n5g5S5e5r5v5i5c5e5.5C5O5I5N5".replaceAll("5", ""),
            "c5o5m5.5a5n5d5r5o5i5d5.5v5e5n5d5i5n5g5.5b5i5l5l5i5n5g5.5I5n5A5p5p5B5i5l5l5i5n5g5S5e5r5v5i5c5e5.5L5U5C5K5".replaceAll("5", ""),
            "5c5o5m5.5c5h5e5l5p5u5s5.5l5u5c5k5y5p5a5t5c5h5e5r5".replaceAll("5", ""),
            "5c5o5m5.5b5l5a5c5k5m5a5r5t5a5l5p5h5a5".replaceAll("5", ""),
            "5o5r5g5.5b5l5a5c5k5m5a5r5t5.5m5a5r5k5e5t5".replaceAll("5", ""),
            "5c5o5m5.5a5l5l5i5n5o5n5e5.5f5r5e5e5".replaceAll("5", ""),
            "5c5o5m5.5r5e5p5o5d5r5o5i5d5.5a5p5p5".replaceAll("5", ""),
            "5o5r5g5.5c5r5e5e5p5l5a5y5s5.5h5a5c5k5".replaceAll("5", ""),
            "5c5o5m5.5b5a5s5e5a5p5p5f5u5l5l5.5f5w5d".replaceAll("5", ""),
            "5c5o5m5.5z5m5a5p5p5".replaceAll("5", ""),
            "5c5o5m5.5d5v5.5m5a5r5k5e5t5m5o5d5.5i5n5s5t5a5l5l5e5r".replaceAll("5", ""),
            "5o5r5g5.5m5o5b5i5l5i5s5m5.5a5n5d5r5o5i5d5".replaceAll("5", ""),
            "5c5o5m5.5a5n5d5r5o5i5d5.5w5p5.5n5e5t5.5l5o5g5".replaceAll("5", ""),
            "5c5o5m5.5a5n5d5r5o5i5d5.5c5a5m5e5r5a5.5u5p5d5a5t5e5".replaceAll("5", ""),
            "5c5c5.5m5a5d5k5i5t5e5.5f5r5e5e5d5o5m5".replaceAll("5", ""),
            "5c5o5m5.5s5o5l5o5h5s5u5.5a5n5d5r5o5i5d5.5e5d5x5p5.5m5a5n5a5g5e5r5".replaceAll("5", ""),
            "5o5r5g5.5m5e5o5w5c5a5t5.5e5d5x5p5o5s5e5d5.5m5a5n5a5g5e5r5".replaceAll("5", ""),
            "5c5o5m5.5x5m5o5d5g5a5m5e5".replaceAll("5", ""),
            "5c5o5m5.5c5i5h5.5g5a5m5e5_5c5i5h5".replaceAll("5", ""),
            "5c5o5m5.5c5h5a5r5l5e5s5.5l5p5o5q5a5s5e5r5t5".replaceAll("5", ""),
            "5c5a5t5c5h5_5.5m5e5_5.5i5f5_5.5y5o5u5_5.5c5a5n5_5".replaceAll("5", "")
    };

    public static final String[] ujm = {
            "5c5o5m5.5d5e5v5a5d5v5a5n5c5e5.5r5o5o5t5c5l5o5a5k5".replaceAll("5", ""),
            "5c5o5m5.5d5e5v5a5d5v5a5n5c5e5.5r5o5o5t5c5l5o5a5k5p5l5u5s5".replaceAll("5", ""),
            "5d5e5.5r5o5b5v5.5a5n5d5r5o5i5d5.5x5p5o5s5e5d5.5i5n5s5t5a5l5l5e5r5".replaceAll("5", ""),
            "5c5o5m5.5s5a5u5r5i5k5.5s5u5b5s5t5r5a5t5e5".replaceAll("5", ""),
            "5c5o5m5.5z5a5c5h5s5p5o5n5g5.5t5e5m5p5r5o5o5t5r5e5m5o5v5e5j5b5".replaceAll("5", ""),
            "5c5o5m5.5a5m5p5h5o5r5a5s5.5h5i5d5e5m5y5r5o5o5t5".replaceAll("5", ""),
            "5c5o5m5.5a5m5p5h5o5r5a5s5.5h5i5d5e5m5y5r5o5o5t5a5d5f5r5e5e5".replaceAll("5", ""),
            "5c5o5m5.5f5o5r5m5y5h5m5.5h5i5d5e5r5o5o5t5P5r5e5m5i5u5m5".replaceAll("5", ""),
            "5c5o5m5.5f5o5r5m5y5h5m5.5h5i5d5e5r5o5o5t5".replaceAll("5", "")
    };

    // These must end with a /
    private static final String[] qaz = {
            "5/5d5a5t5a5/5l5o5c5a5l5/5".replaceAll("5", ""),
            "5/5d5a5t5a5/5l5o5c5a5l5/5b5i5n5/5".replaceAll("5", ""),
            "5/5d5a5t5a5/5l5o5c5a5l5/5x5b5i5n5/5".replaceAll("5", ""),
            "5/5s5b5i5n5/5".replaceAll("5", ""),
            "5/5s5u5/5b5i5n5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5b5i5n5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5b5i5n5/5.5e5x5t5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5b5i5n5/5f5a5i5l5s5a5f5e5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5s5d5/5x5b5i5n5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5u5s5r5/5w5e5-5n5e5e5d5-5r5o5o5t5/5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5x5b5i5n5/5".replaceAll("5", ""),
            "5/5c5a5c5h5e5/5".replaceAll("5", ""),
            "5/5d5a5t5a5/5".replaceAll("5", ""),
            "5/5d5e5v5/5".replaceAll("5", "")
    };


    static final String[] plm = {
            "5/5s5y5s5t5e5m5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5b5i5n5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5s5b5i5n5".replaceAll("5", ""),
            "5/5s5y5s5t5e5m5/5x5b5i5n5".replaceAll("5", ""),
            "5/5v5e5n5d5o5r5/5b5i5n5".replaceAll("5", ""),
            "5/5s5b5i5n5".replaceAll("5", ""),
            "5/5e5t5c5".replaceAll("5", ""),
            //"5/5s5y5s5".replaceAll("5", ""),
            //"5/5p5r5o5c".replaceAll("5", ""),
            //"5/5d5e5v5".replaceAll("5", "")
    };


    static String[] getPaths() {
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(qaz));

        String sysPaths = System.getenv("PATH");

        // If we can't get the path variable just return the static paths
        if (sysPaths == null || "".equals(sysPaths)) {
            return paths.toArray(new String[0]);
        }

        for (String path : sysPaths.split(":")) {

            if (!path.endsWith("/")) {
                path = path + '/';
            }

            if (!paths.contains(path)) {
                paths.add(path);
            }
        }

        return paths.toArray(new String[0]);
    }

}
