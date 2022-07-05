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

                simulaciones: [
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
                ],
            };
        },
        // async fetch(){
        //     this.simulaciones = await fetch(`${this.urlApi}/simulaciones`).then(res => {
        //     console.log(res.json());
        //     return res.json();
        //     });
        // },
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
                this.simulaciones = serverResponse["simulacionAcotada_"];
                return true;
            },
            getSimulacionesEquipo: function(simulaciones){
                var equipoActual = document.getElementById('select_equipoSimulacion').selectedIndex;
                for(let i = 0; i < simulaciones.length; i++ ){
                    document.getElementById('select_simulacion').value = simulaciones[equipoActual].nombre_;
                }
                console.log();
            },

            obtSimulaciones(simulaciones){
                var equipoActual = document.getElementById('select_equipoSimulacion').selectedIndex;
                for(let i = 0; i < simulaciones.length; i++ ){
                    document.getElementById('select_simulacion').value = simulaciones[i].nombre_;
                }
                console.log("ok");
                return equipoActual;

            },
            getEquipos: function(simulaciones) {
                let selectIdEquipo = document.getElementById('select_equipoSimulacion');
                for (let i = 0; i < simulaciones.length; i++) {
                    let opcionEquipo = document.createElement('option');
                    opcionEquipo.text = simulaciones[i].nombreEquipo_;
                    selectIdEquipo.add(opcionEquipo)
                }
                console.log();
                return selectIdEquipo;
            },
            guardarEquipo(equipoSeleccionado){
                equipoSeleccionado = document.getElementById('select_equipoSimulacion');
                let opcion = equipoSeleccionado.selectedOptions[0].value;
                console.log(opcion);
                return opcion;

            },
            guardarSimulacion(simulacionSeleccionada){
                simulacionSeleccionada = document.getElementById('select_simulacion');
                //alert(simulacionSeleccionada.options[simulacionSeleccionada.selectedIndex].value);
                let opcion = simulacionSeleccionada.selectedOptions[0].value;
                console.log(opcion);
                return opcion;
            },
            enviarSolicitud(){
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
                        <button @click="()=>getSimulaciones({$axios})">aaa</button>
                    </div>
                    <div class="row">
                        <form id="form_iniciarSimulacion" method="post">
                            <div class="my-4 form-group row">
                                <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                                <div class="col-sm-6">
                                    <select id="select_equipoSimulacion" name="equipo-simulacion" @change= "guardarEquipo()" class="form-select" aria-label="Equipo" required>
                                        <!--<option value="" disabled selected></option>
                                            <option>Equipo simulador de lluvia </option>
                                            <option>Simulador 2.0</option>-->
                                    </select>
                                </div>
                            </div>
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label" >Seleccione una simulación</label>
                                <div class="col-sm-6">
                                    <select id="select_simulacion" name="simulacion" class="form-select" @change= "guardarSimulacion()" aria-label="Simulacion" required>
                                        <option value="" disabled selected></option>
                                            <option>Simulación 1</option>
                                            <option>Simulación 2</option>
                                    </select>
                                </div>
                            </div>
                            <div class = "row my-4 ">
                                <div class= "col-12 contenido-botones my-4">
                                    <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>                            
                                <div class="col-12 contenido-botones my-4">    
                                    <NuxtLink to="/menu-operador"><SubmitButton :submitbutton = "submitbutton" @click = "enviarSolicitud()"/></NuxtLink>
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