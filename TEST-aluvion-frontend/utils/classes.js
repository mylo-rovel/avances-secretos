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

    setIntensidad(newIntensidad) {
        if (typeof newIntensidad !== "number" || newIntensidad < 0 || newIntensidad > 100){
            return false;
        }
        this.#intensidad = newIntensidad;
        return true;
    }

    setDuracion(newDuracion) {
        if (typeof newDuracion !== "number" || newDuracion < 0 || newDuracion > 9999){
            return false;
        }
        this.#duracion = newDuracion;
        return true;
    }
}

export class Usuario {
    #nombreUsuario = "";

    constructor(nombreUsuario) {
        this.#nombreUsuario = nombreUsuario;
    }

    getJsonUsuarion() {
        return JSON.stringify({
            nombre: this.#nombreUsuario
        });
    }
}

export class Secuencia {

}

export class Simulador {

}