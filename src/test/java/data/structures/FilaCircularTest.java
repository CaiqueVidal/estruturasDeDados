package data.structures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FilaCircularTest {

    @Test
    void initializationOk() {
        int size = random(10);

        Fila<Integer> fila = new FilaCircularImpl<>(size);

        assertEquals(fila.size(), size);
        assertTrue(fila.isEmpty());
        assertNull(fila.front());
    }

    @Test
    void initializationZeroSizeNotAllowed() {
        assertThrows(RuntimeException.class, () -> new FilaCircularImpl<>(0));
    }

    @Test
    void initializationNegativeSizeNotAllowed() {
        final int size = -random(10);

        assertThrows(RuntimeException.class, () -> new FilaCircularImpl<>(size));
    }

    @ParameterizedTest
    @MethodSource("enqueueArguments")
    void enqueueOk(int size, int [] elements, int expectedFront, boolean expectedFull) {
        Fila<Integer> fila = new FilaCircularImpl<>(size);

        Arrays.stream(elements).forEach(fila::enqueue);

        assertEquals(fila.front(), expectedFront);
        assertEquals(fila.isFull(), expectedFull);
        assertFalse(fila.isEmpty());
    }

    static Stream<Arguments> enqueueArguments() {
        int elementOne = random();
        int elementTwo = random();

        return Stream.of(
                arguments(2, new int[] {elementOne, elementTwo}, elementOne, true),
                arguments(3, new int[] {elementOne, elementTwo}, elementOne, false)
        );
    }

    @Test
    void enqueueFailedFullStack() {
        int elementOne = random();
        int elementTwo = random();
        Fila<Integer> fila = new FilaCircularImpl<>(1);
        fila.enqueue(elementOne);

        assertThrows(RuntimeException.class, () -> fila.enqueue(elementTwo));
    }

    @ParameterizedTest
    @MethodSource("dequeueArguments")
    void dequeueOk(Fila<Integer> filaToBeUsed, int expectedElement, boolean expectedEmpty) {
        var actualElement = filaToBeUsed.dequeue();

        assertThat(actualElement).isEqualTo(expectedElement);
        assertThat(filaToBeUsed.isEmpty()).isEqualTo(expectedEmpty);
        assertThat(filaToBeUsed.isFull()).isFalse();
    }

    static Stream<Arguments> dequeueArguments() {
        int elementOne = random();
        int elementTwo = random();

        Fila<Integer> filaOneElementFull = new FilaCircularImpl<>(1);
        filaOneElementFull.enqueue(elementOne);

        Fila<Integer> filaTwoElementsFull = new FilaCircularImpl<>(2);
        filaTwoElementsFull.enqueue(elementOne);
        filaTwoElementsFull.enqueue(elementTwo);

        Fila<Integer> filaTwoElementsNotFull = new FilaCircularImpl<>(2);
        filaTwoElementsNotFull.enqueue(elementOne);

        return Stream.of(
                arguments(filaOneElementFull, elementOne, true),
                arguments(filaTwoElementsFull, elementOne, false)
        );
    }

    @Test
    void dequeueFailedEmptyStack() {
        int size = random(10);
        Fila<Integer> fila = new FilaCircularImpl<>(size);

        assertThrows(RuntimeException.class, fila::dequeue);
    }

    @Test
    void verifyQueue() {
        int elementOne = random();
        int elementTwo = random();
        int elementThree = random();

        Fila<Integer> fila = new FilaCircularImpl<>(2);
        fila.enqueue(elementOne);
        fila.enqueue(elementTwo);

        assertTrue(fila.isFull());
        assertEquals(fila.front(), elementOne);
        assertEquals(fila.dequeue(), elementOne);

        fila.enqueue(elementThree);

        assertEquals(fila.front(), elementTwo);
        assertEquals(fila.dequeue(), elementTwo);

        assertEquals(fila.front(), elementThree);
        assertEquals(fila.dequeue(), elementThree);

        assertTrue(fila.isEmpty());
    }

    private static int random() {
        return new Random().nextInt();
    }

    private static int random(int limit) {
        var number = 0;
        while (number == 0) {
            number = new Random().nextInt(limit+1);
        }
        return number;
    }
}
