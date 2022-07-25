export const secuenciasState = {
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
    secuencias: [],  
}