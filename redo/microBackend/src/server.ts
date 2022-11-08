import http from "http";
import { app }  from "./web/app.js";
import { SocketController } from "./sockets/socket.controller.js";
import { populateMockDB } from './model/ejecuciones.model.js';

const httpServer:http.Server = http.createServer(app);
const socketServer = new SocketController(httpServer);

const PORT = process.env["PORT"] || 3001;

httpServer.listen(PORT, () => {
    console.log("Server ONLINE!");
    populateMockDB();
})
socketServer.socketListen();


