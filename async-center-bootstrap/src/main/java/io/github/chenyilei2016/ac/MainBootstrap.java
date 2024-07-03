package io.github.chenyilei2016.ac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyilei
 * @since 2024/07/03 17:19
 */

@SpringBootApplication
public class MainBootstrap {

    public static void main(String[] args) {
//        org.apache.poi.openxml4j.util.ZipSecureFile.setMinInflateRatio(0.001); //更宽松一点

//        System.setProperty("")

        SpringApplication.run(MainBootstrap.class, args);
    }
}
