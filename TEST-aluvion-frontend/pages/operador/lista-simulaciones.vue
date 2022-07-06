<script lang="js">

import Vue from 'vue';
import {mapState, mapMutations} from "vuex";
import PageHeader from '~/components/PageHeader.vue'
import SubmitButton from '~/components/SubmitButton.vue'
import Modal from '~/components/Modal.vue'

export default Vue.extend({
  name: "ListaSimulaciones",
  components: { PageHeader, SubmitButton, Modal },
  data() {
    return {
      "tituloPag": "Sistema de Alerta Temprana Aluvional",
      "submitbutton":"abrir",
      simulaciones: []
    };
  },
  computed: mapState(["urlApi"]) ,
  async fetch(){
    
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
  
    //FUNCION EN LA QUE RELLENA LAS FILAS DE LA TABLA CON LOS DATOS DE SIMULACION (ID, NOMBRE, EQUIPO)
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
        nombreSimulacionCol.innerHTML = '<SubmitButton id=button-verConfSimulacion-' + i + ' onClick="verConfiguracionSimulacion(this.id);" <i class="fas fa-edit"></i>' + nombreSimulacion + '/>';
        equipoSimulacionCol.textContent = equipoSimulacion;
      }*/
    },
    
    //FUNCION EN LA QUE SE PUEDE VER LOS DATOS DE UNA SIMULACION DESDE TOCANDO EL NOMBRE DE LA SIMULACION EN LA TABLA
    /*verConfiguracionSimulacion(confSimulacion1){
        document.querySelector(".back-modal").style.display = "flex";
        var confSimulacion2 = confSimulacion1.split("-");
        console.log(simulaciones[(parseInt(confSimulacion2[2]))]);
        document.getElementById('ver_id').value = simulaciones[(parseInt(confSimulacion2[2]))].id_;
        document.getElementById('ver_nombre').value = simulaciones[(parseInt(confSimulacion2[2]))].nombre_;
        document.getElementById('ver_equipo').value = simulaciones[(parseInt(confSimulacion2[2]))].equipo_;
        document.getElementById('ver_descricpcion').value = simulaciones[(parseInt(confSimulacion2[2]))].descripcion_;
        document.getElementById('ver_estado').value = simulaciones[(parseInt(confSimulacion2[2]))].estado_;
    },*/
    
    abrirModal(){
      document.getElementById("boton_prueba").addEventListener("click",
      function() {
        document.querySelector(".back-modal").style.display = "flex";
      });
    },
    cerrarModal(){
      document.querySelector(".close-modal").addEventListener("click",
      function() {
        document.querySelector('.back-modal').style.display = "none";
      });
    },
  }
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
        <div class="my-4 table-responsive">
          <table id="tablaSimulaciones" class=" tabla-simulacion table table-lifht table-bordered table-hover table-striped">
            <thead class=" text-white">
              <th scope="row">ID</th>
              <th scope="row">Simulación</th>
              <th scope="row">Equipo</th>
              <th scope="row">Ver</th>
            </thead>
            <tbody id="tBody-simulaciones"></tbody>
          </table>
        </div>
        <button id="boton_prueba" type="button" class="btn btn-primary" @click = "abrirModal()" >probar modal</button> 
        <div class= "back-modal">
          <div class="modal-content my-4">
            <div class="row">
              <div class = "titulo-modal my-4">
                <h4>Configuración Simulación</h4>
              </div>
              <div class="row my-4">
                <form action="/ver-conf-simulacion" method="get">
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">ID Simulación</label>
                      <div class="col-sm-4">
                        <input id="ver_id" name="id" type="text" class="form-control-plaintext" aria-describedby="addon-wrapping" readonly>
                      </div>  
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Nombre Simulación</label>
                      <div class="col-sm-4">
                        <input id="ver_nombre" name="nombre" type="text" class="form-control-plaintext" aria-describedby="addon-wrapping" readonly>
                      </div>
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Equipo</label>
                      <div class="col-sm-4">
                        <input id="ver_equipo" name="equipo" type="text" class="form-control-plaintext" aria-describedby="addon-wrapping" readonly>
                      </div>
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Secuencias</label>
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Descripción</label>
                      <div class="col-sm-4">
                        <input id="ver_descripcion" name="descripcion" type="text" class="form-control-plaintext"  aria-describedby="addon-wrapping" readonly>
                      </div>  
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Estado</label>
                      <div class="col-sm-4">
                        <input id="ver_estado" name="estado" type="text" class="form-control-plaintext"  aria-describedby="addon-wrapping" readonly>
                      </div>
                    </div>
                  </div>           
                </form>
              </div>
            </div>
            <div class ="close-modal" @click="cerrarModal()">+</div>
          </div>
        </div>
      </div>
    </section>

  </section>

</template>
<style>
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
  .back-modal {
        display: none;
        width: 100%;
        height: 100%;
        background-color: #646464;
        position: relative;
        top: 0;
        justify-content: center;
        align-items: center;
    }
    .modal-content {
        /*width: 500px;
        height: 300px;*/
        background-color: white;
        border-radius: 10px;
        padding: 5px;
        position: relative;
        margin-left: 40px;
        margin-right: 40px;
    }
    .close-modal {
        position: absolute;
        top: 0;
        right: 14px;
        font-size: 42px;
        transform: rotate(45deg);
        cursor: pointer;
    }
</style>