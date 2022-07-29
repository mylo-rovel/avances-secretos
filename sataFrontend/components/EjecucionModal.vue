<script>
    export default {
        name: 'EjecucionModal',
        props: ["isModalOpened", "ejecucionSeleccionada"],
        data() {
            return {
                listaSecuencias: [],
                listaEventos: [],
                indexSecuenciaSeleccionada: -1,
            }
        },
        mounted() {
            console.clear()
            this.listaSecuencias = this.ejecucionSeleccionada["secuencia_"];
            for (let i = 0; i < this.listaSecuencias.length; i++) {
                console.log(this.listaSecuencias[i]["idComponente_"]);
                console.log(this.listaSecuencias[i]["evento_"]);
                console.log('\n')
            }
        },
        methods: {
            closeModal() {
                this.$emit("changeModalToFalse");
            },
            setIndexSecuencia(indexSecuencia) {
                if (indexSecuencia === this.indexSecuenciaSeleccionada) {
                    this.listaEventos = [];
                    this.indexSecuenciaSeleccionada = -1;
                    return;
                }
                this.indexSecuenciaSeleccionada = indexSecuencia;
                this.listaEventos = this.listaSecuencias[indexSecuencia]["evento_"];
                // const listaEventosCruda = this.listaSecuencias[indexSecuencia]["evento_"];
                // console.log(JSON.parse(JSON.stringify(this.listaEventos)));
                // for (let i = 0; i < this.listaEventosCruda.length; i++){
                //     const evento = this.listaEventosCruda[i]
                //     const intensidad = evento["intensidad_"];
                //     const duracion = evento["duracion_"]
                //     this.listaEventos.push([intensidad, duracion])
                // }
                return true;
            }
            
    }
}

</script>

<template>
    <section class="modal-box">
        <article class="modal-body">
            <article class="top-ribbon-container"> 
                <h4>Ejecución {{ejecucionSeleccionada["id_"]}}</h4>
                <button class="close-button" @click="closeModal">X</button> 
            </article>
            <p>Fecha: {{ (new Date(this.ejecucionSeleccionada["fechaEjecucion_"])).toLocaleString() }}</p>
            <article class="modal-body-top-item">
                <p class="modal-body-title">Simulacion: </p>
                <p class="modal-body-text-row">{{ejecucionSeleccionada["nombre_"]}}</p>
            </article>
            <article class="modal-body-top-item">
                <p class="modal-body-title">Descripcion: </p>
                <p class="modal-body-text-block">{{ejecucionSeleccionada["descripcion_"]}}</p>
            </article>
            <article class="modal-body-top-item">
                <p class="modal-body-title">Equipo: </p>
                <p class="modal-body-text-row">{{ejecucionSeleccionada["nombreEquipo_"]}}</p>
            </article>
            <article class="modal-body-top-item">
                <p class="modal-body-title">Descripcion del equipo: </p>
                <p class="modal-body-text-block">{{ejecucionSeleccionada["descripcionEquipo_"]}}</p>
            </article>                        
        </article>

        <article class="modal-body modal-footer">
            <article class=""> 
                <h5>Secuencias usadas</h5>
            </article>
            <article>
                <article v-for="(secuencia, indexSecuencia) in listaSecuencias" :key="`secuenciaKey_${indexSecuencia}`"> 
                    <p class="secuencia-body-row" @click="() => setIndexSecuencia(indexSecuencia)" >Valvula id: {{secuencia["idComponente_"]}}</p>

                    <div v-if="indexSecuencia === indexSecuenciaSeleccionada">
                        <table class="tabla-eventos-secuencia-modal">
                            <tr class="">
                                <th>Intensidad</th>
                                <th>Duración</th>
                            </tr>
                            <tr v-for="(evento, rowIndex) in listaEventos" :key="`eventKey_${rowIndex}`">
                                <td class="event-element intensity-element"> {{evento["intensidad_"]}} </td>
                                <td class="event-element intensity-element"> {{evento["duracion_"]}} </td>
                            </tr>
                        </table>            

                    </div>
                </article>
            </article>
        </article>          
    </section>
</template>

<style>
.modal-box{
    position: relative;
    width: clamp(300px, 60vw, 700px);
    height: 80vh;
    border: 1px solid #ffffff;
    background-color: #ebe9e9;
    border-radius: 10px;
    margin: 0 auto;
    margin-top: 10vh;
    overflow-y: scroll;
}

.top-ribbon-container {
    width:100%;
    height:fit-content; 
    display: flex;
    justify-content: space-between;
    margin: 0 0rem 1rem 0;
}

.close-button {
    border:0;
    background-color: #d30000;
    padding: 0.2rem 0.6rem;
    width:fit-content;
    height:fit-content;
    border-radius: 50px;
    border: 1px solid black;
    margin: 0 1rem 0.2rem 0;
}

.close-button:hover {
    background-color: #ff0000;
}

.modal-body {
    margin-left:1rem;
    margin-top: 1rem;
}

.modal-body-top-item {
    margin: 1rem 0;
}

.modal-body-top-item p{
    width:fit-content;
    display: inline;
}

.modal-body-top-item .modal-body-text-block {
    display: block;
    width:90%;

}

.modal-body-top-item .modal-body-text-block,
.modal-body-top-item .modal-body-text-row {
    background-color: white;
    border: 1px solid rgba(0, 0, 0, 0.4);;
    border-radius: 4px;
    padding: 0 0.3rem;
}

.modal-footer {
    margin: 2rem 1rem;
    display: inherit;
}

.secuencia-body-row{
    width:100%;
    height:fit-content;
    background-color: white;
    border-radius: 5px;
    border: 1px solid black;
    padding-left: 0.5rem;
    cursor: pointer;
}

.secuencia-body-row:hover{
    background-color: black;
}

.tabla-eventos-secuencia-modal {
    width: fit-content;
    margin: 0 auto 1rem auto;
    max-height: 120px;
}

td, th {
    border-top: 1px solid black;
    border-bottom: 1px solid black;
    text-align: center;
}

</style>