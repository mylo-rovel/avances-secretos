<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { library } from "@fortawesome/fontawesome-svg-core";
    import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
    import { fas } from "@fortawesome/free-solid-svg-icons/";
    import '@fortawesome/fontawesome-svg-core/styles.css';
    import GraficoLinea from '~/components/GraficoLinea.vue';
    library.autoAddCss = false;
    library.add(fas);
    Vue.config.productionTip = false;
    export default Vue.extend({
      name: "Graficos",
      components: { PageHeader, FontAwesomeIcon, GraficoLinea },
      head(){
        return{
          title: "Simulaciones - Sistema de Alerta Temprana Aluvional",
          meta: [{
            name: "graficos",
            content: "Graficos",       
          }]
        }
      },
      data() {
        return {
          "tituloPag": "Sistema de Alerta Temprana Aluvional",
          chartDataProps:{labelsL:[], dataL:[], optionsL:[]},
          nombrePag: "",
          nombresPag: [
            "Graficos Sensores Pluviometricos",
            "Graficos Sensores Temperatura",
            "Graficos Sensores Presión Valvula",
            "Graficos Sensores Humedad",
            "Graficos Sensores Presion Atmosferica",
            "Graficos Sensores Caudal"
          ]
        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          const queryString = window.location.search;
          const urlParams = new URLSearchParams(queryString);
          const graphIndex = urlParams.get('g');
          this.nombrePag = this.nombresPag[graphIndex];

          // switch(graphType){
          //   case "pluviometro":
            
          //   case "presionAtm":

          //   case "presionValv":

          //   case "temp":

          //   case "caudal":

          //   case "humedad":

          //}

      },
      methods: {
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        },
        getMedidasProperties (rawMedidasArr) {   
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
        }
      }
    })

</script>

<template>

  <section id="vistaResumen">
    <div>
      <NavbarPag :tituloPag="tituloPag"/>
    </div>
    <div class="container-header">
      <PageHeader />
    </div>
    <div class="container">
      <div class="row my-4">
        <!-- <h3>{{this.nombrePag}}</h3>        -->
      </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="grafico my-4" style="max-width: 100%;">
                <GraficoLinea/>
            </div>
        </div>
    </div>
   
  </section>
</template>


<style>
  .grafico thead {
    color: white;
    border-bottom: 2px solid #2162ad;
  }
  .grafico th{
    padding: 0.5rem;
    color: #7f8a99;
  }

  .grafico {
    display: flex;
    flex-direction: column;
    max-width: 22em;
    min-height: 22em;
    overflow: hidden;
    border-radius: .5em;
    text-decoration: none;
    background: #ecebeb;
    border: 3px solid #2162ad;
    padding: 2.75em 2.5em;
    box-shadow: 0 1.5em 2.5em -.5em rgba(#000000, .1);
    transition: transform .45s ease, background .45s ease;
  } 
  .grafico h3 {
    color: #2E3C40;
    font-size: 3.5em;
    font-weight: 600;
    line-height: 1;
    padding-bottom: .5em;
    margin: 0 0 0.142857143em;
    border-bottom: 2px solid #2162ad;
    transition: color .45s ease, border .45s ease;
  }

  .grafico h4 {
    color: #7f8a99;
    text-transform: uppercase;
    font-size: 1.125em;
    font-weight: 700;
    line-height: 1;
    letter-spacing: 0.1em;
    margin: 0 0 1.777777778em;
    transition: color .45s ease;
  }

  .link-text {
    display: block;
    color: rgb(33, 98, 173);
    font-size: 1.125em;
    font-weight: 600;
    line-height: 1.2;
    margin: auto 0 0;
    transition: color .45s ease;
  }

  .options{
    width: 12vw;
    height: 40px;
    margin-left: 10px;
    background-color: #fff;
    border-radius: 4px;
  }
  .vistaResumen{
    background-color: #928d8d;
  }

</style>