<script lang="js">

    import Vue from 'vue'
    import {mapState, mapMutations} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import SecuenciaButton from '~/components/SecuenciaButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import Modal from '~/components/Modal.vue'
    //import * as d3 from "d3";
    import { getEquiposDesplegables, getValvulasDesplegables } from '~/utils/utility_functions';
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

                
                equiposDisponibles:[],
                idEquiposDisponibles:[],

                
                valvulasDisponibles:[],
                equipoSeleccionado:"",

                simulacionInput:"",
                secuenciasInput: [],
                descripcionInput:"",

                dictequipo:{},
                dictvalvula:{},                
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
            const JWTtoken = window.localStorage.getItem("token");
            const post_config = { 
                method: 'get', 
                headers: {'authorization': JWTtoken}
            };

            const urlEquipos = `${this.urlApi}/equipos/`;
            const dataEquipo = await fetch(urlEquipos, post_config).catch(err => err);
            const listaEquiposCrudos = await dataEquipo.json();
            this.equiposDisponibles = getEquiposDesplegables(listaEquiposCrudos);
            console.log(getEquiposDesplegables(listaEquiposCrudos))
            this.dictequipo = {...getEquiposDesplegables(listaEquiposCrudos)};
           
        },
        methods: {
             ...mapMutations(["setSecuenciaModificar", "setCantidadSecuencias"]),

            //Metodo que guarda el equipo seleccionado
            saveEquipo: function(equipoSeleccionado){
                let opcionSeleccionada = document.getElementById('select_equipoSimulacion').selectedOptions[0].value;
                //this.simulacionesDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaSimulaciones"];
                //this.listaIdsDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaIds"];
                return opcionSeleccionada;
                console.log(opcionSeleccionada);
            },
            //Metodo que guarda el nombre de la simulacion
            saveSimulacion: function(simulacionInput){
                let nuevaSimulacion = document.getElementById("add_nombre_simulacion").value;
                this.simulacionInput = nuevaSimulacion;
                console.log(this.simulacionInput);
                return this.simulacionInput;
            },
            //Metodo de guarda la descripcion de la simulacion
            saveDescripcion: function(descripcionInput){
                let nuevaDescripcion = document.getElementById("add_descripcion_equipo").value;
                this.descripcionInput = nuevaDescripcion;
                console.log(this.descripcionInput);
                return this.descripcionInput;
            },

            async getValvulas(){
                const JWTtoken = window.localStorage.getItem("token");
                const post_config = { 
                    method: 'get', 
                    headers: {'authorization': JWTtoken}
                };
                const idEquipoSeleccionado = this.equiposDisponibles[this.equipoSeleccionado]["idEquipo"][0];
                const urlValvulaEquipo = `${this.urlApi}/equipos/valvulas/${idEquipoSeleccionado}`;
                console.log(idEquipoSeleccionado);
                //console.log(urlValvulaEquipo);
                const dataValvula = await fetch(urlValvulaEquipo, post_config).catch(err => err);
                const listaValvulasCrudas = await dataValvula.json();
                this.valvulasDisponibles = Object.keys(getValvulasDesplegables(listaValvulasCrudas));
                console.log(this.valvulasDisponibles);
                //console.log(this.valvulasDisponibles.lenght);
            },
         
            cantSecuencias(){
                this.secuencias[this.valvulasDisponibles.lenght];
                console.log(this.secuencias);
            },

            //Metodo que envia registro de una nueva simulacion
            async sendAddSimulacion(){
                let registro = JSON.stringify({"nombre":this.simulacionInput, "descripcion":this.descripcionInput, "equipo":this.equipoSeleccionado, "secuencias":this.secuencias});
                console.log(registro);

                const tokenUsuario = window.localStorage.getItem("token");
                const POST_config = {
                    'method': 'POST',
                    'body': registro,
                    'headers':{
                        'Content-Type':'application/json',
                        'authorization': tokenUsuario,}
                };

                const respuesta = await fetch(`${this.urlApi}/simulaciones/secuencias/`, POST_config);
                const mensajeRespuesta = await respuesta.json();
                console.log(mensajeRespuesta);
            },
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
                <div class="row">
                    <form id ="form-regsimulacion" method="post">
                        <div class="my-4 form-group row">
                            <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                            <div class="col-sm-6">
                                <select id="select_equipoSimulacion" v-model="equipoSeleccionado" @change= "saveEquipo()" class="form-select" aria-label="Equipo" required>
                                    <option v-for="i in Object.keys(equiposDisponibles)" :value="i">{{i}}</option>
                                </select>
                            </div>
                        </div>
                        <button @click="getValvulas()">PRUEBA</button>
                        <div class="my-4 form-group row">
                            <label for="add_nombre_simulacion" class="col-sm-4 col-form-label">Nombre Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_nombre_simulacion" type="text" class="form-control" @change="saveSimulacion(simulacionInput)" required>
                            </div>
                        </div>
                        <div class="my-4 form-group row">
                            <label for="add_nombre" class="col-sm-4 col-form-label">Secuencias</label>
                            <div  v-for="(evento, posicionSecuencia) in valvulasDisponibles" :key="`secKey_${posicionSecuencia}`"  class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula3" class="conf-secuencias col-form-label">Válvula {{posicionSecuencia+1}}</label>
                                </div>
                                <div class="col-sm-6">
                                   <NuxtLink to="/operador/agregar-evento"><SecuenciaButton :nombreSecuencia = "nombreSecuencia" :posicionSecuencia = "posicionSecuencia" @click ="setSecuenciaModificar()"/></NuxtLink>
                                </div>
                                
                            </div>
                            <button @click="cantSecuencias(valvulasDisponibles)">PRUEBA</button>
                        </div>
                        <div class="my-4 form-group row">
                            <label for="add_descripcion_equipo" class="col-sm-4 col-form-label">Descripción Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_descripcion_equipo" @change="saveDescripcion(descripcionInput)" type="text" class="form-control" required>
                            </div>
                        </div>
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <SubmitButton :submitbutton = "submitbutton" @click = "sendAddSimulacion()" />
                                
                            </div>
                        </div> 
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>
<style>
    .contenido-botones{
        width: fit-content;
        margin: auto ;
    }   
    .conf-secuencias{
        margin: 0 auto;
        padding-left: 4rem;
    }  
    .btn-secuencias{
        background-color: #ecf0f1;
        border: 1px solid #d0d3d4;
        color: black;
        padding: 0.25rem 1.5rem;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 1rem;
        margin: 0;
        cursor: pointer;
        border-radius: 10px;
    }
    .btn-secuencias:hover{
        background: #ecf0f1;
        border: 1px solid #d0d3d4;
        color: black;
        /*transform: scale(1.1);*/
    }
    .back-modal{
        display: none;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        position: relative;
        top: 0;
        justify-content: center;
        align-items: center;
    }
    .modal-content {
        /*width: 500px;
        height: 300px;*/
        background-color: white;
        border-radius: 10px;
        text-align: center;
        padding: 5px;
        position: relative;
        margin-left: 40px;
        margin-right: 40px;
    }
    .close-modal {
        position: absolute;
        top: 0;
        right: 14px;
        font-size: 42px;
        transform: rotate(45deg);
        cursor: pointer;
    }
</style>