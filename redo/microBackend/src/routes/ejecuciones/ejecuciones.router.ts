import express, {Router} from "express";
import { 
    httpGetDatosSimulacion,
    httpGetUltimosDatosSimulacion
 } from "./ejecuciones.controller.js";

export const ejecucionesRouter: Router = express.Router();

ejecucionesRouter.get("/", httpGetDatosSimulacion);
ejecucionesRouter.get("/ultimos", httpGetUltimosDatosSimulacion);
