package com.blackfiresoft.sheepmall.util;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件文本加密工具类
 */
@Configuration
public class TextEncode {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        return new DESEncrypt();
    }

    public static class DESEncrypt implements StringEncryptor {

        @Value("${jasypt.encryptor.password}")
        private String password;

        private PBEConfig config() {
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword(password);
            config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
            config.setKeyObtentionIterations("500");
            config.setPoolSize("1");
            config.setProviderName("SunJCE");
            config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
            config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
            config.setStringOutputType("base64");
            return config;
        }

        @Override
        public String encrypt(String s) {
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(config());
            return encryptor.encrypt(s);
        }

        @Override
        public String decrypt(String s) {
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
            encryptor.setConfig(config());
            return encryptor.decrypt(s);
        }
    }

}
