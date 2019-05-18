package apm.muei.distancenevermatters.Server;

import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketUtils {

    private static final String URL = ServerActions.URL;
    private Socket socket;
    private static SocketUtils instance;


    private SocketUtils(){
        try {
            socket = IO.socket(URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static SocketUtils getInstance() {
       if (instance == null){
           instance = new SocketUtils();
       }
       return instance;
    }

    public void connect(){
        if (!this.socket.connected()){
            socket.connect();
        }
    }

    public void join(long code){
        socket.emit(ServerActions.JOIN, code);
    }

    public void leave(long code){
        socket.emit(ServerActions.LEAVE, code);
    }

    public void disconnect(){
        socket.disconnect();
    }

    public void sendMovement(Movement movement, long code){
        String jsonMovement = new Gson().toJson(movement);
        socket.emit(ServerActions.SENDMOVEMENT, jsonMovement, code);
    }

    public Socket getSocket(){
        return this.socket;
    }

}
