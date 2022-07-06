<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'


    export default Vue.extend({
        name: "IniciarSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton},
        data() {
            return {
                "cancelbutton": "cancelar",
                "tituloPag": "Sistema de Alerta Temprana Aluvional",
                "submitbutton": "guardar",
                //simulaciones: [],
                //equipos:[],
                equipoSeleccionado: "",
                simulacionSeleccionada:"",
                apiURL: "http://192.168.43.73:8081/api",

                /*simulaciones: [
                    {
                    "id_": 1,
                    "nombre_": "simu1",
                    "nombreEquipo_": "simulador4",
                    "fechaEjecucion_": "AAAAAAAAAAAA",
                    "aguaCaida_": 0.0,
                    "memoizedIsInitialized": -1,
                    },
                    {
                    "id_": 2,
                    "nombre_": "simu2",
                    "nombreEquipo_": "simulador4",
                    "fechaEjecucion_": "AAAAAAAAAAAA",
                    "aguaCaida_": 0.0,
                    "memoizedIsInitialized": -1,
                    }
                ],*/
                simulaciones: [
                    {
                    id_: 1,
                    nombre_: "simu1",
                    nombreEquipo_: "simulador4",
                    fechaEjecucion_: "AAAAAAAAAAAA",
                    aguaCaida_: 0.0,
                    }
                ]
            };
        },
        async fetch(){
            this.getEquipos(this.simulaciones);
            this.getSimulacionesEquipo(this.simulaciones);
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
            getEquipos: function(simulaciones) {
                let selectIdEquipo = document.getElementById('select_equipoSimulacion');
                for (let i = 0; i < simulaciones.length; i++) {
                    let opcionEquipo = document.createElement('option');
                    opcionEquipo.text = simulaciones[i].nombreEquipo_;
                    selectIdEquipo.add(opcionEquipo);
                }
                console.log();
                return selectIdEquipo;
            },

            //Metodo que llena las opciones del select con los id de simulaciones del equipo seleccionado
            getSimulacionesEquipo: function(simulaciones){
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
            sendSolicitudSimulacion: function(){
                let info = JSON.stringify({"equipo":equipoSeleccionado, "simulacion":simulacionSeleccionada});
                return info;
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
                        <!--<button @click="()=>getSimulaciones({$axios})">aaa</button>
                        <button @click="simus(simulaciones)">aaajjj</button>-->
                    </div>
                    <div class="row">
                        <form id="form_iniciarSimulacion" method="post">
                            <div class="my-4 form-group row">
                                <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                                <div class="col-sm-6">
                                    <select id="select_equipoSimulacion" name="equipo_simulacion" @change= "saveEquipo()" class="form-select" aria-label="Equipo" required>
                                        <!--<option value="" disabled selected></option>
                                            <option>Equipo simulador de lluvia </option>
                                            <option>Simulador 2.0</option>-->
                                    </select>
                                </div>
                            </div>
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label" >Seleccione una simulación</label>
                                <div class="col-sm-6">
                                    <select id="select_idSimulacion" name="id_simulacion" class="form-select" @click= "displayNombreSimulacion()" @change= "saveSimulacion()" aria-label="Simulacion" required>
                                        <!--<option value="" disabled selected></option>
                                            <option>1</option>
                                            <option>2</option>-->
                                    </select>
                                </div>
                            </div>
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label" >Nombre simulación</label>
                                <div class="col-sm-6">
                                    <input id="nombreSimulacion" name="nombre_simulacion" class="form-control" aria-label="Simulacion" required>
                                </div>
                            </div>
                            <div class = "row my-4 ">
                                <div class= "col-12 contenido-botones my-4">
                                    <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
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