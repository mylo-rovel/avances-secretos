<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import {mapState} from "vuex";
    import CustomButton from '../../components/CustomButton.vue';
    import { setListasDesplegables } from '~/utils/utility_functions';
    import SideBar from '../../components/SideBar/SideBar.vue';

    export default Vue.extend({
        name: "IniciarSimulacion",
        components: { SideBar},
        head(){
            return{
                title: "Iniciar Simulación - Sistema de Alerta Temprana Aluvional",
                meta: [{
                    name: "iniciarSimulacion",
                    content: "Iniciar simulación"
                }],
            }
        },
        data() {
            return {
                "tituloPag": "Sistema de Alerta Temprana Aluvional",
                simulacionesDisponibles: [],
                listaIdsDisponibles:[],

                equiposDisponibles:[],
                equipoSeleccionado: "",
                idSimulacionSeleccionada:0,

                // simulaciones: [],
                dictEquiposSimulaciones:{}
            };
        },
        computed: mapState(["urlApi"]) ,
        
        async mounted(){
            const JWTtoken = window.localStorage.getItem("token");
            const post_config = { 
                method: 'get', 
                headers: {'authorization': JWTtoken}
            };
            const url_to_fetch = `${this.urlApi}/simulaciones/`;
            const rawdata = await fetch(url_to_fetch, post_config).catch(err => err);
            if (rawdata instanceof Error) {
                alert("❌ Error al solicitar datos del servidor ❌");
                return;
            }

            const listaSimulacionesCrudas = await rawdata.json();

            this.equiposDisponibles = Object.keys(setListasDesplegables(listaSimulacionesCrudas));
            this.dictEquiposSimulaciones = {...setListasDesplegables(listaSimulacionesCrudas)};
        },

        

        methods: {
            //Metodo que guarda el equipo seleccionada
            saveEquipo: function(equipoSeleccionado){
                let opcionSeleccionada = document.getElementById('select_equipoSimulacion').selectedOptions[0].value;
                this.simulacionesDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaSimulaciones"];
                this.listaIdsDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaIds"];
                return opcionSeleccionada;
                console.log(opcionSeleccionada);
            },

            //Metodo que guarda la simulacion seleccionada
            saveSimulacion: function(simulacionSeleccionada){
                simulacionSeleccionada = document.getElementById('select_idSimulacion');
                let opcion = simulacionSeleccionada.selectedOptions[0].value;
                console.log(opcion);
                return opcion;
            },

            async sendStartSimulacionReq(){
                let solicitud = JSON.stringify({"nombreEquipo":this.equipoSeleccionado, "id":this.idSimulacionSeleccionada});
                console.log(solicitud);

                const tokenUsuario = window.localStorage.getItem("token");
                const POST_config = {
                    'method': 'POST',
                    'body': solicitud,
                    'headers':{
                        'Content-Type':'application/json',
                        'authorization': tokenUsuario,}
                };

                const respuesta = await fetch(`${this.urlApi}/simulaciones/`, POST_config).catch(err => err);
                if (respuesta instanceof Error) { 
                    console.log(respuesta);
                    return false; 
                }
                const mensajeRespuesta = await respuesta.json();
                alert(mensajeRespuesta["mensajeTexto_"]);          
                // si el mensaje no incluye una X quiere decir que todo salió bien
                if (!mensajeRespuesta["mensajeTexto_"].includes("❌")){
                    const anchorElement = document.createElement("a");
                    anchorElement.href= "/operador/ver-simulacion";
                    anchorElement.click(); 
                }
            }
        }
    })
</script>
    <template>
        <section>
            <SideBar/>
            <div class= "container">
                <div>
                    <div class="row my-4">
                        <h3 style="border-bottom: 1mm solid #2162ad;">Iniciar Simulación</h3>
                    </div>
                    <div class="row">
                        <form id="form_iniciarSimulacion" method="post">
                            <div class="my-4 form-group row">
                                <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                                <div class="col-sm-6">
                                    <select id="select_equipoSimulacion" v-model="equipoSeleccionado" @change= "saveEquipo()" class="form-select" aria-label="Equipo" required>
                                        <option v-for="i in equiposDisponibles" :value="i">{{i}}</option>
                                    </select>
                                </div>
                            </div>
                           
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label" >Seleccione una simulación</label>
                                <div  class="col-sm-6">
                                    <select id="select_idSimulacion" v-model="idSimulacionSeleccionada" class="form-select" aria-label="Simulacion" required>
                                         <option v-for="(simulacion, indiceFila) in simulacionesDisponibles" :value="listaIdsDisponibles[indiceFila]">
                                            {{`${listaIdsDisponibles[indiceFila]} - ${simulacion}`}}
                                        </option> 
                                    </select>
                                </div>
                            </div>
                        </form>
                        <!-- PARTE BOTONES -->
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink :to="'menu-operador'">
                                    <CustomButton  :text="'ATRÁS'" :custombcolor="'#C70039'" customhcolor="#72152f"/>
                                </NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">
                                <CustomButton  :text="'INICIAR'" :custombcolor="'#4482ee'" customhcolor="#264a88" @click.native="sendStartSimulacionReq"/>   
                            </div>
                        </div> 

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

    .finalFormButton {
    color: white;
    font-weight: 900;
    padding: 0.5rem 1rem;
    border-radius: 10px;
    height: fit-content;
    margin: auto 0;
}
</style>