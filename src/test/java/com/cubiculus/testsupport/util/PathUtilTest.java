package com.cubiculus.testsupport.util;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cubiculus.testsupport.util.PathUtil;

public class PathUtilTest {

    private PathUtil pathUtil = new PathUtil();

    private static Stream<Arguments> dataProviderForRemoveLastSlash() {
        return Stream.of(//
                arguments("hehe", "hehe"), //
                arguments("///", ""), //
                arguments("/hehe/", "/hehe")//
        );
    }

    @ParameterizedTest
    @MethodSource("dataProviderForRemoveLastSlash")
    public void testRemoveLastSlash(final String originalPath, final String expectedResult)
            throws Exception {
        final String result = pathUtil.removeLastSlash(originalPath);

        assertEquals(expectedResult, result, String
                .format("Expected string is '%s' but returned is '%s'", expectedResult, result));
    }

    private static Stream<Arguments> dataProviderForRemoveBeginningSlash() {
        return Stream.of(//
                arguments("hehe", "hehe"), //
                arguments("///", ""), //
                arguments("/hehe/", "hehe/")//
        );
    }

    @ParameterizedTest
    @MethodSource("dataProviderForRemoveBeginningSlash")
    public void testRemoveBeginnigSlash(final String originalPath, final String expectedResult)
            throws Exception {
        final String result = pathUtil.removeBeginnigSlash(originalPath);

        assertEquals(expectedResult, result, String
                .format("Expected string is '%s' but returned is '%s'", expectedResult, result));
    }

    private static Stream<Arguments> dataProviderForGetPathElementFromUrl() {
        return Stream.of(//
                arguments("https://90.cz/order/208/", 0, "order"), //
                arguments("https://90.cz/order/208/stul/zidle#pakun=wee&opo=99", 3, "zidle"), //
                arguments("https://90.cz/order/208/", 1, "208")//
        );
    }

    @ParameterizedTest
    @MethodSource("dataProviderForGetPathElementFromUrl")
    public void testGetPathElementFromUrl(final String originalPath, final Integer index,
            final String expectedResult) throws Exception {
        final String result = pathUtil.getPathElementFromUrl(originalPath, index);

        assertEquals(expectedResult, result, String
                .format("Expected string is '%s' but returned is '%s'", expectedResult, result));
    }

    private static Stream<Arguments> dataProviderForGetServerPart() {
        return Stream.of(//
                arguments("https://90.cz", "https://90.cz"), //
                arguments("http://90.cz:9898/order/208/", "http://90.cz:9898"), //
                arguments("https://90.cz/order/208/", "https://90.cz")//
        );
    }

    @ParameterizedTest
    @MethodSource("dataProviderForGetServerPart")
    public void testGetServerPart(final String originalPath, final String expectedResult)
            throws Exception {
        final String result = pathUtil.getServerPart(originalPath);

        assertEquals(expectedResult, result, String
                .format("Expected string is '%s' but returned is '%s'", expectedResult, result));
    }
}
