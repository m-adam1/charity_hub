package com.charity_hub.cases.internal.api.controllers;

import com.charity_hub.cases.internal.api.dtos.GetCasesRequest;
import com.charity_hub.cases.internal.application.queries.GetAllCases.GetCasesQueryResult;
import com.charity_hub.cases.shared.CasesClient;
import com.charity_hub.shared.domain.ILogger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllCasesController {

    private final CasesClient casesClient;
    private final ILogger logger;

    public GetAllCasesController(CasesClient casesClient, ILogger logger) {
        this.casesClient = casesClient;
        this.logger = logger;
    }

    @GetMapping("/v1/cases")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetCasesQueryResult> getCases(@ModelAttribute GetCasesRequest request) {

        GetCasesQueryResult response = casesClient.getAllCases(
                request.code(),
                request.tag(),
                request.content(),
                Math.max(request.offset(), 0),
                Math.min(Math.max(request.limit(), 1), 100)
        );
        return ResponseEntity.ok(response);
    }
}
