package oovu.clients;

import oovu.messaging.Response;

import com.cycling74.max.Executable;

public class ServerClientCreationCallback implements Executable {

    public final ServerClient server_client;

    public ServerClientCreationCallback(ServerClient server_client) {
        this.server_client = server_client;
    }

    @Override
    public void execute() {
        Response response = this.server_client.get_server()
            .generate_dumpmeta_response();
        this.server_client.handle_response(response);
    }
}
