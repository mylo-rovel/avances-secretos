<script lang="js">
    import Vue from 'vue';
    import CustomButton from '../../components/CustomButton.vue';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import {mapState} from "vuex";
    import { BPagination } from 'bootstrap-vue';
    import { 
        setListasDesplegables, 
        getListasUsarDatosGrafico, 
        checkIfUserShouldBeHere } from '~/utils/utility_functions';

    export default Vue.extend({
        name: "EquiposEnEjecucion",
        components: { BPagination, SideBar },
        data() {
            return {
            equiposDisponibles:[],
            equipoSeleccionado: "",

            paginaRenderizar: "listaEquiposEjecutando",
            // paginaRenderizar: "graficoEjecucion",

            objetoDatosEjecucion: {},
            cantidadValoresGrafico:0,
            porPantalla:7,
            pagActual:1,
            listaEnEjecucion:[]
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){
            checkIfUserShouldBeHere(["OPERADOR"]);

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
                    "indiceFinal": 50
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
            },
        }
    })
</script>

<template>
    <section>
        <SideBar/>
        <div class= "container">
            <div v-if="paginaRenderizar === 'listaEquiposEjecutando'" id="verSimulacionSelection">
                <div class="row">
                    <div class="my-4" >
                    <NuxtLink :to="'menu-operador'">
                        <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
                    </NuxtLink>     
                    </div>
                    <h2>Equipos en Ejecución</h2>
                </div>
                <div class="row">
                    <div class="my-4 table-responsive table-container">
                        <table id="tablaSimulaciones" class="tabla-simulacion table table-lifht table-bordered table-hover table-striped">
                        <thead class="text-white">
                            <th scope="row">Nombre Equipo</th>
                            <th scope="row">Nombre Simulación</th>
                            <th scope="row">Ver en Vivo</th>
                        </thead>
                        <tbody id="tBody-simulaciones">
                        <tr scope="row" :key="`eventKey_${rowIndex}`">
                            <td> Genesis66 </td>
                            <td> Lluvia de 2010 </td>
                            <td> <NuxtLink :to="'ver-simulacion'"> <CustomButton :text="'Ver'" :custombcolor="'#68da85'" :customhcolor="'#3c724a'"/> </NuxtLink> </td>
                            </tr>
                        </tbody>
                        </table>
                    </div>
                    <b-pagination ref="pagination" class="justify-content-end" v-model="pagActual"
                    :total-rows="this.listaEnEjecucion.length"
                    :per-page="porPantalla"
                    first-number
                    last-number></b-pagination>
             
                    

                    <!-- <form>
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
                       
                        <div class = "row my-4 button-ribbon">
                          
                            <div class= "col-12 contenido-botones my-4 button-container">
                                <NuxtLink :to="'menu-operador'">
                                    <CustomButton :text="'Cancelar'" :custombcolor="'#c70039'" :customhcolor="'#7c0225'" style="width: 6em" />
                                </NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4 button-container">
                                
                                <CustomButton :text="'Enviar'" :custombcolor="'#025cfa'" :customhcolor="'#012f7e'" style="width: 6em" @click.native="sendVerSimulacionReq"  />
                            </div>
                        </div>
                    </form> -->
                </div>
            </div>
            <!-- <div v-if="paginaRenderizar === 'graficoEjecucion'" id="verSimulacionGrafico">
               <GraficoEjecucion  
                :objetoDatosEjecucion="objetoDatosEjecucion"
                :equipoSeleccionado="equipoSeleccionado"
                :cantidadValoresGrafico="cantidadValoresGrafico"
                /> 
            </div> -->
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