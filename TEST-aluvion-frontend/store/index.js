// import { Evento, Usuario, Secuencia, Simulador } from "~/utils/classes";
import { Evento, Usuario } from "~/utils/classes";

export const state = () => ({
    counter:0, // just for knowing how to use this store xd

    listaEventos: [], // => Evento []
    
    usuarioActual: new Usuario(""), // sólo por ahora
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
        // const newIntensidad = Math.floor(Math.random()*100);
        // const newDuracion = Math.floor(Math.random()*100);
        // const newEvento = new Evento(newIntensidad, newDuracion);
        const newEvento = new Evento(0, 0);
        if (indexToInsert === 0){
            state.listaEventos.unshift(newEvento);
            return true;
        }
        const leftArr = arr.slice(0,indexToInsert);
        const rightArr = arr.slice(indexToInsert);
        state.listaEventos = [...leftArr, newEvento, ...rightArr];
        return true;
    },

    removeEvento(state, indexToRemove) {
        const arr = state.listaEventos; // pass by reference just to write less XD;
        if (indexToRemove === 0){
            state.listaEventos.shift();
            return true;
        }
        const leftArr = arr.slice(0,indexToRemove);
        const rightArr = arr.slice(indexToRemove+1);
        state.listaEventos = [...leftArr, ...rightArr];
        return true;
    },

    setNuevoValorEvento(state, dataObject){
        const {attriToModify, rowIndex} = dataObject;
        let {newValue} = dataObject;
        newValue = (newValue === "" || newValue === null) ? 0 : parseInt(newValue);
        if (newValue < 0) { return false; }
        
        const arr = state.listaEventos;
        if (attriToModify === "duracion") {
            arr[rowIndex].setDuracion(newValue);
            return true;
        }
        arr[rowIndex].setIntensidad(newValue);
        return true;
    }
}