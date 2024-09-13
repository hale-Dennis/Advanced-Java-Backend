package com.dennis.web.security.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "customactuator")
public class CombinedInfo {

    private final InfoEndpoint infoEndpoint;
    private final HealthEndpoint healthEndpoint;

    public CombinedInfo(InfoEndpoint infoEndpoint, HealthEndpoint healthEndpoint) {
        this.infoEndpoint = infoEndpoint;
        this.healthEndpoint = healthEndpoint;
    }

    @ReadOperation
    public Map<String, Object> combinedInfo() {
        Map<String, Object> combinedData = new HashMap<>();

        // Fetch info from /actuator/info
        Map<String, Object> info = infoEndpoint.info();
        combinedData.put("appInfo", info);

        // Fetch health details from /actuator/health
        HealthComponent healthComponent = healthEndpoint.health();

        if (healthComponent instanceof Health) {
            Health health = (Health) healthComponent;  // Safe cast to Health
            combinedData.put("healthStatus", health.getStatus());  // Get health status

            // Get health details if present
            if (health.getDetails() != null) {
                combinedData.put("healthDetails", health.getDetails());
            }
        } else {
            combinedData.put("healthStatus", healthComponent.getStatus()); // For non-Health types
        }

        combinedData.put("description", "this is a custom actuator endpoint that combines details from /health and /info endpoints" );

        return combinedData;
    }
}