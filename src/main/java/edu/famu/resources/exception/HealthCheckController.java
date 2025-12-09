package edu.famu.resources.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public Map<String, Object> checkDatabase() {
        Map<String, Object> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            response.put("status", "connected");
            response.put("database", metaData.getDatabaseProductName());
            response.put("version", metaData.getDatabaseProductVersion());
            response.put("url", metaData.getURL());
            response.put("username", metaData.getUserName());
            response.put("ssl", connection.getClientInfo("sslMode"));

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
}
