package com.saltatorv.file.storage.manager.validation;

import com.saltatorv.file.storage.manager.validation.Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ExtensionTest {

    private Extension extension;

    @BeforeEach
    public void setup() {
        extension = null;
    }

    @Test
    @DisplayName("Return true when extension is included in file name")
    public void returnTrueWhenExtensionIsIncludedInFileName() {
        //given
        createExtension(".png");

        //when
        var result = extensionIsIncludedIn("img.png");

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Return false when extension is not included in file name")
    public void returnFalseWhenExtensionIsNotIncludedInFileName() {
        //given
        createExtension(".png");

        //when
        var result = extensionIsIncludedIn("test.txt");

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("Extension without dot is converted to contain dot at start")
    public void extensionWithoutDotIsConvertedToContainDotAtStart() {
        //given
        createExtension("png");

        //when
        var result = extensionIsIncludedIn("png");

        //then
        assertFalse(result);
    }

    private void createExtension(String extension) {
        this.extension = new Extension(extension);
    }

    private boolean extensionIsIncludedIn(String fileName) {
        return extension.isIncludedIn(Path.of(fileName));
    }

}
