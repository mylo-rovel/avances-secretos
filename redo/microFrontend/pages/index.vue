<!-- eslint-disable dot-notation -->
<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import GraficoBarras from '../components/charts/GraficoBarras.vue';
    import GraficoLinea from '../components/charts/GraficoLinea.vue';
    import GraficoDona from '../components/charts/GraficoDona.vue';
    import GraficoPie from '../components/charts/GraficoPie.vue';
    import PageHeader from '../components/PageHeader.vue';
    import NavbarPag from '../components/NavbarPag.vue';

    export default Vue.extend({
        name: "VerSimulacion",
        components: { 
            GraficoBarras, 
            GraficoLinea, 
            GraficoDona, 
            GraficoPie,
            PageHeader,
            NavbarPag
        },
        data() {
            return {
                chartDataProps:{x:[], y:[]}
            };
        },
        computed: mapState(["urlApi"]),
        mounted() {
            window.setInterval(this.getMasDatosEjecucion, 1000);
        },
        methods: {
            getMedidasProperties (rawMedidasArr) {
                /* 
                objetoRespuesta: IMedida[]
                interface IMedida {
                    senial: number;
                    fisico: number;
                    timestamp: number;
                }
                */
                return rawMedidasArr.reduce((acc, medidaObj) => {
                    acc.seniales.push(medidaObj['senial']);
                    acc.fisicos.push(medidaObj['fisico']);
                    acc.timestamps.push(medidaObj['timestamp']);
                    return acc;
                }, {seniales: [], fisicos: [], timestamps: []})
            },
            async getMasDatosEjecucion(){
                const rawReponse = await fetch(`${this.urlApi}/ejecuciones/ultimos`).catch(err => err);
                if (rawReponse instanceof Error) {
                    console.log("❌ Error al pedir datos del grafico ❌");
                    return;
                }
                const {fisicos, timestamps} = this.getMedidasProperties(await rawReponse.json());
                this.chartDataProps.x = timestamps;
                this.chartDataProps.y = fisicos;
                console.log(fisicos, timestamps);
            },
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
            <div id="verSimulacionGrafico">
                <GraficoBarras :chartDataProps="chartDataProps" />
                <GraficoLinea :chartDataProps="chartDataProps" />
                <GraficoDona :chartDataProps="chartDataProps" />
                <GraficoPie :chartDataProps="chartDataProps" />
            </div>
        </div>
    </section>
</template>

<style>
    #verSimulacionGrafico {
        display: flex;
        flex-wrap: wrap;
        margin: 0 auto;
    }

    .myChart {
        width: 50%;
        border: 1px solid black;
        margin-top: 20px;
    }
    #verSimulacionGrafico .myChart:nth-child(odd) {
        border-right: 0;
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
        background-color: rgba(2,92,250,1);        
    }
    .cancelarButton {
        background-color: rgba(199,0,57,1);
    }

    .sendDataButton:hover {
        color: rgba(2,92,250,1);
        border: 1px solid rgba(2,92,250,1);
        background-color:white;
        transition: all 0.4s;
        transform: scale(1.1);
    }
    .cancelarButton:hover {
        color: rgba(199,0,57,1);
        border: 1px solid rgba(199,0,57,1);
        background-color:white;
        transition: all 0.4s;
        transform: scale(1.1);
    }


</style>