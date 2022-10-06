package chainable;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChainableTest {

    @Test
    public void testChainable() {
        var dummyLogic = new DummyLogic(20);
        Chainable<Integer, String> foo = Chainable.of(17)
                .andThen(this::foobar)
                .orElse(dummyLogic::process)
                .checkResult("Nothing");

        assertThat(foo.getReason())
                .isPresent()
                .contains("Nothing");

        Chainable<String, String> bar = foo
                .orElse(it -> Optional.of("meh")) // Recovers from previous failure
                .andThen(this::tripletize)
                .orElse(msg -> Optional.of(msg + " " + msg));

        assertThat(bar.getReason())
                .isEmpty();

        assertThat(bar.toOptional())
                .contains("meh meh meh")
                .contains(bar.expect());

    }

    private Optional<String> foobar(Integer i) {
        return Optional.of(i)
                .filter(it -> it < 10)
                .map(x -> "hello");
    }

    private Optional<String> tripletize(String msg) {
        return Optional.of(msg)
                .filter(it -> it.length() == 3)
                .map(it -> String.format("%s %s %s", it, it, it));
    }
}