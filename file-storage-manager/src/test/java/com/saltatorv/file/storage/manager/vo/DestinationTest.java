package com.saltatorv.file.storage.manager.vo;

import com.saltatorv.file.storage.manager.vo.Destination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DestinationTest {

    Destination destination;
    Destination resultDestination;

    @Test
    @DisplayName("Can resolve absolute destination properly")
    public void canResolveAbsoluteDestinationProperly() {
        // given
        createDestination("/tmp");

        // when
        resolve("test.txt");

        // then
        assertPathIsEqualTo("/tmp/test.txt");

    }

    @Test
    @DisplayName("Can resolve relative destination properly")
    public void canResolveRelativeDestinationProperly() {
        // given
        createDestination("tmp");

        // when
        resolve("test.txt");

        // then
        assertPathIsEqualTo("tmp/test.txt");

    }

    @Test
    @DisplayName("Can compare two same destinations pointing to same path")
    public void canCompareTwoSameDestinationsPointingToSamePath() {
        // given
        var first = createDestination("tmp/test/data.txt");
        var second = createDestination("tmp/test/data.txt");

        // when
        var result = compare(first, second);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("Can not compare two same destinations pointing to same path")
    public void canNotCompareTwoSameDestinationsPointingToSamePath() {
        // given
        var first = createDestination("tmp/test/data_1.txt");
        var second = createDestination("tmp/test/data_2.txt");

        // when
        var result = compare(first, second);

        // then
        assertFalse(result);
    }

    private Destination createDestination(String destination) {
        this.destination = new Destination(destination);
        return this.destination;
    }

    private void resolve(String path) {
        this.resultDestination = destination.resolve(path);
    }

    private boolean compare(Destination first, Destination second) {
        return first.equals(second);
    }

    private void assertPathIsEqualTo(String expectedPath) {
        expectedPath = expectedPath.replace("/", java.io.File.separator);
        expectedPath = expectedPath.replace("\"", java.io.File.separator);
        assertEquals(Path.of(expectedPath), resultDestination.getDestination());
    }
}
