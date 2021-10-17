package com.cubiculus.testsupport.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.base.Strings;

public class PathUtil {

    private final static String SLASH = "/";

    public String removeLastSlash(String str) {
        while (str.endsWith(SLASH)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String removeBeginnigSlash(String str) {
        while (str.startsWith(SLASH)) {
            str = str.substring(1);
        }
        return str;
    }

    public String getPathPart(final String fullHttpPath) {
        try {
            final URL url = new URL(fullHttpPath);
            return url.getPath();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public String getPathElementFromUrl(final String fullHttpPath, final int index) {
        String path = getPathPart(fullHttpPath);
        path = removeLastSlash(path);
        path = removeBeginnigSlash(path);
        final String[] parts = path.split(SLASH);
        return parts[index];
    }

    public String getServerPart(final String fullHttpPath) {
        try {
            final URL url = new URL(fullHttpPath);
            final String file = url.getFile();
            String returnPath;
            if (Strings.isNullOrEmpty(file)) {
                returnPath = fullHttpPath;
            } else {
                returnPath = fullHttpPath.substring(0, fullHttpPath.indexOf(file));
            }
            return returnPath;
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
