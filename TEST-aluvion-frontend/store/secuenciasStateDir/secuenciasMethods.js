export const secuenciasMethods = {
    // añade una fila a la tabla
    addEvento(state, indexToInsert) {
        // arr => sólo para obtener los elementos rapidamente.
        // para guardar el estado hay que usar el objeto state y sus propiedades
        const arr = state.listaEventos;
        const newEvento = {"intensidad": 0, "duracion":0};
        
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
        const valorMaximo = { "intensidad": 100, "duracion": 9999 };

        const {attriToModify, rowIndex} = dataObject;
        let {newValue} = dataObject;

        newValue = (newValue === "" || newValue === null) ? 0 : parseInt(newValue);
        // retornar false si el valor es mayor al máximo o negativo o la propiedad a cambiar no es la correcta
        if ( newValue > valorMaximo[attriToModify] || newValue < 0 || !(["intensidad", "duracion"].includes(attriToModify)) ) { return false; }

        state.listaEventos[rowIndex][attriToModify] = newValue;
        return true;
    },

    setListaEventos (state) {
        state.secuencias[state.currentSecuencia]['listaEventos'] = [... state.listaEventos];
    },

    // buscamos modificar el indice currentSecuencia para poder acceder a los eventos de cualquier valvula rapidamente
    // ? OJO: buscamos utilizar esta función en los botones de la vista crear simulacion
    setSecuenciaModificar(state, indiceValvula) {
        if (typeof indiceValvula !== 'number') {return false;}
        // si no es un número no podemos compararlo con 0
        if (indiceValvula < 0 || indiceValvula >= secuencias.length) {return false;}

        state.currentSecuencia = indiceValvula;
        // entregamos una copia del array guardado asociado a la válvula seccionada
        state.listaEventos = [... state.secuencias[state.currentSecuencia]];
        return true;
    },

    setCantidadSecuencias(state, cantValvulas) {
        const aux = []
        for (let i = 0; i < cantValvulas; i++) {
            aux.push({"listaEventos": []})
        }
        state.secuencias = [...aux];
        return true;
    }
}