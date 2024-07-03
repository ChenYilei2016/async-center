package io.github.chenyilei2016;

import org.springframework.boot.SpringApplication;

/**
 * @author chenyilei
 * @since 2024/07/03 17:19
 */

public class MainBootstrap {

    public static void main(String[] args) {
//        org.apache.poi.openxml4j.util.ZipSecureFile.setMinInflateRatio(0.001); //更宽松一点
        SpringApplication.run(MainBootstrap.class, args);
    }
}
