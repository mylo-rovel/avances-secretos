import { secuenciasMethods } from "~/store/secuenciasStateDir/secuenciasMethods.js";
import { secuenciasState } from "~/store/secuenciasStateDir/secuenciasState.js";

// El estado corresponde
export const state = () => ({
    // nunca debe ser igual a 0 o habrán errores
    duracionTokenDias: 6,

    urlApi: "http://localhost:8081/api",
    // urlApi: "http://192.168.43.73:8081/api",

    ...secuenciasState
});

export const mutations = {
    ... secuenciasMethods,
}