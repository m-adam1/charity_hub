package com.charity_hub.cases.shared;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.charity_hub.cases.internal.application.queries.GetAllCases.GetCasesQueryResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.charity_hub.cases.shared.dtos.CaseDTO;
import com.charity_hub.cases.shared.dtos.ContributionDTO;

@FeignClient(name = "cases-service", url = "${cases-service.url}")
public interface CasesClient extends ICasesAPI {

    @Override
    @GetMapping("/api/cases/contributions/user/{userId}")
    CompletableFuture<List<ContributionDTO>> getUsersContributions(@PathVariable("userId") UUID userId);

    @Override
    @GetMapping("/api/cases/contributions/not-confirmed/{userId}")
    CompletableFuture<List<ContributionDTO>> getNotConfirmedContributions(@PathVariable("userId") UUID userId);

    @Override
    @GetMapping("/api/cases/contributions/users")
    CompletableFuture<List<ContributionDTO>> getUsersContributions(@RequestParam("userIds") List<UUID> usersIds);

    @Override
    @GetMapping("/api/cases/by-codes")
    CompletableFuture<List<CaseDTO>> getCasesByCodes(@RequestParam("codes") List<Integer> casesCodes);

    @Override
    @GetMapping("/v1/cases")
    GetCasesQueryResult getAllCases(
            @RequestParam(value = "code", required = false) Integer code,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    );
}
