<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
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
                equiposDisponibles:[],
                equipoSeleccionado: "",
                simulacionSeleccionada:"",
                apiURL: "http://localhost:8081/api",

                simulaciones: []
            };
        },
        async mounted(){
            const JWTtoken = window.localStorage.getItem("token");
            const post_config = { 
                method: 'get', 
                headers: {'authorization': JWTtoken}
            };
            const url_to_fetch = `http://localhost:8081/api/simulaciones/`;
            const rawdata = await fetch(url_to_fetch, post_config).catch(err => err);
            const listaSimulacionesCrudas = await rawdata.json();
            this.simulaciones = setListasDesplegables(listaSimulacionesCrudas);
            this.equiposDisponibles = Object.keys(setListasDesplegables(listaSimulacionesCrudas));
            console.log(this.equiposDisponibles);
           // console.log(Object.keys(setListasDesplegables(listaSimulacionesCrudas)));
            this.simulacionesDisponibles = setListasDesplegables(listaSimulacionesCrudas)[this.equiposDisponibles];
            console.log(this.simulacionesDisponibles);
            console.log();
        },

        methods: {
            async getSimulaciones ({$axios}){//enviar peticion
                console.clear();
                const serverPath = `${this.apiURL}/simulaciones`;
                const serverResponse = await $axios.$get(serverPath).catch(err => err);
                if (serverResponse instanceof Error) {
                    alert("ERROR. rayos :(", serverResponse)
                    return false;
                }
                console.log(serverResponse);
                //this.simulaciones = serverResponse["simulacionAcotada_"];
                return true;
            },

            //Metodo que llena las opciones del select con los equipos
            // getEquipos: function(simulaciones) {
            //     let selectIdEquipo = document.getElementById('select_equipoSimulacion');
            //     for (let i = 0; i < simulaciones.length; i++) {
            //         let opcionEquipo = document.createElement('option');
            //         opcionEquipo.text = simulaciones[i].nombreEquipo_;
            //         selectIdEquipo.add(opcionEquipo);
            //     }
            //     console.log();
            //     return selectIdEquipo;
            // },

            //Metodo que llena las opciones del select con los id de simulaciones del equipo seleccionado
            getSimulacionesEquipo: function(){
                var equipoActual = document.getElementById('select_equipoSimulacion').selectedIndex;
                for(let i = 0; i < simulaciones.length; i++ ){
                    document.getElementById('select_idSimulacion').value = simulaciones[equipoActual].id_;
                }
                console.log();
            },

            //Metodo que muestra el nombre de la simulacion dada la seleccion de un id 
            displayNombreSimulacion: function() { 
                var simulacionActual = document.getElementById('select_idSimulacion').selectedIndex;
                document.getElementById('nombreSimulacion').value = this.simulaciones[simulacionActual].nombre_;
                console.log(simulacionActual);
            },

            //Metodo que guarda el equipo seleccionada
            saveEquipo: function(equipoSeleccionado){
                equipoSeleccionado = document.getElementById('select_equipoSimulacion');
                let opcion = equipoSeleccionado.selectedOptions[0].value;
                console.log(opcion);
                return opcion;
            },

            //Metodo que guarda la simulacion seleccionada
            saveSimulacion: function(simulacionSeleccionada){
                simulacionSeleccionada = document.getElementById('select_idSimulacion');
                let opcion = simulacionSeleccionada.selectedOptions[0].value;
                console.log(opcion);
                return opcion;
            },

            //Metodo que enviar solicitud de iniciar simulacion
            async sendSolicitudSimulacion(){
                let info = JSON.stringify({"equipo":equipoSeleccionado, "simulacion":simulacionSeleccionada});

                // document.getElementById("form_iniciarSimulacion").submit();
                const POST_config = {
                    'method': 'POST',
                    'body': info,
                    'authorization': 'sdzfdnfdsf'
                };
                const rawResponse = await fetch('https://jsonplaceholder.typicode.com/', POST_config);
                console.log(rawResponse);
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
                        <!--<button @click="sendSolicitudSimulacion()">aaa</button>
                        <button @click="simus(simulaciones)">aaajjj</button>-->
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
                                <div  v-if= "" class="col-sm-6">
                                    <select id="select_idSimulacion" v-model="simulacionSeleccionada" class="form-select" @change= "saveSimulacion()" aria-label="Simulacion" required>
                                         <option v-for="i in simulaciones" :value="i">{{i}}</option>
                                    </select>
                                </div>
                            </div>
                            <!--<div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label" >Nombre simulación</label>
                                <div class="col-sm-6">
                                    <input id="nombreSimulacion" name="nombre_simulacion" class="form-control" aria-label="Simulacion" required>
                                </div>
                            </div>-->
                            <div class = "row my-4 ">
                                <div class= "col-12 contenido-botones my-4">
                                    <NuxtLink to="/operador/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>                            
                                <div class="col-12 contenido-botones my-4">    
                                    <NuxtLink to="/menu-operador"><SubmitButton :submitbutton = "submitbutton" @click = "sendSolicitudSimulacion()"/></NuxtLink>
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
    
</style>