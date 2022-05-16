package com.example.demo.config.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Lav Kherwa");  // here we can pull the userInfo from JWT and pass
    }
}
