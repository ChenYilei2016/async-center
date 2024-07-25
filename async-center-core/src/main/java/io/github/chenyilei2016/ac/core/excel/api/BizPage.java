package io.github.chenyilei2016.ac.core.excel.api;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chenyilei
 */
public interface BizPage extends Serializable {

    Integer getNo();

    Map<String, String> getAttributes();
}
