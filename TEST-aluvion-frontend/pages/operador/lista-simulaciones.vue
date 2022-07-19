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
          }],
        }
      },
      data() {
        return {
          "tituloPag": "Sistema de Alerta Temprana Aluvional",
          "submitbutton":"volver",
          "cancelbutton":"atras",
          simulacionesEjecutadas: []
        };
      },
      
      computed: mapState(["urlApi"]) ,

      async mounted() {
          checkIfUserShouldBeHere(["OPERADOR"]);
          const request_config = { 
              method: 'get',
              headers: {'authorization': window.localStorage.getItem("token")}
          };
          const serverPath = `${this.urlApi}/ejecuciones/`;
          const rawdata = await fetch(serverPath, request_config).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const ejecucionesAcotadas = await rawdata.json();
          this.simulacionesEjecutadas = ejecucionesAcotadas["ejecucionAcotada_"];
      },
      methods: {
        logSimulacionesEjecutadas() {
          console.log(this);
          console.log(this.simulacionesEjecutadas);
        },
        verSimulacion(){
          document.querySelector(".back-modal").style.display = "flex";

        },

        /* abrirModal(){
          document.getElementById("boton_prueba").addEventListener("click",
          function() {
            document.querySelector(".back-modal").style.display = "flex";
          });
        }, */
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

        
        <div class="my-4 table-responsive table-container">
          <table id="tablaSimulaciones" class=" tabla-simulacion table table-lifht table-bordered table-hover table-striped">
            <thead class=" text-white">
              <th scope="row">Simulación</th>
              <th scope="row">Equipo</th>
              <th scope="row">Fecha</th>
              <th scope="row">Agua</th>
            </thead>
            <tbody id="tBody-simulaciones">
              <tr scope="row" v-for="(elementObj, rowIndex) in simulacionesEjecutadas" :key="`eventKey_${rowIndex}`">
                <td ><button :id='"boton-"+elementObj' class="btn" @click="verSimulacion()">{{elementObj["nombreSimulacion_"]}}</button></td>
                <td> {{elementObj["nombreEquipo_"]}} </td>
                <td> {{elementObj["fechaEjecucion_"]}} </td>
                <td> {{elementObj["aguaCaida_"]}} </td>
              </tr>
            </tbody>
          </table>
          
        </div>
        <center class = "row my-4  ">
          <div class="col-12 contenido-botones my-4">    
            <NuxtLink to="/operador/menu-operador"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
          </div>
        </center>
       <!-- <button id="boton_prueba" type="button" class="btn btn-primary" @click = "logSimulacionesEjecutadas()" >probar modal</button> -->
        <div class= "back-modal">
          <div class="modal-content my-4">
            <div class="row">
              <div class = "titulo-modal my-4">
                <h4>Configuración Simulación</h4>
              </div>
              <div>
                <div class="row my-4" v-for="(i, rowIndex) in simulacionesEjecutadas" :key="`eventKey_${rowIndex}`">
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">ID Simulación</label>
                      <div class="col-sm-4">
                        <input id="ver_id" type="text" class="form-control-plaintext" aria-describedby="addon-wrapping" readonly>
                      </div>  
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label">Nombre Simulación</label>
                    
                      <label id="ver_nombre" class="form-control-plaintext" aria-describedby="addon-wrapping">{{i["nombreSimulacion_"]}}</label>
                
                    </div>
                  </div>
                  <div class="row my-4">
                    <div class="input-group flex-nowrap">
                      <label class="col-sm-4 col-form-label" id="addon-wrapping">Equipo</label>
                      <div class="col-sm-4">
                        <label id="ver_equipo" class="form-control-plaintext" aria-describedby="addon-wrapping" >{{i["nombreEquipo_"]}}</label>             
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
                </div>
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
  .boton-nombre{
    background-color: #025cfa;
    color: white;

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

    .table-container {
      max-height: 50vh;
      overflow-y: scroll;
    }
    .atras-button {
        background: white;
        color: black;
        border: 1px solid black;
        transform: scale(1.1);
    }
</style>