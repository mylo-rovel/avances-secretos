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
        components: { PageHeader, CancelButtom, SubmitButton},
        data() {
            return {
            "cancelbutton": "cancelar",
            "submitbutton": "guardar",
            simulacionesDisponibles: [],
            listaIdsDisponibles:[],

            equiposDisponibles:[],
            equipoSeleccionado: "",
            idSimulacionSeleccionada:0,

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
            //console.log(this.equiposDisponibles);

        },
        methods:{

            saveEquipo: function(equipoSeleccionado){
                let opcionSeleccionada = document.getElementById('select_equipoSimulacion').selectedOptions[0].value;
                this.simulacionesDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaSimulaciones"];
                this.listaIdsDisponibles = this.dictEquiposSimulaciones[opcionSeleccionada]["listaIds"];
                console.log(opcionSeleccionada);
                return opcionSeleccionada;
                console.log("hola");
                
            },
            async sendSolicitudVerSimulacion(){
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
                console.log(respuesta);
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
            <div>
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
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink to="/operador/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <NuxtLink to="/operador/menu-operador"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>