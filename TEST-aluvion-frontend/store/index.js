import { secuenciasStateMethods } from "~/store/secuenciasStateMethods.js";

// El estado corresponde
export const state = () => ({
    // solo para entender cómo funciona el store o 'estado global'
    counter:0,
    urlApi: "http://192.168.43.73:8081/api",
    //192.168.43.73 emilio
    //10.20.109.227 red alumnos

    

    // EL PROPOSITO DE ESTE ARRAY ES FACILITAR EL GUARDADO DE LAS FILAS SIMPLEMENTE USANDO 1 ARRAY A LA VEZ
    // esta lista obtendrá una copia del contenido del array secuencias en el indice 'currentSecuencia'
    // a través de un botón.
    // importante pasar una COPIA para evitar el paso por referencia
    // si el usuario no guarda y retrocede, no guardamos elementos
    // si el usuario vuelve a entrar ya habiendo guardado una lista, este podrá ver nuevamente sus valores
    // EJEMPLO DE LO QUE SERÍA listaEventos:
    // [ 
    //     {"intensidad": 5, "duracion":9},
    //     {"intensidad": 7, "duracion":7},
    //     {"intensidad": 1, "duracion":5}
    // ]
    listaEventos: [],

    // => indice de la tabla evento a utilizar =>
    // usada para acceder a la válvula deseada: secuencias[currentSecuencia]['listaEventos']
    //! => su valor será modificado usando la funcion 'setSecuenciaModificar'
    currentSecuencia: 0,
    
    // secuencias: [
    //     {"listaEventos": [ 
    //         {"intensidad": 5, "duracion":9},
    //         {"intensidad": 7, "duracion":7},
    //         {"intensidad": 1, "duracion":5}
    //     ]},
    //     {"listaEventos": [ 
    //         {"intensidad": 5, "duracion":9},
    //         {"intensidad": 7, "duracion":7},
    //         {"intensidad": 1, "duracion":5}
    //     ]},
    //     {"listaEventos": [ 
    //         {"intensidad": 5, "duracion":9},
    //         {"intensidad": 7, "duracion":7},
    //         {"intensidad": 1, "duracion":5}
    //     ]}
    // ]
    // DESPUÉS OBTENDREMOS LA CANTIDAD DE VALVULAS PARA INICIALIZAR EL ARRAY CON UNA CANTIDAD DE OBJETOS
    // EQUIVALENTE AL VALOR OBTENIDO
    secuencias: [
        {"listaEventos": []}, 
        {"listaEventos": []}, 
        {"listaEventos": []}
    ],
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

}