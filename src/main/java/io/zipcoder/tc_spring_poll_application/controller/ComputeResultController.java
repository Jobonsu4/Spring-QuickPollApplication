package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.dto.OptionCount;
import io.zipcoder.tc_spring_poll_application.dto.VoteResult;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ComputeResultController {

    private final VoteRepository voteRepository;

    @Autowired
    public ComputeResultController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    // GET /computeresult?pollId=1
    @GetMapping("/computeresult")
    public ResponseEntity<VoteResult> computeResult(@RequestParam Long pollId) {

        Iterable<Vote> allVotes = voteRepository.findVotesByPoll(pollId);

        Map<Long, Integer> counts = new LinkedHashMap<>();
        int total = 0;
        for (Vote v : allVotes) {
            if (v.getOption() == null || v.getOption().getId() == null) continue;
            counts.merge(v.getOption().getId(), 1, Integer::sum);
            total++;
        }

        List<OptionCount> resultList = new ArrayList<>();
        counts.forEach((optionId, cnt) -> {
            OptionCount oc = new OptionCount();
            oc.setOptionId(optionId);
            oc.setCount(cnt);
            resultList.add(oc);
        });

        VoteResult vr = new VoteResult();
        vr.setTotalVotes(total);
        vr.setResults(resultList);

        return ResponseEntity.ok(vr);
    }
}
