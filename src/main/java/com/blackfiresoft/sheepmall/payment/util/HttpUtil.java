package com.blackfiresoft.sheepmall.payment.util;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class HttpUtil {

    public static String parseRequestHeader(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to parse request header", e);
        }
    }
}
