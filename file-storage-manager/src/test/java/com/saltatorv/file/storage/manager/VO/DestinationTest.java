package com.saltatorv.file.storage.manager.VO;

import com.saltatorv.file.storage.manager.VO.Destination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private void createDestination(String destination) {
        this.destination = new Destination(destination);
    }

    private void resolve(String path) {
        this.resultDestination = destination.resolve(path);
    }

    private void assertPathIsEqualTo(String expectedPath) {
        expectedPath = expectedPath.replace("/", java.io.File.separator);
        expectedPath = expectedPath.replace("\"", java.io.File.separator);
        assertEquals(Path.of(expectedPath), resultDestination.getDestination());
    }
}
