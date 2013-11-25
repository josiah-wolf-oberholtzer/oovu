package oovu.messaging;

import com.cycling74.max.Executable;

public class DeferredResponseCallback implements Executable {
    public final MessagePasser message_passer;
    public final Response response;

    public DeferredResponseCallback(MessagePasser message_passer,
        Response response) {
        this.message_passer = message_passer;
        this.response = response;
    }

    @Override
    public void execute() {
        this.message_passer.handle_response(this.response);
    }
}
