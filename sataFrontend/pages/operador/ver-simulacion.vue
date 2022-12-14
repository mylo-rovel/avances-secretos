<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import GraficoDona from '../../components/GraficoDona.vue';
    import GraficoLinea from '../../components/GraficoLinea.vue';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import {mapState} from "vuex";
    import { 
        setListasDesplegables, 
        getListasUsarDatosGrafico, 
        checkIfUserShouldBeHere } from '~/utils/utility_functions';
    import { infoGeneral , listaColores } from '../../utils/charts_utils';

    export default Vue.extend({
        name: "VerSimulacion",
        components: { SideBar, GraficoDona, GraficoLinea },
        data() {
            return {
            equiposDisponibles:[],
            equipoSeleccionado: "",
            
            paginaRenderizar: "listaEquiposEjecutando",
            // paginaRenderizar: "graficoEjecucion",

            objetoDatosEjecucion: {},
            cantidadValoresGrafico:0,
            listaSensores:[],
            listaSensoresPluv:[],
            listaSensoresTemp:[],
            listaSensoresHum:[],
            chartDataPropsHum:{labelsL:[], dataL:[], optionsL:[]},
            chartDataPropsPluv:{labelsL:[], dataL:[], optionsL:[]},
            chartDataPropsTemp:{labelsL:[], dataL:[], optionsL:[]}
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){
            checkIfUserShouldBeHere(["OPERADOR"]);
            this.listaSensores = infoGeneral['listaSensores'];
            const listaSensoresPluvParcial = [];
            const listaSensoresTempParcial = [];
            const listaSensoresHumParcial = [];
            let datasetsHum = []; 
            let datasetsPluv = [];
            let datasetsTemp = [];
            for (let value of this.listaSensores) {
                const listaMedidas = value["datos"];
                const nombreSensor = value["nombre"];
                if(value['tipo'] == 'Pluviometro'){
                    listaSensoresPluvParcial.push(value);
                    this.chartDataPropsPluv.labelsL = value['labels'];
                    datasetsPluv.push(
                        {
                        label: nombreSensor,
                        backgroundColor: listaColores[Math.floor(Math.random() * listaColores.length)],
                        fill: true,
                        data: listaMedidas
                        }
                    );
                }
                if(value['tipo'] == 'Temperatura' ){
                    listaSensoresTempParcial.push(value);
                    this.chartDataPropsTemp.labelsL = value['labels'];
                    datasetsTemp.push(
                        {
                        label: nombreSensor,
                        backgroundColor: listaColores[Math.floor(Math.random() * listaColores.length)],
                        fill: true,
                        data: listaMedidas
                        }
                    );
                }
                if(value['tipo'] == 'Humedad'){
                    listaSensoresHumParcial.push(value);
                    this.chartDataPropsHum.labelsL = value['labels'];
                    datasetsHum.push(
                        {
                        label: nombreSensor,
                        backgroundColor: listaColores[Math.floor(Math.random() * listaColores.length)],
                        fill: true,
                        data: listaMedidas
                        }
                    );
                }
            }
            this.listaSensoresPluv = listaSensoresPluvParcial;
            this.listaSensoresTemp = listaSensoresTempParcial;
            this.listaSensoresHum = listaSensoresHumParcial;
            this.chartDataPropsHum.dataL = datasetsHum;
            this.chartDataPropsPluv.dataL = datasetsPluv;
            this.chartDataPropsTemp.dataL = datasetsTemp;
            
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
                <div class="row" style="justify-content:center;border-top: 1mm solid #2162ad;">
                    <h3 style="margin-top: 10px;">Temperatura</h3>
                    <div class="row" style="justify-content:center;">
                        <div class="grafico my-4" style="max-width: 80%;"> 
                            <h4>Sensores Temperatura en el tiempo</h4>
                            <GraficoLinea :chartDataProps="chartDataPropsTemp" />
                        </div>
                    </div>
                </div>
                <div class="row" style="justify-content:center;border-top: 1mm solid #2162ad;">
                    <h3 style="margin-top: 10px;">Pluviometro</h3>
                    <div class="row" style="justify-content:center;">
                        <div class="grafico my-4" style="max-width: 80%;"> 
                            <h4>Sensores Pluviometros en el tiempo</h4>
                            <GraficoLinea :chartDataProps="chartDataPropsPluv" />
                        </div>
                    </div>
                </div>
                <div>
                    <div class="row dona"  >
                        <h3 style="margin-top: 10px;">Humedad</h3>
                        <div class="grafico my-4" v-for="(elementObj, rowIndex) in this.listaSensoresHum" :key="`eventKey_${rowIndex}`">
                            <h4>{{elementObj["nombre"]}}</h4>
                            <GraficoDona :chartDataProps="{dataL:[elementObj['datos'][0],100-elementObj['datos'][0]]}" style="height: 140px;"/>
                            <h4 style="align-self:center;margin-top: -40px;">{{elementObj["datos"][0] + '%'}}</h4>
                        </div>
                    </div>
                    <div class="row" style="justify-content:center;">
                        <div class="grafico my-4" style="max-width: 80%;"> 
                            <h4>Sensores Humedad en el tiempo</h4>
                            <GraficoLinea :chartDataProps="chartDataPropsHum" />
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </section>
</template>

<style>
    .grafico {
        display: flex;
        flex-direction: column;
        margin: 5px auto;
        max-width: 18em;
        min-height: 16em;
        overflow: hidden;
        border-radius: .5em;
        text-decoration: none;
        background: #ecebeb;
        border: 3px solid #2162ad;
        padding: 2em 2em;
        box-shadow: 0 1.5em 2.5em -.5em rgba(#000000, .1);
        transition: transform .45s ease, background .45s ease;
    } 
    
    .dona{
        justify-content:center;
        border-top: 1mm solid #2162ad;
        pointer-events: none;
    }

</style>