package ru.igrey.dev;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.Poll;

/**
 * Created by sanasov on 10.04.2017.
 */
public class VoteService {
    private Poll poll;
    private String answer;
    private Long votedUserId;


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
        answerOption.addVotedUserId(votedUserId);
    }

    private void removeOldUserVote() {
        for (AnswerOption answerOption : poll.getAnswerOptions()) {
            answerOption.userIdSet().remove(votedUserId);
        }
    }

}
