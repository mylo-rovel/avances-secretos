<script lang="js">

    import Vue from 'vue'
    import {mapState, mapMutations} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import SecuenciaButton from '~/components/SecuenciaButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import Modal from '~/components/Modal.vue'

    import { 
        getEquiposDesplegables, 
        getValvulasDesplegables, 
        getValvulasDict } from '~/utils/utility_functions';
    import { setSecuenciaModificar, setCantidadSecuencias } from '~/store/secuenciasStateDir/secuenciasMethods';

    export default Vue.extend({
        name: "RegistrarSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton, Modal, SecuenciaButton},
        data() {
            return {
                "cancelbutton": "cancelar",
                "submitbutton": "guardar",
                "infoContenido": "Simulación registrada :D",
                "nombreSecuencia": "Configurar Secuencia",

                equiposDisponibles:{},
                dictValvulasDisponibles:{},
                valvulasDisponibles:[],

                equipoSeleccionado:"",
                nombreSimulacionInput:"",
                descripcionInput:"",

                isGeneradorEventosOpen: false,

            };
        },
        head(){
            return{
                title: "Registrar Simulación - Sistema de Alerta Temprana Aluvional",
                meta: [{
                    name: "registrarSimulacion",
                    content: "Registrar simulación"
                }],
            }
        },
        computed: mapState(["urlApi", "secuencias"]) ,
        
        async mounted(){
            checkIfUserShouldBeHere(["OPERADOR"]);

            const JWTtoken = window.localStorage.getItem("token");
            const post_config = { 
                method: 'get', 
                headers: {'authorization': JWTtoken}
            };

            const urlEquipos = `${this.urlApi}/equipos/`;
            const serverResponse = await fetch(urlEquipos, post_config).catch(err => err);
            if (serverResponse instanceof Error) {
                alert("❌ No hay respuesta del server ❌");
                return;
            }
            const listaEquiposCrudos = await serverResponse.json();
            this.equiposDisponibles = getEquiposDesplegables(listaEquiposCrudos);
           
        },
        methods: {
             ...mapMutations(["setSecuenciaModificar", "setCantidadSecuencias"]),

            async getValvulas(){
                const JWTtoken = window.localStorage.getItem("token");
                const post_config = { 
                    method: 'get', 
                    headers: {'authorization': JWTtoken}
                };
                const idEquipoSeleccionado = this.equiposDisponibles[this.equipoSeleccionado]["idEquipo"][0];
                const urlValvulaEquipo = `${this.urlApi}/equipos/valvulas/${idEquipoSeleccionado}`;

                const dataValvula = await fetch(urlValvulaEquipo, post_config).catch(err => err);
                if (dataValvula instanceof Error) {
                    alert("❌ Error pidiendo datos al servidor ❌");
                    return;
                }
                const listaValvulasCrudas = await dataValvula.json()
                const dictValvulasEquipo = getValvulasDict(listaValvulasCrudas);

                this.valvulasDisponibles = [...Object.keys(dictValvulasEquipo)]; //borrar

                this.dictValvulasDisponibles = dictValvulasEquipo;
                this.setCantidadSecuencias(dictValvulasEquipo);
            },

            //Metodo que guarda el equipo seleccionado
            saveEquipo: function(equipoSeleccionado){
                let opcionSeleccionada = document.getElementById('select_equipoSimulacion').selectedOptions[0].value;
                this.getValvulas();
                return opcionSeleccionada;
            },

            //Metodo que guarda el nombre de la simulacion
            saveNombreSimulacion: function(nombreSimulacionInput){
                let nuevaSimulacion = document.getElementById("add_nombre_simulacion").value;
                this.nombreSimulacionInput = nuevaSimulacion;
                return this.nombreSimulacionInput;
            },

            // abrir el modal del generador de eventos SÓLO para la valvula seleccionada
            seleccionarValvulaUtilizar(posicionSecuencia) {
                this.setSecuenciaModificar(posicionSecuencia);
                this.isGeneradorEventosOpen = true;
            },        

            //Metodo que envia registro de una nueva simulacion
            async sendAddSimulacion(eventObj){
                eventObj.preventDefault();
                const simulacionBody = JSON.stringify({
                    "nombre":this.nombreSimulacionInput,
                    "descripcion":this.descripcionInput,
                    "nombreEquipo":this.equipoSeleccionado,
                    "rutOperador": "",
                    "listaSecuencias":this.secuencias});

                const tokenUsuario = window.localStorage.getItem("token");
                const POST_config = {
                    'method': 'POST',
                    'body': simulacionBody,
                    'headers':{
                        'Content-Type':'application/json',
                        'authorization': tokenUsuario,}
                };
                
                console.clear()
                const respuesta = await fetch(`${this.urlApi}/simulaciones/secuencias/`, POST_config).catch(err => err);
                if (respuesta instanceof Error) {
                    alert("❌ Error enviando al servidor ❌");
                    return;
                }
                const mensajeRespuesta = await respuesta.json();
                alert(mensajeRespuesta["mensajeTexto_"]);
                window.location.reload();
            },
            cancelarAddSimulacion() {
                const anchorElement = document.createElement("a");
                anchorElement.href= "/operador/menu-operador";
		        anchorElement.click();
            }
        }    
    })
