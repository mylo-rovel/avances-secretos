<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import {mapState} from "vuex";
    import { setListasDesplegables } from '~/utils/utility_functions';

    export default Vue.extend({
        name: "VerSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton },
        data() {
            return {
            "cancelbutton": "cancelar",
            "submitbutton": "guardar",
            simulacionesDisponibles: [],
            listaIdsDisponibles:[],

            equiposDisponibles:[],
            equipoSeleccionado: "",
            idSimulacionSeleccionada:0,

            dictEquiposSimulaciones:{},

            paginaRenderizar: "listaEquiposEjecutando",
            // paginaRenderizar: "graficoEjecucion",

            listaDatosEjecucion: [
                {caudal: 400, pos: 1},
                {caudal: 500, pos: 2},
                {caudal: 450, pos: 3},
                {caudal: 600, pos: 4},
                {caudal: 400, pos: 5},
            ]
            
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){

            const JWTtoken = window.localStorage.getItem("token");
            const get_config = { 
                method: 'get', 
                headers: {'authorization': JWTtoken}
            };
            const url_to_fetch = `${this.urlApi}/equipos/trabajando`;
            const rawdata = await fetch(url_to_fetch, get_config).catch(err => err);
            if (rawdata instanceof Error) {
                alert("❌ Error al enviar solicitud ❌");
                return;
            }
            const objetoRespuesta = await rawdata.json();
                    
            this.equiposDisponibles = objetoRespuesta["equipoAcotado_"].reduce((acc, equipoAcotado) => {
                return [...acc, equipoAcotado["nombre_"]];
            }, []);
            // this.dictEquiposSimulaciones = {...setListasDesplegables(listaSimulacionesCrudas)};
        },
        methods:{

            saveEquipo: function(equipoSeleccionado){
                let opcionSeleccionada = document.getElementById('select_equipoSimulacion').selectedOptions[0].value;
                this.simulacionesDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaSimulaciones"];
                this.listaIdsDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaIds"];
                console.log(opcionSeleccionada);
                return opcionSeleccionada;
            },

            async sendVerSimulacionReq(e){
                e.preventDefault();

                const tokenUsuario = window.localStorage.getItem("token");
                const GET_config = {
                    'method': 'POST',
                    'headers':{
                        'authorization': tokenUsuario,}
                };

                const rawReponse = await fetch(`${this.urlApi}/`, GET_config).catch(err => err);
                if (rawReponse instanceof Error) {
                    alert("❌ Error al enviar solicitud ❌");
                    return;
                }
                const objetoRespuesta = await rawReponse.json();
                alert(mensajeTexto);

                this.graficoEjecucion = "graficoEjecucion";
            },
            
            cancelarVerSimulacion() {
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
            <PageHeader/>
        </div>
        <div class= "container">
            <div v-if="paginaRenderizar === 'listaEquiposEjecutando'" id="verSimulacionSelection">
                <div class="row my-4">
                    <h2>Ver Simulación</h2>
                </div>
                <div class="row">
                    <form>
                        <div class="my-4 form-group row">
                            <label for="simulador" class="col-sm-4 col-form-label ">Seleccione el equipo</label>
                            <div class="col-sm-6">
                                <select id="select_equipoSimulacion" v-model="equipoSeleccionado" @change= "saveEquipo()" class="form-select" aria-label="Equipo" required>
                                    <option v-for="i in equiposDisponibles" :value="i">{{i}}</option>
                                </select>
                            </div>
                        </div>
                        <!-- PARTE BOTONES -->
                        <div class = "row my-4 button-ribbon">
                            <div class= "col-12 contenido-botones my-4 button-container">
                                <button class="finalFormButton cancelarButton" @click="cancelarVerSimulacion" > CANCELAR </button>
                            </div>                            
                            <div class="col-12 contenido-botones my-4 button-container">    
                                <button class="finalFormButton sendDataButton" @click="sendVerSimulacionReq" > ENVIAR </button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
            <div v-if="paginaRenderizar === 'graficoEjecucion'" id="verSimulacionGrafico">
                <svg></svg>
            </div>
        </div>
    </section>
</template>

<style>

    .button-ribbon {
        width: 50vw;
        display: flex;
        margin: 0 auto;
        justify-content: space-between;
    }

    .button-container {
        width: fit-content;
        margin: 0 auto;
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


</style>