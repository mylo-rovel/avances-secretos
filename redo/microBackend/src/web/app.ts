import cors, {CorsOptions} from "cors";
import morgan from "morgan";
import express, {Express} from "express";
import { ejecucionesRouter } from '../routes/ejecuciones/ejecuciones.router.js';

export const app: Express = express();

const whitelist = ["http://localhost:3000"];
const corsOptions = {
    origin: function(origin, callback) {
        if (!origin || whitelist.indexOf(origin) !== -1){
            callback(null, true);
        }
        else{
            callback(new Error("Not allowed by CORS :o"))
        }
    }
} as CorsOptions;

app.use(cors(corsOptions));


app.use(morgan("combined"));
app.use(express.json());


app.use('/api/ejecuciones', ejecucionesRouter);