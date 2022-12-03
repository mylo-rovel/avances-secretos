<script lang="js">
    import Vue from 'vue';
    import {mapState} from "vuex";
    import PageHeader from '~/components/PageHeader.vue'
    import CustomButton from '../../components/CustomButton.vue';
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import { BPagination } from 'bootstrap-vue'

    export default Vue.extend({
      name: "ListaSimulaciones",
      components: { PageHeader, BPagination },
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
          simulacionesEjecutadasConFiltro: [],
          simulacionesEjecutadas: [],
          listaEquipos: [],
          listaFechas: [],
          filtroEquipo:"",
          filtroAnno:"",
          filtroMes:"",
          porPantalla:7,
          pagActual:1
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
          this.listaFechas = this.simulacionesEjecutadas['fechaEjecucion_'];
          serverPath = `${this.urlApi}/equipos/`;
          rawdata = await fetch(serverPath, this.getRequestConfig()).catch(err => err);
          if (rawdata instanceof Error) { return false; }
          const equiposAcotados = await rawdata.json();
          this.listaEquipos = equiposAcotados["equipoAcotado_"];
          //TODO:Crear consulta para que devuelva una lista unica de los años en que se ejecutaron las simulaciones
      },
      methods: {
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
        },
        getRequestConfig() {
          return { 
                method: 'get',
                headers: {'authorization': window.localStorage.getItem("token")}
            };
        }
      }
    })

</script>

<template>
  <section id="vistaSimulaciones">
    <section id="contenidoTabla">
        <div>
          <NavbarPag :tituloPag="tituloPag"/>
        </div>
        <!-- <div class="container-header">
          <PageHeader />
        </div> -->
        <div class="container">
          <div class="row">
            <div class="my-4" >
              <NuxtLink :to="'menu-operador'">
                <CustomButton :text="'Volver'" :custombcolor="'#7f8a99'" :customhcolor="'#575c63'" style="width: 6em" />
              </NuxtLink>     
            </div>
            <h4>Simulaciones Ejecutadas</h4>       
          </div>
          <div class="row my-3">
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
                  <td> <NuxtLink :to="'resumen?id=' + elementObj['id_']"> <CustomButton :text="'Ver'" :custombcolor="'#68da85'" :customhcolor="'#3c724a'"/> </NuxtLink> </td>
                </tr>
              </tbody>
            </table>
          </div>
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
    overflow-y: auto;
  }
  .tabla-simulacion{
    background-color: white;
    text-align: center;
    border-collapse: collapse;
    width: 100%;
  }
  .tabla-simulacion thead {
    background-color: #4e89f0;
    color: white;
  }
  .tabla-simulacion th{
    padding: 0.5rem;
  }
  .ul_top {
    display: flex;
    justify-content: space-between;
    list-style-type: none;
    padding: 0;
    width: 90%;
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