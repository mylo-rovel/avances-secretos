import { Evento, Usuario, Secuencia, Simulador } from "~/utils/classes";

export const state = () => ({
    counter:0, // just for knowing how to use this store xd

    listaEventos: [], // => Evento []
    
    usuarioActual: null,
    secuenciaSeleccionada: null,
    simuladorSeleccionado: null,
    listaUsuarios: [],
    listaSecuencias: [],
    listaSimuladores: [],
});


export const actions = {

}

export const mutations = {
    // just for knowing how to use this store xd
    increase_counter(state, increment = 1) {
        state.counter += increment;
    },


    addEvento(state, indexToInsert) {
        const arr = state.listaEventos; // pass by reference just to write less XD;
        const newIntensidad = Math.floor(Math.random()*100);
        const newDuracion = Math.floor(Math.random()*100);
        const newEvento = new Evento(newIntensidad, newDuracion);
        if (indexToInsert === 0){
            state.listaEventos.unshift(newEvento);
            return true;
        }
        const leftArr = arr.slice(0,indexToInsert);
        const rightArr = arr.slice(indexToInsert);
        state.listaEventos = [...leftArr, newEvento, ...rightArr];
        return true;
    },

    getDuracionListaEventos() {
        return state.listaEventos.reduce((acc, evento) => acc + evento.getDuracion(), 0);
    }
}