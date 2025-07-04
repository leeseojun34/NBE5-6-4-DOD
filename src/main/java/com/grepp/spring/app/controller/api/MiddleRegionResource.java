package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.middle_region.model.MiddleRegionDTO;
import com.grepp.spring.app.model.middle_region.service.MiddleRegionService;
import com.grepp.spring.util.ReferencedException;
import com.grepp.spring.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/middleRegions", produces = MediaType.APPLICATION_JSON_VALUE)
public class MiddleRegionResource {

    private final MiddleRegionService middleRegionService;

    public MiddleRegionResource(final MiddleRegionService middleRegionService) {
        this.middleRegionService = middleRegionService;
    }

    @GetMapping
    public ResponseEntity<List<MiddleRegionDTO>> getAllMiddleRegions() {
        return ResponseEntity.ok(middleRegionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MiddleRegionDTO> getMiddleRegion(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(middleRegionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMiddleRegion(
            @RequestBody @Valid final MiddleRegionDTO middleRegionDTO) {
        final Long createdId = middleRegionService.create(middleRegionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMiddleRegion(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MiddleRegionDTO middleRegionDTO) {
        middleRegionService.update(id, middleRegionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMiddleRegion(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = middleRegionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        middleRegionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
