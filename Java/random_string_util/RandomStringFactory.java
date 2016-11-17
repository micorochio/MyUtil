import java.util.Arrays;

/**
 * Created by zing on 2016/11/15.
 */
public class RandomStringFactory {

    public int length;
    public RandomStringFactory beforeFactory = null, afterFactory = null;
    public String beforeString = "", afterString = "";
    public char[] range;

    private RandomStringFactory() {
    }

    public RandomStringFactory(int length, RandomStringFactory beforeFactory, RandomStringFactory afterFactory, String beforeString, String afterString, char[] range) {
        this.length = length;
        this.beforeFactory = beforeFactory;
        this.afterFactory = afterFactory;
        this.beforeString = beforeString;
        this.afterString = afterString;
        this.range = range;
    }

    /**
     * 根据规则，创建随机字符串
     *
     * @return
     */
    public String makeItToString() {
        String now;
        if (beforeFactory != null) {
            beforeString += beforeFactory.makeItToString();
        }

        if (length > 0 && range != null) {
            char[] chars = new char[length];
            for (int i = 0; i < length; i++) {
                chars[i] = getRandomCase(range);
            }
            now = new String(chars);
        } else {
            now = "";
        }

        if (afterFactory != null) {
            afterString += afterFactory.makeItToString();
        }
        return beforeString + now + afterString;
    }

    /**
     * 从数组中获取一个随机的字符
     *
     * @param array
     * @return
     */
    public char getRandomCase(char[] array) {
        int len = array.length;
        return array[(int) Math.floor(Math.random() * len)];
    }

    /**
     * 拼接任意多个数组
     *
     * @param A
     * @return
     */
    public static char[] concatArray(char[]... A) {
        if (A == null) {
            return null;
        } else if (A.length == 1) {
            return A[0];
        }

        int length = 0;
        for (char[] c : A) {
            length += c.length;
        }
        char[] allInOne = new char[length];
        int step = 0;

        for (char[] c : A) {
            System.arraycopy(A, 0, allInOne, step, c.length);
            step += c.length;
        }
        return allInOne;
    }

    @Override
    public String toString() {
        return "RandomStringFactory:\n" +
                "length=" + length +
                "\n, beforeFactory=" + beforeFactory +
                "\n, afterFactory=" + afterFactory +
                "\n, beforeString='" + beforeString + '\'' +
                "\n, afterString='" + afterString + '\'' +
                "\n, range=" + Arrays.toString(range);
    }

    static class Builder {

        private int length;
        private RandomStringFactory beforeFactory = null, afterFactory = null;
        private String beforeString = "", afterString = "";
        private char[] range;

        private static Builder builder;

        public Builder setLength(int length) {
            builder.length = length;
            return builder;
        }

        public Builder setBeforeFactory(RandomStringFactory beforeFactory) {
            builder.beforeFactory = beforeFactory;
            return builder;
        }

        public Builder setAfterFactory(RandomStringFactory afterFactory) {
            builder.afterFactory = afterFactory;
            return builder;
        }

        public Builder setBeforeString(String beforeString) {
            builder.beforeString = beforeString;
            return builder;
        }

        public Builder setAfterString(String afterString) {
            builder.afterString = afterString;
            return builder;
        }

        public Builder setRange(char[]... range) {
            builder.range = concatArray(range);
            return builder;
        }

        public static Builder prepare(int length) {
            builder = new Builder();
            builder.length = length;
            return builder;
        }

        public RandomStringFactory build() {
            return new RandomStringFactory(builder.length, builder.beforeFactory, builder.afterFactory, builder.beforeString, builder.afterString, builder.range);
        }


    }


}
