package io.github.chenyilei2016.ac.core.common.enums;

import lombok.Getter;
import org.apache.poi.util.IOUtils;

import java.io.InputStream;

/**
 * @author chenyilei
 * @since 2024/07/24 19:38
 */
@Getter
public enum AcExcelTypeEnum {

    /**
     * csv
     */
    CSV(".csv", new byte[] {-27, -89, -109, -27}),

    /**
     * xls
     */
    XLS(".xls", new byte[] {-48, -49, 17, -32, -95, -79, 26, -31}),

    /**
     * xlsx
     */
    XLSX(".xlsx", new byte[] {80, 75, 3, 4});

    final String value;
    final byte[] magic;

    AcExcelTypeEnum(String value, byte[] magic) {
        this.value = value;
        this.magic = magic;
    }

    final static int MAX_PATTERN_LENGTH = 8;


    private static AcExcelTypeEnum recognitionExcelType(InputStream inputStream) throws Exception {
        // Grab the first bytes of this stream
        byte[] data = IOUtils.peekFirstNBytes(inputStream, MAX_PATTERN_LENGTH);
        if (findMagic(XLSX.magic, data)) {
            return XLSX;
        } else if (findMagic(XLS.magic, data)) {
            return XLS;
        }
        // csv has no fixed prefix, if the format is not specified, it defaults to csv
        return CSV;
    }

    private static boolean findMagic(byte[] expected, byte[] actual) {
        int i = 0;
        for (byte expectedByte : expected) {
            if (actual[i++] != expectedByte && expectedByte != '?') {
                return false;
            }
        }
        return true;
    }

}