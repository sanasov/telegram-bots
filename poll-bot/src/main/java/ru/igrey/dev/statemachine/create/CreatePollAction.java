package ru.igrey.dev.statemachine.create;

/**
 * Created by sanasov on 04.04.2017.
 */
public interface CreatePollAction {

    void applyToPoll(String incomingText);

}
