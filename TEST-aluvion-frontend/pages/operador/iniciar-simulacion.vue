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
            "submitbutton": "guardar",
            simulaciones: [],
            equipos:[],
            equipoSeleccionado: "",
            simulacionSeleccionada:""
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
                 const serverPath = `${this.apiURL}/leddeldestino`;
                const serverResponse = await $axios.$get(serverPath).catch(err => err);
                if (serverResponse instanceof Error) {
                    alert("ERROR. rayos :(", serverResponse)
                    return false;
                }
                alert(serverResponse);
                return true;
            },
            getSimulacionesEquipo(){
                var equipoActual = document.getElementById('select_equipoSimulacion').selectedIndex;
                for(let i = 0; i < simulaciones.length; i++ ){
                    document.getElementById('select_simulacion').value = simulaciones[equipoActual].nombre_;
                }
            },
            getEquipos(equipos) {
                let selectIdEquipo = document.getElementById('select_equipoSimulacion');
                for (let i = 0; i < equipos.length; i++) {
                    let optionpet = document.createElement('option')
                    optionpet.text = equipos[i].nombre_;
                    selectidPet.add(optionpet)
                }
                console.log();
            },
            guardarEquipo(equipoSeleccionado){
                equipoSeleccionado = document.getElementById('select_equipoSimulacion');
                return equipoSeleccionado;
            },
            guardarSimulacion(simulacionSeleccionada){
                simulacionSeleccionada = document.getElementById('select_simulacion');
                return simulacionSeleccionada;
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
                    </div>
                    <div class="row">
                        <form id="form_iniciarSimulacion" method="post">
                            <div class="my-4 form-group row">
                                <label for="equipo-simulacion" class="col-sm-4 col-form-label " >Seleccione un equipo</label>
                                <div class="col-sm-6">
                                    <select id="select_equipoSimulacion" name="equipo-simulacion" class="form-select" @click="getSimulacionesEquipo()" aria-label="Equipo" required>
                                        <!--<option value="" disabled selected></option>
                                            <option>Equipo simulador de lluvia </option>
                                            <option>Simulador 2.0</option>-->
                                    </select>
                                </div>
                            </div>
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label ">Seleccione una simulación</label>
                                <div class="col-sm-6">
                                    <select id="select_simulacion" name="simulacion" class="form-select" aria-label="Simulacion" required>
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
                                    <NuxtLink to="/menu-operador"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
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