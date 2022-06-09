import { secuenciasStateMethods } from "~/store/secuenciasStateMethods.js";

// El estado corresponde
export const state = () => ({
    urlApi: "http://localhost:8081/api",
    // solo para entender cómo funciona el store o 'estado global'
    counter:0,

    // => Evento [] ====> CORRESPONDE A LA TABLA DE EVENTOS
    // esta lista obtendrá una copia del array 'secuencias' usando el indice 'currentSecuencia' a través de un botón
    // si guardamos la lista de eventos => secuencias[currentSecuencia] = [... listaEventos]
    // importante pasar una COPIA para evitar el paso por referencia
    // si el usuario no guarda y retrocede, no guardamos elementos
    // si el usuario vuelve a entrar ya habiendo guardado una lista, este podrá ver nuevamente sus valores
    listaEventos: [],

    // => indice de la tabla evento a utilizar => usada para acceder a la válvula deseada: secuencias[currentSecuencia]
    //! => su valor será modificado usando la funcion 'setSecuenciaModificar'
    currentSecuencia: 0,
    
    // Evento [][] => array de listaEventos. Cada elemento es una válvula
    secuencias: [
        [],[],[]
    ],

    secuenciasFormateadas: [],
});

// funciones asincronas
// todo: obtener la cantidad de valvulas de un simulador para preparar la lista secuencias
// todo: utilizar el indice 'currentSecuencia' para acceder a cada válvula (el boton de acceso a la
// todo: tabla de eventos debe alterar este valor) de forma rápida
export const actions = {

}

export const mutations = {
    // solo para entender cómo funciona el store o 'estado global'
    increase_counter(state, increment = 1) {
        state.counter += increment;
    },
    
    // esta sintaxis indica que estamos entregando las funciones del objeto secuenciasStateMethods
    // importado desde el archivo '~/store/secuenciasStateMethods.js' para ocuparlas dentro de este
    // objeto que manipula el estado. así mantenemos separadas las funciones según su pertinencia
    ... secuenciasStateMethods,
    
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

    // obtenemos una estructura 3d de los eventos => 
    // [   [ eventoArr1Val1, eventoArr2Val1, ...],          SECUENCIA 1
    //     [ eventoArr1Val2, eventoArr2Val2, ...],          SECUENCIA 2
    //     [ eventoArr1Val3, eventoArr2Val3, ...],          SECUENCIA 3
    //]
    // eventoArr1Val1 = [24, 34] => [intensidad, duracion]
    // EL PORQUÉ DE ESTO RADICA EN QUE NO SE ME OCURRIÓ CÓMO ENVIAR LOS OBJETOS EVENTOS
    // ASÍ TAL CUAL SE CREAN EN JS
    formatearSecuencias(state) {
        const valvulas = [];
        for (let i=0; i < state.secuencias.length; i++) {        
            let secFormateada = [];
            for (let j=0; j < state.secuencias[i].length; j++){
                // getValoresEventos: [int, int]
                const eventoArr = state.secuencias[i][j].getValoresEvento();
                secFormateada.push(eventoArr);
            }
            valvulas.push(secFormateada);
        }
        state.secuenciasFormateadas = [... valvulas];
        return state.secuenciasFormateadas;
    }
}