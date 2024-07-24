package io.github.chenyilei2016.ac.core.file;

import java.io.Closeable;
import java.io.InputStream;

/**
 * @author chenyilei
 */
public interface FileWriter extends Closeable {

    void write(DataGroup fileData);

    InputStream finish();

}
