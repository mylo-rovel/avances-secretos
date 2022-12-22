<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import CustomButton from '../../components/CustomButton.vue';
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { library } from "@fortawesome/fontawesome-svg-core";
    import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
    import { fas } from "@fortawesome/free-solid-svg-icons/";
    import '@fortawesome/fontawesome-svg-core/styles.css';
    import GraficoLinea from '~/components/GraficoLinea.vue';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import { infoGeneral,listaColores } from '../../utils/charts_utils'
    library.autoAddCss = false;
    library.add(fas);
    Vue.config.productionTip = false;
    export default Vue.extend({
      name: "Graficos",
      components: { FontAwesomeIcon, GraficoLinea, CustomButton, SideBar },
      head(){
        return{
          title: "Graficos - Sistema de Alerta Temprana Aluvional",
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
            "Gráficos Sensores Pluviométricos",
            "Gráficos Sensores Temperatura",
            "Gráficos Sensores Humedad",
          ],
          tiposSensores:[
            "Pluviometro",
            "Temperatura",
            "Humedad"
          ],
          idSimEjecutada:"",
          nombreEquipo: "",
          nombreSimulacion:"",
        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          const queryString = window.location.search;
          const urlParams = new URLSearchParams(queryString);
          const graphIndex = urlParams.get('g');
          this.nombrePag = this.nombresPag[graphIndex];
          this.idSimEjecutada = urlParams.get('id');
          let datasets = [];
          
          this.listaSensores = infoGeneral['listaSensores'];
          this.nombreEquipo = infoGeneral['nombreEquipo'];
          this.nombreSimulacion = infoGeneral['nombreSimulacion'];
          for (let value of infoGeneral['listaSensores']) {
            const listaMedidas = value["datos"];
            const nombreSensor = value["nombre"];
            if(this.tiposSensores[graphIndex] == value['tipo']){
              this.chartDataProps.labelsL = value['labels'];
              datasets.push(
                {
                  label: nombreSensor,
                  backgroundColor: listaColores[Math.floor(Math.random() * listaColores.length)],
                  fill: true,
                  data: listaMedidas
                }
              );
            }
          }
          this.chartDataProps.dataL = datasets;
      },
      methods: {
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        },
        exportarCSV(){
          let dataAExportar = [];
          const queryString = window.location.search;
          const urlParams = new URLSearchParams(queryString);
          const graphIndex = urlParams.get('g');
          for(let sensor of this.listaSensores){
            if(this.tiposSensores[graphIndex] == sensor['tipo']){
              for (let i = 0; i < sensor['datos'].length; i++) {
                const element = [sensor['nombre'],sensor['datos'][i],sensor['labels'][i]];
                dataAExportar.push(element);
              }
            }
          }
          let csvContent = "data:text/csv;charset=utf-8," + dataAExportar.map(e => e.join(",")).join("\n");
          let encodedUri = encodeURI(csvContent);
          let link = document.createElement("a");
          link.setAttribute("href", encodedUri);
          link.setAttribute("download", this.nombreSimulacion + this.nombreEquipo + + ".csv");
          document.body.appendChild(link);
          link.click();
          console.log(dataAExportar);
        }
        
      }
    })

</script>

<template>
  <section id="vistaResumen">
    <SideBar/>
    <div class="container">
        <div class="row">
          <div class="my-4" >
            <NuxtLink :to="'resumen?id='+ this.idSimEjecutada">
              <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
            </NuxtLink>     
          </div>
          
          <h3 style="border-bottom: 1mm solid #2162ad;">{{this.nombrePag}}</h3>
          <div class="container">
            <div class="my-4">
              <CustomButton :text="'Descargar CSV'" :custombcolor="'#1c94e4fd'" :customhcolor="'#13659b'" @click.native="exportarCSV()"/>
            </div>
          </div>
          <div class="grafico my-4" style="max-width: 100%;">
              <GraficoLinea :chartDataProps="chartDataProps" />
          </div>
        </div>
    </div>
    
  </section>
</template>


<style>
  .test{
    background-color: #7f8a99;
    color:#575c63;
  }

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