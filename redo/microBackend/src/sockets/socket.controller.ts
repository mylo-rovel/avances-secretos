import http from "http";
import { Server } from "socket.io"

export class SocketController {
    private socketServer: Server;

    constructor(httpServer:http.Server) {
        this.socketServer = new Server(httpServer, 
            { cors: { origin: '*', methods: ['GET', 'POST']} });
    }

    public socketListen () {
        console.log("Listening on sockets")
        this.socketServer.on('connection', (socket) => {  
            console.log(`${"#".padStart(40, "#")}\nA user has connected: ${socket.id}`);
            console.log(`Current amount of connected clients: ${this.socketServer.engine.clientsCount}\n\n\n`);

            //? Ending of connection
            socket.on("disconnect", (reason) => {
                console.log(`${socket.id} has disconnected. REASON:${reason}`);
                console.log(`# connected clients: ${this.socketServer.engine.clientsCount}\n`);
            })
        })
    }
}