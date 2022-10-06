package chainable;

import com.sun.net.httpserver.Filter;

import java.util.Optional;
import java.util.function.Function;

public class Chainable<T, R> {
    private final T input;
    private final R result;
    private final String reason;

    private Chainable(T input, R result, String reason) {
        this.input = input;
        this.result = result;
        this.reason = reason;
    }

    public static <R> Chainable<?, R> of(R value) {
        return new Chainable<>(null, value, null);
    }

    public Chainable<T, R> orElse(Function<T, Optional<R>> block) {
        if (result != null) {
            return this;
        }

        var next = Optional.ofNullable(input)
                .flatMap(block)
                .orElse(null);

        return new Chainable<>(input, next, reason);
    }

    public <O> Chainable<R, O> andThen(Function<R, Optional<O>> block) {
        if (result == null) {
            return new Chainable<>(null, null, reason);
        }

        var next = block.apply(result)
                .orElse(null);

        return new Chainable<>(result, next, null);
    }

    public Chainable<T, R> checkResult(String reason) {
        if (this.reason != null || this.result != null) {
            return this;
        }

        return new Chainable<>(input, null, reason);
    }


    public boolean isPresent() {
        return result != null;
    }

    public boolean isEmpty() {
        return result == null;
    }

    public Optional<String> getReason() {
        return Optional.ofNullable(reason)
                .filter(it -> result == null);
    }

    public Optional<R> toOptional() {
        return Optional.ofNullable(result);
    }

    public R unwrap(R defaultValue) {
        if (result == null) {
            return defaultValue;
        }

        return result;
    }

    public R expect() {
        if (result == null) {
            throw new IllegalStateException(Optional.ofNullable(reason)
                    .orElse("Chainable result is empty"));
        }

        return result;
    }
}
