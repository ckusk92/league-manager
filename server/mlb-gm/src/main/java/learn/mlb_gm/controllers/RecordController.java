package learn.mlb_gm.controllers;

import learn.mlb_gm.domain.RecordService;
import learn.mlb_gm.domain.Result;
import learn.mlb_gm.models.Record;
import learn.mlb_gm.models.response_objects.RecordWithTeam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService service) {this.service = service;}

    @GetMapping
    public List<Record> findAll() {return service.findAll();}

    @GetMapping("/standings")
    public List<RecordWithTeam> standings() {return service.getStandings();}

    @GetMapping("/{userTeamId}")
    public ResponseEntity<Record> findForTeam(@PathVariable int userTeamId) {
        Record record = service.findForTeam(userTeamId);
        if(record == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(record);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Record record) {
        Result<Record> result = service.add(record);

        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{userTeamId}")
    public ResponseEntity<Object> update(@PathVariable int userTeamId, @RequestBody Record record) {
        if(userTeamId != record.getUserTeamId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Record> result = service.update(record);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userTeamId}")
    public ResponseEntity<Void> deleteForTeam(@PathVariable int userTeamId) {
        if(service.deleteForTeam(userTeamId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
