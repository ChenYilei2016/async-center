package io.github.chenyilei2016.ac.core.file;

import java.io.Closeable;
import java.io.InputStream;

/**
 * @author chenyilei
 */
public interface FileReader extends Closeable {

    void read(InputStream inputStream);

    DataGroup finish();
}
