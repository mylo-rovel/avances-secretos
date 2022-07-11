import { secuenciasMethods } from "~/store/secuenciasStateDir/secuenciasMethods.js";
import { secuenciasState } from "~/store/secuenciasStateDir/secuenciasState.js";

// El estado corresponde
export const state = () => ({

    urlApi: "http://192.168.43.73:8081/api",
    //192.168.43.73 emilio
    ...secuenciasState
});

export const actions = {
}

export const mutations = {
    ... secuenciasMethods,
}