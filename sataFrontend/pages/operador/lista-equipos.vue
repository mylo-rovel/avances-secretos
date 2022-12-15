<script lang="js">
    import Vue from 'vue';
    import CustomButton from '../../components/CustomButton.vue';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import {mapState} from "vuex";
    import { BPagination } from 'bootstrap-vue';
    import { 
        setListasDesplegables, 
        getListasUsarDatosGrafico, 
        checkIfUserShouldBeHere,
        listaSimuEnEjecucion,
        listaEquipos } from '~/utils/utility_functions';

    export default Vue.extend({
        name: "ListaEquipos",
        components: { BPagination, SideBar },
        head(){
            return{
                title: "Listado Equipos - Sistema de Alerta Temprana Aluvional",
                meta: [{
                    name: "listadoEquipos",
                    content: "Listado equipos"
                }],
            }
        },
        data() {
            return {
            equiposDisponibles:[],
            equipoSeleccionado: "",

            paginaRenderizar: "listaEquipos",
            // paginaRenderizar: "graficoEjecucion",

            objetoDatosEjecucion: {},
            cantidadValoresGrafico:0,
            porPantalla:7,
            pagActual:1,
            listaEnEjecucion:[],
            listaEquipos:[]
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){
            checkIfUserShouldBeHere(["OPERADOR"]);
            // OBTENER LA LISTA DE LOS EQUIPOS QUE ESTÁN EJECUTANDO UNA
            this.listaEnEjecucion = listaSimuEnEjecucion;
            this.listaEquipos = listaEquipos
        },
        methods: {
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        },
      }

    })
</script>

<template>
    <section>
        <SideBar/>
        <div class= "container">
            
            <div class="row">
                <div class="my-4" >
                <NuxtLink :to="'menu-operador'">
                    <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
                </NuxtLink>     
                </div>
                <h3 style="border-bottom: 1mm solid #2162ad;">Listado de Equipos</h3>
            </div>
            <div class="row">
                <div class="my-4 table-responsive table-container">
                    <table id="tablaSimulaciones" class="tabla-simulacion table table-lifht table-bordered table-hover table-striped">
                    <thead class="text-white">
                        <tr>
                            <th scope="row" rowspan="3">Nombre Equipo</th>
                            <!-- <th scope="row" rowspan="3">Descripción</th> -->
                            <th scope="row" rowspan="3">Cantidad Válvulas</th>
                            <th scope="row" colspan="3">Cantidad de sensores</th>
                        </tr>
                        <tr>
                            <th scope="row">Pluviometro</th>
                            <th scope="row">Temperatura</th>
                            <th scope="row">Humedad</th>
                        </tr>
                    </thead>
                    
                    <tbody id="tBody-simulaciones">
                    <tr scope="row" v-for="(elementObj, rowIndex) in this.listaEquipos.slice((this.pagActual-1) * this.porPantalla,(this.pagActual) * this.porPantalla)" :key="`eventKey_${rowIndex}`">
                        <td> {{elementObj["nombreEquipo_"]}} </td>
                        <!-- <td> {{elementObj["descripcion"]}} </td> -->
                        <td> {{elementObj["sensorPluv"]}} </td>
                        <td> {{elementObj["sensorTemp"]}} </td>
                        <td> {{elementObj["sensorHum"]}} </td>
                        <td> {{elementObj["cantValv"]}} </td>
                        
                    </tr>
                    </tbody>
                    </table>
                </div>
                <b-pagination ref="pagination" class="justify-content-end" v-model="pagActual"
                :total-rows="this.listaEnEjecucion.length"
                :per-page="porPantalla"
                first-number
                last-number></b-pagination>
            </div>
        </div>
    </section>
</template>

<style>
 .table-container {
    max-height: 50vh;
    overflow-y: auto;
  }
  .tabla-simulacion{
    background-color: white;
    text-align: center;
    border-collapse: collapse;
    width: 100%;
  }
  .tabla-simulacion thead {
    background-color: #4e89f0;
    color: white;
  }
  .tabla-simulacion th{
    padding: 0.5rem;
  }
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
        background-color: #025cfa;        
    }
    .sendDataButton:hover {
        color: #012f7e;
        border: 1px solid rgba(2,92,250,1);
        background-color:white;
        transition: all 0.4s;
        transform: scale(1.1);
    }
 

</style>