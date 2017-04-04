package ru.igrey.dev.statemachine;

import ru.igrey.dev.domain.Poll;

/**
 * Created by sanasov on 04.04.2017.
 */
public interface CreatePollAction {

    Poll applyToPoll(Poll poll);

}
