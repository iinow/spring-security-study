package io.security.basicsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @PreAuthorize("hasRole('d')")
    public String getMain() {
        return "dfkdfjd";
    }
}
