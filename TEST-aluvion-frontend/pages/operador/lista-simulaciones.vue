<script lang="js">

import Vue from 'vue';
import {mapState, mapMutations} from "vuex";
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
  name: "ListaSimulaciones",
  components: { PageHeader },
  data() {
    return {
      "tituloPag": "Sistema de Alerta Temprana Aluvional",
      simulaciones: []
    };
  },
  computed: mapState(["urlApi"]) ,
  async fetch(){
    this.simulaciones = await fetch(`${this.urlApi}/simulaciones`).then(res => {
      console.log(res.json());
      return res.json();
    });
  },
  head(){
    return{
      title: "Simulaciones - Sistema de Alerta Temprana Aluvional",
      meta: [{
        name: "simulaciones",
        content: "Simulaciones"
      }],
    }
  },
  methods: {

    async obtenerSimulacionesTabla(simulaciones){
      let tBodySimulaciones = document.getElementById("tBody-simulaciones");

      /*for(let i = 0; i < simulaciones.length; i++){
        const idSimulacion = simulaciones[i].id_;//id desde la bd            Este id va en nombreSimulacionCol
        const nombreSimulacion = simulaciones[i].nombre_; //nombre desde la bd 
        const equipoSimulacion = simulaciones[i].nombreEquipo_; //equipo desde la bd 

        let simulacionRow = tBodySimulaciones.insertRow(-1);
        let idSimulacionCol= simulacionRow.insertCell(-1);
        let nombreSimulacionCol = simulacionRow.insertCell(-1);
        let equipoSimulacionCol = simulacionRow.insertCell(-1);

        idSimulacionCol.textContent = idSimulacion;
        nombreSimulacionCol.innerHTML = '<SubmitButton id=button-verConfSimulacion-' + i + ' onClick="verSimulacion(this.id);" <i class="fas fa-edit"></i>' + nombreSimulacion + '/>';
        equipoSimulacionCol.textContent = equipoSimulacion;
      }*/
    },

  }
})
</script>

<template>
  <section>
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
      <div class="my-4 table-responsive">
        <table id="tabla-simulaciones" class="table table-lifht table-bordered table-hover table-striped">
          <thead class=" text-white">
            <th scope="row">ID</th>
            <th scope="row">Simulación</th>
            <th scope="row">Equipo</th>
            <th scope="row">Ver</th>
          </thead>
          <tbody id="tBody-simulaciones"></tbody>
        </table>
      </div>
    </div>
  </section>

</template>
<style>
  table{
    background-color: gray;
    text-align: center;
    border-collapse: collapse;
    width: 100%;
  }
  thead {
    background-color: #025cfa;
    color: white;

  }
  th {
    border: solid 1px black;
    padding: 0.5rem;

  }
  .white-box-transparent {
    border-radius: 2%;
    margin: 1%;
    padding: 2%;
    background: rgba(245, 245, 245, 0.6);
}
</style>