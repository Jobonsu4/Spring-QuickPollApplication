package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/polls")
public class VoteController {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    // POST /polls/{pollId}/votes
    @PostMapping("/{pollId}/votes")
    public ResponseEntity<Void> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        vote = voteRepository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    // GET /polls/votes  (all votes)
    @GetMapping("/votes")
    public Iterable<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // GET /polls/{pollId}/votes  (✅ fixed: use findVotesByPoll, not findById)
    @GetMapping("/{pollId}/votes")
    public Iterable<Vote> getVotesByPoll(@PathVariable Long pollId) {
        return voteRepository.findVotesByPoll(pollId);
    }
}
