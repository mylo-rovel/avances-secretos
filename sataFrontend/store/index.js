import { secuenciasMethods } from "~/store/secuenciasStateDir/secuenciasMethods.js";
import { secuenciasState } from "~/store/secuenciasStateDir/secuenciasState.js";
import { configuracionAppState } from "~/store/configuracionAppStateDir/configuracionAppState.js";

// El estado corresponde
export const state = () => ({
    ...configuracionAppState,
    ...secuenciasState
});

export const mutations = {
    ... secuenciasMethods,
}