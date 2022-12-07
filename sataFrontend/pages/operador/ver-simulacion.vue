<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import {mapState} from "vuex";
    import { 
        setListasDesplegables, 
        getListasUsarDatosGrafico, 
        checkIfUserShouldBeHere } from '~/utils/utility_functions';

    export default Vue.extend({
        name: "VerSimulacion",
        components: { SideBar },
        data() {
            return {
            equiposDisponibles:[],
            equipoSeleccionado: "",

            paginaRenderizar: "listaEquiposEjecutando",
            // paginaRenderizar: "graficoEjecucion",

            objetoDatosEjecucion: {},
            cantidadValoresGrafico:0,
            
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

                const rawReponse = await fetch(`${this.urlApi}/ejecuciones/valoresgrafico`, POST_config).catch(err => err);
                if (rawReponse instanceof Error) {
                    alert("❌ Error al pedir gráfico ❌");
                    console.log(rawReponse)
                    return;
                }
                const objetoRespuesta = await rawReponse.json();
                if (objetoRespuesta === "Indices incorrectos") {
                    alert("❌ INDICES INCOHERENTES ❌");
                    console.log(rawReponse)
                    return;
                }
                const valoresCaudalArr = objetoRespuesta["caudalTiempo_"];
                this.objetoDatosEjecucion = getListasUsarDatosGrafico(valoresCaudalArr);
                this.cantidadValoresGrafico = objetoRespuesta["listaSize_"];
                this.paginaRenderizar = "graficoEjecucion";
                console.log(`CANTIDAD VALORES GRAFICO ${objetoRespuesta["listaSize_"]}`);
                console.log(`CANTIDAD INICIAL PEDIDA ${this.objetoDatosEjecucion["caudales"].length}`);
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
                    <NuxtLink :to="'equipos-en-ejecucion'">
                        <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
                    </NuxtLink>     
                    </div>
                    <h2>Ver Simulacion</h2>
                </div>
                <div class="row">
                    
                </div>
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