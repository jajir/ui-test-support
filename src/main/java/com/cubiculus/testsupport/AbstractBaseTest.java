package com.cubiculus.testsupport;

import com.google.common.base.Preconditions;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * All test should be extended from this test. here is configuration.
 *
 * @author jan
 *
 */
public abstract class AbstractBaseTest extends AbstractWebdriverTest {

    private final static String DEFAULT_SERVER = "http://localhost:8090/";
    private final static String SERVER_SYSTEM_PROPERTY = "server";
    private final static String SUPPORT_SERVER_SYSTEM_PROPERY = "suportServer";
    private final static String DEFAULT_SUPPORT_SERVER = "http://localhost:8092/";

    /**
     * Project relative path containing test data files.
     */
    public final static String TEST_DATA_FOLDER = "src/test/data";

    /**
     * Folder for temporary downloads
     */
    public final static String DOWNLOAD_FILE_PATH = Paths
            .get("target" + File.separator + "download").toAbsolutePath().toString();

    /**
     * Provide font-end server URL.
     *
     * @return
     */
    protected String getServerUrl() {
        return System.getProperty(SERVER_SYSTEM_PROPERTY, DEFAULT_SERVER);
    }

    /**
     * Provide support server URL.
     *
     * @return
     */
    protected String getSupportServerUrl() {
        return System.getProperty(SUPPORT_SERVER_SYSTEM_PROPERY, DEFAULT_SUPPORT_SERVER);
    }

    /**
     *
     * @param relativePath tests/src/test/data/->test-too-heavy.csv<- root je
     *                     tests/src/test/data/
     * @return File
     */
    public File getTestFileByName(final String relativePath) throws Exception {
        final File folder = new File(TEST_DATA_FOLDER);
        final ArrayList<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
        final String searchFor = TEST_DATA_FOLDER + "/" + relativePath;
        File file = null;
        for (int i = 0; i < files.size(); i++) {
            if (searchFor.equals(files.get(i).getPath())) {
                file = files.get(i);

            }
        }
        Preconditions.checkNotNull(file, "File not found.");
        return file;
    }

}
