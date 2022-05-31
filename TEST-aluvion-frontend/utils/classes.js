export class Evento {
    #intensidad = 0;
    #duracion = 0;

    constructor(intensidad, duracion) {
        this.#intensidad = intensidad ;
        this.#duracion = duracion;
    }

    getValoresEvento() {
        return [this.#intensidad, this.#duracion];
    }
     
    getIntensidad() {
        return this.#intensidad;
    }

    getDuracion() {
        return this.#duracion;
    }
}

export class Usuario {

}

export class Secuencia {

}

export class Simulador {

}