package com.enicarthage.mobility.controller;

import com.enicarthage.mobility.dto.LigneBusDto;
import com.enicarthage.mobility.model.LigneBus;
import com.enicarthage.mobility.service.LigneBusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lines")
public class LigneBusController {
    private final LigneBusService service;

    public LigneBusController(LigneBusService service) {
        this.service = service;
    }

    // GET /api/lines
    @GetMapping
    public List<LigneBusDto> all() {
        return service.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // GET /api/lines/{id}
    @GetMapping("/{id}")
    public LigneBusDto get(@PathVariable Long id) {
        return toDto(service.findById(id));
    }

    // POST /api/lines
    @PostMapping
    public ResponseEntity<LigneBusDto> create(@Valid @RequestBody LigneBusDto dto) {
        LigneBus entity = fromDto(dto);
        LigneBus saved = service.save(entity);
        return ResponseEntity.created(URI.create("/api/lines/" + saved.getId())).body(toDto(saved));
    }

    // PUT /api/lines/{id}
    @PutMapping("/{id}")
    public LigneBusDto update(@PathVariable Long id, @Valid @RequestBody LigneBusDto dto) {
        LigneBus existing = service.findById(id);
        existing.setCode(dto.getCode());
        existing.setNom(dto.getNom());
        existing.setHorairesJson(dto.getHorairesJson());
        return toDto(service.save(existing));
    }

    // DELETE /api/lines/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Extra: GET /api/lines/{id}/schedule -> retourne horairesJson parsé (ici renvoie la string)
    @GetMapping("/{id}/schedule")
    public ResponseEntity<String> schedule(@PathVariable Long id) {
        LigneBus l = service.findById(id);
        return ResponseEntity.ok(l.getHorairesJson() == null ? "[]" : l.getHorairesJson());
    }

    // mapping helpers
    private LigneBusDto toDto(LigneBus l) {
        LigneBusDto d = new LigneBusDto();
        d.setId(l.getId());
        d.setCode(l.getCode());
        d.setNom(l.getNom());
        d.setHorairesJson(l.getHorairesJson());
        return d;
    }

    private LigneBus fromDto(LigneBusDto d) {
        LigneBus l = new LigneBus();
        l.setCode(d.getCode());
        l.setNom(d.getNom());
        l.setHorairesJson(d.getHorairesJson());
        return l;
    }
}
