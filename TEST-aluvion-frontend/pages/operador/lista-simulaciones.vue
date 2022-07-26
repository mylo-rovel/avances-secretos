<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import Modal from '~/components/Modal.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';


    export default Vue.extend({
      name: "ListaSimulaciones",
      components: { PageHeader, SubmitButton, Modal, CancelButtom},
      head(){
        return{
          title: "Simulaciones - Sistema de Alerta Temprana Aluvional",
          meta: [{
            name: "simulaciones",
            content: "Simulaciones"
          }]
          //src="https://kit.fontawesome.com/yourcode.js" crossorigin="anonymous",
          }
        },
      data() {
        return {
          "tituloPag": "Sistema de Alerta Temprana Aluvional",
          "submitbutton":"volver",
          "cancelbutton":"atras",
          simulacionesEjecutadas: [],
          isModalOpened:false,
          ejecucionSeleccionada:{},
          nombre:""
        };
      },
      
      computed: mapState(["urlApi"]) ,

      async mounted() {
          checkIfUserShouldBeHere(["OPERADOR"]);
          const serverPath = `${this.urlApi}/ejecuciones/`;
          const rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const ejecucionesAcotadas = await rawdata.json();
          this.simulacionesEjecutadas = ejecucionesAcotadas["ejecucionAcotada_"];
          //console.log(this.simulacionesEjecutadas);
          //console.log();
      },
      methods: {
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        },
        async seleccionarEjecucion(elementObj) {
          const idEjecucionSeleccionada = elementObj["id_"];
          const serverPath = `${this.urlApi}/ejecuciones/${idEjecucionSeleccionada}`;
          const rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          this.ejecucionSeleccionada = await rawdata.json();
          this.isModalOpened = true;
          console.log(idEjecucionSeleccionada);
          return;ea6a01b3caf5b22ddfb655f9ba5bba41bfb66
      }}
    })
</script>

<template>
  <section id="vistaSimulaciones">
    <section id="contenidoTabla">

        <div>
          <NavbarPag :tituloPag="tituloPag"/>
        </div>
        <div class="container-header">
          <PageHeader />
        </div>
        <div class="container">
          <div class="row my-4">
            <h4>Simulaciones</h4>       
          </div> 

          <div class="my-4 table-responsive table-container">
            <table id="tablaSimulaciones" class=" tabla-simulacion table table-lifht table-bordered table-hover table-striped">
              <thead class="text-white">
                <th scope="row">Simulación</th>
                <th scope="row">Equipo</th>
                <th scope="row">Fecha</th>
                <th scope="row">Agua</th>
              </thead>
              <tbody id="tBody-simulaciones">
                <tr scope="row" v-for="(elementObj, rowIndex) in simulacionesEjecutadas" :key="`eventKey_${rowIndex}`"  @click="() => seleccionarEjecucion(elementObj)">
                  <td> {{elementObj["nombreSimulacion_"]}} </td>
                  <td> {{elementObj["nombreEquipo_"]}} </td>
                  <td> {{elementObj["fechaEjecucion_"]}} </td>
                  <td> {{elementObj["aguaCaida_"]}} </td>
                </tr>
              </tbody>
            </table>
          </div>
          <article v-if="isModalOpened" class="modal-background-container">
            <EjecucionModal 
              @changeModalToFalse="isModalOpened=false"
                :ejecucionSeleccionada="ejecucionSeleccionada" 
                :isModalOpened="isModalOpened"/>
          </article>
        </div>
      </div>
    </section>
  </section>
</template>

<style>

  .table-container {
    max-height: 50vh;
    overflow-y: scroll;
  }

  .tabla-simulacion{
    text-align: center;
    border-collapse: collapse;
    width: 100%;
  }
  thead {
    background-color: #025cfa;
    color: white;
  }
  th{
    padding: 0.5rem;
  }
  .titulo-modal{
    text-align: center;
  }
  .boton-nombre{
    background-color: #025cfa;
    color: white;

  }

  .table-container {
    max-height: 50vh;
    overflow-y: scroll;
  }

  .modal-background-container{
    z-index: 999;
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width:100vw;
    height:100vh;
    background-color: rgba(0, 0, 0, 0.8);
  }
</style>