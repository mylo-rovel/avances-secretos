<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import Modal from '~/components/Modal.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { BootstrapVue, IconsPlugin, BPagination } from 'bootstrap-vue'

    export default Vue.extend({
      name: "ListaSimulaciones",
      components: { PageHeader, Modal, BPagination },
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
          listaFechas: [],
          isModalOpened:false,
          ejecucionSeleccionada:{},
          nombre:"",
          filtroEquipo:"",
          filtroAnno:"",
          filtroMes:"",
          porPantalla:8,
          pagActual:1,
          first:0,
          last:8,
          cantPag:1
          // showLess:false
        };
      },
      
      computed: mapState(["urlApi"]),

      async mounted() {
          //checkIfUserShouldBeHere(["OPERADOR"]);
          let serverPath = `${this.urlApi}/ejecuciones/`;
          let rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const ejecucionesAcotadas = await rawdata.json();
          this.simulacionesEjecutadas = ejecucionesAcotadas["ejecucionAcotada_"];
          this.simulacionesEjecutadasConFiltro = [... this.simulacionesEjecutadas];
          this.cantPag = Math.round(this.simulacionesEjecutadasConFiltro.length/8);
          // this.showLess = this.cantPag <= 8;
          this.listaFechas = this.simulacionesEjecutadas['fechaEjecucion_'];
          //console.log(this.simulacionesEjecutadasConFiltro);
          serverPath = `${this.urlApi}/equipos/`;
          rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const equiposAcotados = await rawdata.json();
          this.listaEquipos = equiposAcotados["equipoAcotado_"];
          //TODO:Crear consulta para que devuelva una lista unica de los años en que se ejecutaron las simulaciones
      },
      methods: {
        cambiarPagina(){
          // console.log(this.$refs['pages'][this.pagActual-1]);
          // this.$refs['pages'][this.pagActual-1].classList.remove('active');
          console.log(this.pagActual);
          console.log(this.$refs.pagination);
          console.log(this.$refs.pagination.value);
          this.first = (this.pagActual-1) * this.porPantalla;
          this.last = (this.pagActual) * this.porPantalla;
          // this.$refs['pages'][this.pagActual-1].classList.add('active');
          // console.log(this.$refs['pages']);
        },
        filtrar(){
          //Filtro por nombre equipo
          this.simulacionesEjecutadasConFiltro = this.simulacionesEjecutadas.filter((item) => {
            return item["nombreEquipo_"].includes(this.filtroEquipo);
          })
          //Filtro por año ejecucion
          if(this.filtroAnno == ""){
            //Si no hay un año seleccionado restablesco el valor del filtro mes y deshabilito la seleccion de mes
            this.filtroMes = "";
            this.$refs.mesRef.disabled =  1;
          }else{
            //Si hay un valor de año seleccionado habilito la seleccion de mes
            this.$refs.mesRef.disabled =  0;
          }
          this.simulacionesEjecutadasConFiltro = this.simulacionesEjecutadasConFiltro.filter((item) => {
            return item["fechaEjecucion_"].includes(this.filtroAnno);
          })
          //Filtro mes ejecucion
          //Debido que la fecha se encuentra en string se necesita simular el formato "Año-Mes" para filtrar
          let stringFilter = this.filtroAnno
          if(this.filtroMes != ""){
            //al año se le agrega un "-" y PadStart permite que los numeros de solo 1 digito se les agregue un 0 antes (Ej: 4 -> 04)
            stringFilter = this.filtroAnno + "-" + this.filtroMes.padStart(2, '0');
          }
          this.simulacionesEjecutadasConFiltro = this.simulacionesEjecutadasConFiltro.filter((item) => {
            return item["fechaEjecucion_"].includes(stringFilter);
          })
          this.cantPag = Math.round(this.simulacionesEjecutadasConFiltro.length/this.porPantalla);
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
          <div class="ul_top">
            <li>
              <h6>Equipo</h6>      
              <select class="options" v-model="filtroEquipo" @change="filtrar()">
                <option value="">Seleccione una opción</option>
                <option v-for="(elementObj, rowIndex) in listaEquipos" :key="`eventKey_${rowIndex}`">{{elementObj["nombre_"]}}</option>
              </select>
            </li>
            <li>
              <h6>Año</h6>      
              <select class="options" v-model="filtroAnno" @change="filtrar()">
                <option value="">Seleccione una opción</option>
                <option>2022</option>
              </select>
            </li>
            <li>
              <h6>Mes</h6>      
              <select disabled="True" ref="mesRef" class="options" v-model="filtroMes" @change="filtrar()">
                <option value="">Seleccione una opción</option>
                <option v-for="index in 12">{{index}}</option>
              </select>
            </li>
            <!-- <li>
              <h6>Ordenar Por</h6>      
              <select class="options">
                <option value="">Seleccione una opción</option>
                <option>Nombre Equipo</option>
              </select>
            </li> -->
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
              <tr scope="row" v-for="(elementObj, rowIndex) in simulacionesEjecutadasConFiltro.slice((this.pagActual-1) * this.porPantalla,(this.pagActual) * this.porPantalla)" :key="`eventKey_${rowIndex}`">
                  <td> {{elementObj["nombreSimulacion_"]}} </td>
                  <td> {{elementObj["nombreEquipo_"]}} </td>
                  <td> {{elementObj["fechaEjecucion_"]}} </td>
                  <td> <CustomButton :text="'Ver'" :custombcolor="'#68da85'" customhcolor="#3c724a"/> </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- <article v-if="isModalOpened" class="modal-background-container">
            <EjecucionModal 
              @changeModalToFalse="isModalOpened=false"
                :ejecucionSeleccionada="ejecucionSeleccionada" 
                :isModalOpened="isModalOpened"/>
          </article> -->
          <!-- <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-end" >
              <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li class="page-item" v-for="index in this.cantPag" @click="cambiarPagina(index)"><a class="page-link">{{index}}</a></li>
              <li class="page-item">
                <a class="page-link" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav> -->
          <!-- <nav aria-label="Page navigation">
          <ul class="pagination justify-content-end" >
            <li class="page-item"><a class="page-link"><span>&laquo;</span></a></li>
            <li v-show="showLess" class="page-item" v-for="index in this.cantPag" @click="cambiarPagina(index)" ref="pages"><a class="page-link">{{index}}</a></li>
            <li class="page-item"><a class="page-link"><span>&raquo;</span></a>
            </li>
          </ul>
        </nav> -->
        <b-pagination ref="pagination" class="justify-content-end" v-model="pagActual"
        :total-rows="this.simulacionesEjecutadasConFiltro.length"
        :per-page="porPantalla"
        first-number
        last-number></b-pagination>
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
  
  .ul_top {
    display: flex;
    justify-content: space-between;
    list-style-type: none;
    padding: 0;
    width: 90%;
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
    margin: auto;
  }
  .options{
    width: 12vw;
    height: 40px;
    margin-left: 10px;
    background-color: #fff;
    border-radius: 4px;
  }

 

</style>