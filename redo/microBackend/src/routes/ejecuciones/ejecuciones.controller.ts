import { Request, Response } from "express";
import { 
        getDatosSimulacion,
        getUltimosDatosSimulacion
 } from "../../model/ejecuciones.model.js";

export const httpGetDatosSimulacion = async (_:Request, res:Response) => {
    console.log("Sending Datos de simulacion");
    return res.status(200).json(getDatosSimulacion());
}


export const httpGetUltimosDatosSimulacion = async (_:Request, res:Response) => {
    console.log("Sending Ultimos Datos de simulacion");
    return res.status(200).json(getUltimosDatosSimulacion());
}
