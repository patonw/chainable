/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package chainable;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test void someLibraryMethodReturnsTrue() {
        Library classUnderTest = new Library();
        assertTrue(classUnderTest.someLibraryMethod(), "someLibraryMethod should return 'true'");
        assertThat(classUnderTest.someLibraryMethod())
                .isTrue();
    }
}