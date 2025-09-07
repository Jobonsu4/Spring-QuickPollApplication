package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.exception.ResourceNotFoundException;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollRepository pollRepository;

    @Autowired
    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    // GET /polls (paged)
    @GetMapping
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
        Page<Poll> page = pollRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    // POST /polls
    @PostMapping
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(newPollUri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // GET /polls/{pollId}
    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long pollId) {
        Poll p = verifyPoll(pollId);
        return ResponseEntity.ok(p);
    }

    // PUT /polls/{pollId}
    @PutMapping("/{pollId}")
    public ResponseEntity<Void> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        poll.setId(pollId);
        pollRepository.save(poll);
        return ResponseEntity.ok().build();
    }

    // DELETE /polls/{pollId}
    @DeleteMapping("/{pollId}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return ResponseEntity.ok().build();
    }

    // Helper from Part 5.2
    private Poll verifyPoll(Long pollId) {
        Optional<Poll> opt = pollRepository.findById(pollId);
        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        return opt.get();
    }
}
