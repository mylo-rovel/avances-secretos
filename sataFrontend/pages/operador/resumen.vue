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
    library.autoAddCss = false;
    library.add(fas);
    Vue.config.productionTip = false;
    export default Vue.extend({
      name: "Resumen",
      components: { PageHeader, FontAwesomeIcon },
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
        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          let serverPath = `${this.urlApi}/ejecuciones/`;
          let rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const ejecucionesAcotadas = await rawdata.json();
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
    <div>
      <NavbarPag :tituloPag="tituloPag"/>
    </div>
    <div class="container-header">
      <PageHeader />
    </div>
    <div class="container">
      <div class="row my-4">
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
            <h4>Nombre Equipo: Genesis143</h4>  
            <h4>Nombre Simulación: Lluvia de 1932 </h4>
            <h4>Cantidad Sensores: 4</h4>
            <h4>Duración de la Simulación: 30 minutos 21 segundos</h4>
        </div>
      </div>
      <div class="row" style="justify-content: space-between;">
        <a class="data-card my-4" href="graficos?id=1&g=0" >
            <h3>30mm</h3>
            <h4>Agua Caída Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
        <a class="data-card my-4" href="graficos?id=1&g=1" >
            <h3>28°C</h3>
            <h4>Temperatura Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
        <a class="data-card my-4" href="graficos?id=1&g=2" >
            <h3>10 psi</h3>
            <h4>Presión De Válvula Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
        <a class="data-card my-4" href="graficos?id=1&g=3" >
            <h3>64%</h3>
            <h4>Humedad Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
        <a class="data-card my-4" href="graficos?id=1&g=4" >
            <h3>1013 hPa</h3>
            <h4>Presión Atmosférica Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
        <a class="data-card my-4" href="graficos?id=1&g=5" >
            <h3>12 m³/s</h3>
            <h4>Caudal Promedio</h4>
            <span class="link-text">
              Ver Gráficos 
              <font-awesome-icon icon="arrow-right" />
            </span>
        </a>
      </div>
      <div class="row">
        <div class="data-card my-4" style="max-width: 100%;pointer-events: none;">
            <h3>Lista de Sensores</h3>
            <table>
              <thead>
                <th scope="row">Nombre</th>
                <th scope="row">Unidad</th>
              </thead>
              <tbody>
                <tr scope="row">
                    <td> Sensor Temperatura 1 </td>
                    <td> C° </td>
                </tr>
                <tr scope="row">
                    <td> Sensor Temperatura 2 </td>
                    <td> C° </td>
                </tr>
                <tr scope="row">
                    <td> Sensor Válvula 1 </td>
                    <td> psi </td>
                </tr>
                <tr scope="row">
                    <td> Sensor Humedad 1 </td>
                    <td> % </td>
                </tr>
                <tr scope="row">
                    <td> Sensor Caudal 1 </td>
                    <td> m³/s </td>
                </tr>

              </tbody>
            </table>
        </div>
      </div>
    </div>
  </section>
</template>


<style>
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