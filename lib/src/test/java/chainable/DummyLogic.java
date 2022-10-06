package chainable;

import java.util.Optional;

public class DummyLogic {
    private final int threshold;

    public DummyLogic(int threshold) {
        this.threshold = threshold;
    }

    public Optional<String> process(int input) {
        return Optional.of(input)
                .filter(i -> i > 20)
                .map(x -> "goodbye");
    }
}
