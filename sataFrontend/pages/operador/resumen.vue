<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import CustomButton from '../../components/CustomButton.vue';
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { library } from "@fortawesome/fontawesome-svg-core";
    import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
    import { fas } from "@fortawesome/free-solid-svg-icons/";
    import '@fortawesome/fontawesome-svg-core/styles.css';
    import SideBar from '../../components/SideBar/SideBar.vue';
    import { infoGeneral } from '../../utils/charts_utils'
    library.autoAddCss = false;
    library.add(fas);
    Vue.config.productionTip = false;
    export default Vue.extend({
      name: "Resumen",
      components: { FontAwesomeIcon, SideBar },
      head(){
        return{
          title: "Simulaciones - Sistema de Alerta Temprana Aluvional",
          meta: [{
            name: "resumen",
            content: "Resumen"
          }]
          //src="https://kit.fontawesome.com/yourcode.js" crossorigin="anonymous",
          }
        },
      data() {
        return {
          "tituloPag": "Sistema de Alerta Temprana Aluvional",
          nombreEquipo: "",
          nombreSimulacion:"",
          listaSensores:[],
          duracionSimulacion:"",
          aguaPromedio:0,
          tempPromedio:0,
          humedadPromedio:0,
          idSimEjecutada:"",
          cantSensoresHum: 0,
          cantSensoresPluv: 0,
          cantSensoresTemp:0,

        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          const queryString = window.location.search;
          const urlParams = new URLSearchParams(queryString);
          this.idSimEjecutada = urlParams.get('id');
          this.nombreEquipo = infoGeneral['nombreEquipo'];
          this.nombreSimulacion = infoGeneral['nombreSimulacion'];
          this.duracionSimulacion = infoGeneral['duracionSimulacion'];
          this.listaSensores = infoGeneral['listaSensores'];
          let aguaPromedioParcial = 0;
          let tempPromedioParcial = 0;
          let humPromedioParcial = 0;
          let cantSensoresPluvParcial = 0;
          let cantSensoresTempParcial = 0;
          let cantSensoresHumParcial = 0;
          for (let value of this.listaSensores) {
            const element = value["datos"];
            if(value['tipo'] == 'Pluviometro'){
              aguaPromedioParcial += (element.reduce((a, b) => a + b, 0) / element.length);
              cantSensoresPluvParcial +=1;
            }
            if(value['tipo'] == 'Temperatura'){
              tempPromedioParcial += (element.reduce((a, b) => a + b, 0) / element.length);
              cantSensoresTempParcial +=1;
            }
            if(value['tipo'] == 'Humedad'){
              humPromedioParcial += (element.reduce((a, b) => a + b, 0) / element.length);
              cantSensoresHumParcial +=1;
            }
          }
          this.aguaPromedio = (aguaPromedioParcial/cantSensoresPluvParcial).toFixed(1);
          this.tempPromedio = (tempPromedioParcial/cantSensoresTempParcial).toFixed(0);
          this.humedadPromedio = (humPromedioParcial/cantSensoresHumParcial).toFixed(0);
          this.cantSensoresPluv = cantSensoresPluvParcial;
          this.cantSensoresTemp = cantSensoresTempParcial;
          this.cantSensoresHum = cantSensoresHumParcial;
          
          
      },
      methods: {
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        }}
    })

</script>

<template>
  <section id="vistaResumen">
    <SideBar/>
    <div class="container">
      <div class="row">
        <div class="my-4" >
            <NuxtLink :to="'lista-ejecuciones'">
              <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
            </NuxtLink>     
        </div>
        <h3>Resumen Simulación</h3>       
      </div>
    </div>
    <div class="container">
      <div class="my-4">
        <CustomButton :text="'Descargar CSV'" :custombcolor="'#1c94e4fd'" :customhcolor="'#13659b'"/>
        <CustomButton :text="'Descargar PDF'" :custombcolor="'#1c94e4fd'" :customhcolor="'#13659b'"/>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="data-card my-4" style="max-width: 100%;pointer-events: none;">
            <h3>Información General</h3>
            <h4>Nombre Equipo: {{this.nombreEquipo}}</h4>  
            <h4>Nombre Simulación: {{this.nombreSimulacion}} </h4>
            <h4>Cantidad Sensores: {{this.listaSensores.length}}</h4>
            <h4>Duración de la Simulación: {{this.duracionSimulacion}}</h4>
        </div>
      </div>
      <div class="row" style="justify-content: space-between;">
        <NuxtLink class="data-card my-4" :to="'graficos?id='+this.idSimEjecutada+'&g=0'" > 
            <h3>{{this.aguaPromedio + ' mm'}}</h3>
            <h4>Agua Caída Promedio</h4>
            <h4>{{this.cantSensoresPluv + ' Sensor(es)'}}</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </NuxtLink>
        <NuxtLink class="data-card my-4" :to="'graficos?id='+this.idSimEjecutada+'&g=1'" >
            <h3>{{this.tempPromedio + '°C'}}</h3>
            <h4>Temperatura Promedio</h4>
            <h4>{{this.cantSensoresTemp + ' Sensor(es)'}}</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </NuxtLink>
        <NuxtLink class="data-card my-4" :to="'graficos?id='+this.idSimEjecutada+'&g=2'" >
            <h3>{{this.humedadPromedio + '%'}}</h3>
            <h4>Humedad Promedio</h4>
            <h4>{{this.cantSensoresHum + ' Sensor(es)'}}</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </NuxtLink>
      </div>
    </div>
  </section>
</template>


<style>

  .h3{
    user-select: none;
  }
  .data-card thead {
    color: white;
    border-bottom: 2px solid #2162ad;
  }
  .data-card th{
    padding: 0.5rem;
    color: #7f8a99;
  }

  .data-card {
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
  .data-card h3 {
    color: #2E3C40;
    font-size: 3.5em;
    font-weight: 600;
    line-height: 1;
    padding-bottom: .5em;
    margin: 0 0 0.142857143em;
    border-bottom: 2px solid #2162ad;
    transition: color .45s ease, border .45s ease;
  }

  .data-card h4 {
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
    color: #2162ad;
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

  .data-card:hover {
    background: #2162ad;
    transform: scale(1.02);
  }  
  .data-card:hover h3 {
      color: #FFFFFF;
      border-bottom-color: #5363f7;
  }

  .data-card:hover h4 {
    color: #FFFFFF;
  }

  .data-card:hover p {
    opacity: 1;
    transform: none;
  }

  .data-card:hover span {
    color: #FFFFFF;
  }

  

</style>