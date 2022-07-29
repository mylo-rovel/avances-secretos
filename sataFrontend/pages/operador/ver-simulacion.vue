<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import {mapState} from "vuex";
    import { 
        setListasDesplegables, 
        getListasUsarDatosGrafico } from '~/utils/utility_functions';

    export default Vue.extend({
        name: "VerSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton },
        data() {
            return {
            "cancelbutton": "cancelar",
            "submitbutton": "guardar",
            equiposDisponibles:[],
            equipoSeleccionado: "",

            paginaRenderizar: "listaEquiposEjecutando",
            // paginaRenderizar: "graficoEjecucion",

            objetoDatosEjecucion: []
            
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){
            // OBTENER LA LISTA DE LOS EQUIPOS QUE ESTÁN EJECUTANDO UNA
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
        },

        methods:{
            async sendVerSimulacionReq(e){
                e.preventDefault();
                const POST_body = {
                    "nombreEquipo": this.equipoSeleccionado,
                    "indiceInicial": 0,
                    "indiceFinal": 100
                }
                const tokenUsuario = window.localStorage.getItem("token");
                const POST_config = {
                    'method': 'POST',
                    'body': JSON.stringify(POST_body),
                    'headers':{
                        'Content-Type':'application/json',
                        'authorization': tokenUsuario,
                        }
                };

                const rawReponse = await fetch(`${this.urlApi}/ejecuciones/valoresgrafico`, POST_config).catch(err => err);
                if (rawReponse instanceof Error) {
                    alert("❌ Error al pedir gráfico ❌");
                    console.log(rawReponse)
                    return;
                }
                const objetoRespuesta = await rawReponse.json();
                const valoresCaudalArr = objetoRespuesta["caudalTiempo_"];
                this.objetoDatosEjecucion = getListasUsarDatosGrafico(valoresCaudalArr);
                // console.log(this.objetoDatosEjecucion);
                this.paginaRenderizar = "graficoEjecucion";

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
                                <select id="select_equipoSimulacion" v-model="equipoSeleccionado" class="form-select" aria-label="Equipo" required>
                                    <option v-for="i in equiposDisponibles" :value="i">
                                        {{i}}
                                    </option>
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
               <GraficoEjecucion  
                :objetoDatosEjecucion="objetoDatosEjecucion"
                :equipoSeleccionado="equipoSeleccionado"
                /> 
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