package model.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BookType {
    Action, Biography, Crime, Fantasy, Romance, SciFi;

    private static final List<BookType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BookType randomBookType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
