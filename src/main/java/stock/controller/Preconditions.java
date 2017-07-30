package stock.controller;

import java.util.function.Supplier;

public class Preconditions {

    public static void checkNotNull(Object o, Supplier<? extends RuntimeException> supplier) {
        if (o == null) {
            throw supplier.get();
        }
    }
}