</script>

<template>
    <section>
        <div>
            <NavbarPag/> 
        </div>
        <div class="container-header">
            <PageHeader />
        </div>
        <div class= "container">
            <div>
                <div class="row my-4">
                    <h4>Registrar Simulación</h4>
                </div>

                <!-- PARTE TABLA GENERADORA DE EVENTOS -->
                <div v-if="isGeneradorEventosOpen" class="generadorEventosContainer"> 
                    <GeneradorEventos 
                        @onCancelClick = "isGeneradorEventosOpen=false"
                        :isGeneradorEventosOpen = "isGeneradorEventosOpen"
                        /> 
                </div>

                <div class="row">
                    <form id ="form-regsimulacion" method="post">

                        <!-- PARTE SELECCIONAR EQUIPO PARA OBTENER LAS VALVULAS -->
                        <div class="my-4 form-group row">
                            <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                            <div class="col-sm-6">
                                <select id="select_equipoSimulacion" v-model="equipoSeleccionado" @change= "saveEquipo()" class="form-select" aria-label="Equipo" required>
                                    <option v-for="i in Object.keys(equiposDisponibles)" :value="i">{{i}}</option>
                                </select>
                            </div>
                        </div>

                        <!-- PARTE ESCRIBIR NOMBRE SIMULACION -->
                        <div class="my-4 form-group row">
                            <label for="add_nombre_simulacion" class="col-sm-4 col-form-label">Nombre Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_nombre_simulacion" type="text" class="form-control" @change="saveNombreSimulacion(nombreSimulacionInput)" required>
                            </div>
                        </div>
                    
                        <!-- PARTE SELECCIONAR LA VALVULA PARA ABRIR TABLA DE GENERADOR DE EVENTOS -->
                        <div class="valvulasSection my-4 form-group row">
                            <label for="add_secuencia" class="valvulasTitle col-sm-4 col-form-label">Secuencias</label>
                            <div v-for="(nombreValvula, posicionSecuencia) in Object.keys(dictValvulasDisponibles)" class="row">
                                <label class="col-sm-4  col-form-label">Secuencia {{posicionSecuencia+1}}</label>
                            
                                <p :key="`valvulaKey_${posicionSecuencia}`"  class="valvulaRow col-form-label col-sm-6" @click="() => seleccionarValvulaUtilizar(posicionSecuencia)">
                                    Válvula id: {{dictValvulasDisponibles [nombreValvula]}}
                                </p>
                            </div>
                        </div>          

                        <!-- PARTE ESCRIBIR DESCRIPCION SIMULACION -->
                        <div class="my-4 form-group row">
                            <label for="add_descripcion_equipo" class="col-sm-4 col-form-label">Descripción Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_descripcion_equipo" v-model="descripcionInput" type="text" class="form-control" required>
                            </div>
                        </div>

                        <!-- PARTE BOTONES -->
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <button class="finalFormButton cancelarButton" @click="cancelarAddSimulacion" > CANCELAR </button>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <button class="finalFormButton sendDataButton" @click="sendAddSimulacion" > GUARDAR </button>
                            </div>
                        </div> 
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>
<style>

    .generadorEventosContainer{
        z-index: 999;
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        width:100vw;
        height:100vh;
        background-color: rgba(0, 0, 0, 0.8);
    }

    .contenido-botones{
        width: fit-content;
        margin: auto ;
    }   
    .conf-secuencias{
        margin: 0 auto;
        padding-left: 2rem;
    }  

    .valvulasSection {
        display:flex;
/*      flex-direction: column;
 */     width: 90%;
        padding: .375rem 2.25rem .375rem .75rem;
    }
    /* .hola{
        display: block;
        width: 100%;
        
        -moz-padding-start: calc(0.75rem - 3px);
        font-size: 1rem;
        font-weight: 400;
        line-height: 1.5;
        color: #212529;
        
        background-size: 16px 12px;
        border: 1px solid #ced4da;
        border-radius: .375rem;
    } */

    .valvulaRow {
       /*  width: 100%; */
        margin: 0.2rem auto 0.5rem auto;
        padding: 0.5rem;
        border: 1px solid #ced4da;  
        border-radius: .375rem;
        cursor: pointer;
        
        text-align:center;
    }
    .valvulaRow:hover {
        background-color: #212529;
        color: #fff;
        /* font-weight: bold;
        transform: scale(1.1); */
    }


    .finalFormButton {
        color: white;
        font-weight: 900;
        padding: 0.5rem 1rem;
        border-radius: 10px;
        height: fit-content;
        margin: auto 0;
    }
    .sendDataButton {
        background-color: rgba(2,92,250,1);        
    }
    .cancelarButton {
        background-color: rgba(199,0,57,1);
    }

    .sendDataButton:hover {
        color: rgba(2,92,250,1);
        border: 1px solid rgba(2,92,250,1);
        background-color:white;
        transition: all 0.4s;
        transform: scale(1.1);
    }
    .cancelarButton:hover {
        color: rgba(199,0,57,1);
        border: 1px solid rgba(199,0,57,1);
        background-color:white;
        transition: all 0.4s;
        transform: scale(1.1);
    }

    @media screen and (max-width: 575px) {
        .valvulasSection {
            width: 100%;
        }
    }

</style>