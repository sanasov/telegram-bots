package ru.igrey.dev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.igrey.dev.domain.poll.AnswerOption;
import ru.igrey.dev.domain.poll.Poll;

/**
 * Created by sanasov on 10.04.2017.
 */
public class VoteService {
    private Poll poll;
    private String answer;
    private Long votedUserId;
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    public VoteService(Long votedUserId, Poll poll, String answer) {
        this.poll = poll;
        this.answer = answer;
        this.votedUserId = votedUserId;
    }

    public void vote() {
        removeOldUserVote();
        AnswerOption answerOption = poll.getAnswerOptions().stream()
                .filter(answerOpt -> answerOpt.answer().equals(answer))
                .findAny()
                .get();
        logger.info("vote: votedUserId " + votedUserId + ", pollId " + poll.getPollId());
        answerOption.addVotedUserId(votedUserId);
    }

    private void removeOldUserVote() {
        for (AnswerOption answerOption : poll.getAnswerOptions()) {
            answerOption.userIdSet().remove(votedUserId);
        }
    }

}
