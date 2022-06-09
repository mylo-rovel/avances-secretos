import { Evento } from "~/utils/classes";

export const secuenciasStateMethods = {
    // añade una fila a la tabla
    addEvento(state, indexToInsert) {

        // let arrSecuencia = state.secuencias[state.currentSecuencia];

        // arr => sólo para obtener los elementos rapidamente.
        // para guardar el estado hay que usar el objeto state y sus propiedades
        const arr = state.listaEventos;
        const newEvento = new Evento(0, 0);
        
        if (indexToInsert === 0){
            state.listaEventos.unshift(newEvento);
            return true;
        }
        const leftArr = arr.slice(0,indexToInsert);
        const rightArr = arr.slice(indexToInsert);
        state.listaEventos = [...leftArr, newEvento, ...rightArr];
        // const leftArr = arrSecuencia.slice(0,indexToInsert);
        // const rightArr = arrSecuencia.slice(indexToInsert);
        // arrSecuencia = [...leftArr, newEvento, ...rightArr];
        return true;
    },

    removeEvento(state, indexToRemove) {
        // arr => sólo para obtener los elementos rapidamente.
        // para guardar el estado hay que usar el objeto state y sus propiedades
        const arr = state.listaEventos;
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
        
        if (attriToModify === "duracion") {
            state.listaEventos[rowIndex].setDuracion(newValue);
            return true;
        }
        state.listaEventos[rowIndex].setIntensidad(newValue);
        return true;
    },

    guardarListaEventos(state) {
        state.secuencias[state.currentSecuencia] = [... state.listaEventos];
    }
}