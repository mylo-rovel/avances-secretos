<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import Modal from '~/components/Modal.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { bPagination, bTable } from 'bootstrap-vue'

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
          simulacionesEjecutadasConFiltro: [],
          simulacionesEjecutadas: [],
          listaEquipos: [],
          isModalOpened:false,
          ejecucionSeleccionada:{},
          nombre:"",
          filtro:""
        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          const serverPath = `${this.urlApi}/ejecuciones/`;
          const rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const ejecucionesAcotadas = await rawdata.json();
          this.simulacionesEjecutadas = ejecucionesAcotadas["ejecucionAcotada_"];
          this.simulacionesEjecutadasConFiltro = [... this.simulacionesEjecutadas];
          const serverPath2 = `${this.urlApi}/equipos/`;
          const rawdata2 = await fetch(serverPath2, this.getRequestConfig()).catch(err => err);
          if (rawdata2 instanceof Error) { return false; }
          const equiposAcotados = await rawdata2.json();
          this.listaEquipos = equiposAcotados["equipoAcotado_"];
          console.log(this.listaEquipos);
          //console.log(this.simulacionesEjecutadas);
          //console.log();

      },
      methods: {
        filtarPorEquipo(){
          this.simulacionesEjecutadasConFiltro = this.simulacionesEjecutadas.filter((item) => {
            return item["nombreEquipo_"].includes(this.filtro);
          })
        },
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
          if (rawdata instanceof Error) { 
            console.log(rawdata);
            return false; 
          }
          this.ejecucionSeleccionada = await rawdata.json();
          this.isModalOpened = true;
          console.log(idEjecucionSeleccionada);
          return;
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
          <div class="row my-6">
            <h5>Filtro</h5>       
          </div>
        <div class="rcorners">
          <div class="row my-6">
            <h6>Equipo</h6>      
            <select class="options" v-model="filtro" @click="filtarPorEquipo()" v-for="(elementObj, rowIndex) in listaEquipos" :key="`eventKey_${rowIndex}`">
              <option value="">Seleccione una opción</option>
              <option>{{elementObj["nombre_"]}}</option>
            </select>
          </div>
        </div>
          <div class="my-4 table-responsive table-container">
            <table id="tablaSimulaciones" class="tabla-simulacion table table-lifht table-bordered table-hover table-striped">
              <thead class="text-white">
                <th scope="row">Simulación</th>
                <th scope="row">Equipo</th>
                <th scope="row">Fecha</th>
                <th scope="row">Estadísticas</th>
              </thead>
              <tbody id="tBody-simulaciones">
              <tr scope="row" v-for="(elementObj, rowIndex) in simulacionesEjecutadasConFiltro" :key="`eventKey_${rowIndex}`">
                  <td> {{elementObj["nombreSimulacion_"]}} </td>
                  <td> {{elementObj["nombreEquipo_"]}} </td>
                  <td> {{elementObj["fechaEjecucion_"]}} </td>
                  <td> <CustomButton :text="'Ver'" :custombcolor="'#68da85'" customhcolor="#3c724a"/> </td>
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
    </section>
  </section>
</template>


<style>
  .table-container {
    max-height: 50vh;
    overflow-y: scroll;
  }
  .tabla-simulacion{
    background-color: white;
    text-align: center;
    border-collapse: collapse;
    width: 100%;
  }
  thead {
    background-color: #4e89f0;
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
  .rcorners{
    border-radius: 25px;
    border: 3px solid #2162ad;
    padding: 20px;
    width: 100%;
    height: 110px;
  }
  .options{
    width: 298px;
    height: 40px;
    margin-left: 10px;
    background-color: #fff;
    border-radius: 4px;
  }


</style>