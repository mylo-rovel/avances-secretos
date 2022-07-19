<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import {mapState} from "vuex";
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import { setListasDesplegables } from '~/utils/utility_functions';


    export default Vue.extend({
        name: "IniciarSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton},
        data() {
            return {
                "cancelbutton": "cancelar",
                "tituloPag": "Sistema de Alerta Temprana Aluvional",
                "submitbutton": "guardar",
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
            const listaSimulacionesCrudas = await rawdata.json();

            this.equiposDisponibles = Object.keys(setListasDesplegables(listaSimulacionesCrudas));
            this.dictEquiposSimulaciones = {...setListasDesplegables(listaSimulacionesCrudas)};

            //this.simulacionesDisponibles = setListasDesplegables(listaSimulacionesCrudas);
            //this.equiposDisponibles = Object.keys(setListasDesplegables(listaSimulacionesCrudas));
            //console.log(this.equiposDisponibles);
            //console.log(listaSimulacionesCrudas);
            //console.log(setListasDesplegables(listaSimulacionesCrudas)[this.equiposDisponibles]['listaIds']);
            //this.listaIdsDisponibles = setListasDesplegables(listaSimulacionesCrudas)[this.equiposDisponibles]['listaIds'];
            
            //console.log(this.listaIdsDisponibles);
           // console.log(Object.keys(setListasDesplegables(listaSimulacionesCrudas)));
            // this.simulacionesDisponibles = setListasDesplegables(listaSimulacionesCrudas)[this.equiposDisponibles];
            // console.log(this.simulacionesDisponibles);
            // console.log();
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

            
            

//Metodo que enviar solicitud de iniciar simulacion SE TIENE QUE ENVIAR LA ID DEL EQUIPO Y SIMULACION , NO NOMBRES
            async sendSolicitudSimulacion(){
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

                const respuesta = await fetch(`${this.urlApi}/simulaciones/`, POST_config);
                const mensajeRespuesta = await respuesta.json();
                console.log(mensajeRespuesta)
            }
        }
    })
</script>
    <template>
        <section>
            <div>
                <NavbarPag :tituloPag="tituloPag"/> 
            </div>
            <div class="container-header">
                <PageHeader/>
            </div>          
            <div class= "container">
                <div>
                    <div class="row my-4">
                        <h3>Iniciar Simulación</h3>
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
                        <div class = "row my-4 ">
                                <div class= "col-12 contenido-botones my-4">
                                    <NuxtLink to="/operador/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>                            
                                <div class="col-12 contenido-botones my-4">    
                                    <NuxtLink to="/menu-operador"><SubmitButton :submitbutton = "submitbutton" @click = "sendSolicitudSimulacion()"/></NuxtLink>
                                </div>
                            </div>
                        <!--<button @click="sendSolicitudSimulacion()">CONSOLE.LOG HERE CHECK ME OUT</button>-->
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
    
</style>