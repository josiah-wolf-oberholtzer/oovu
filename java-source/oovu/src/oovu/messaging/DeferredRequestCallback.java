package oovu.messaging;

import com.cycling74.max.Executable;

public class DeferredRequestCallback implements Executable {
    public final MessagePasser message_passer;
    public final Request request;

    public DeferredRequestCallback(MessagePasser message_passer, Request request) {
        this.message_passer = message_passer;
        this.request = request;
    }

    @Override
    public void execute() {
        this.message_passer.handle_request(this.request);
    }
}
