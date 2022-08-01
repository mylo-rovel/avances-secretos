<script>
import { getListasUsarDatosGrafico } from '~/utils/utility_functions';
import {mapState} from "vuex";
import { Line as LineChartGenerator } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement } from 'chart.js'
ChartJS.register( Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement )


export default {
  name: 'LineChart',
  components: {
    LineChartGenerator
  },
  props: {
    chartId: { type: String, default: 'line-chart' },
    datasetIdKey: { type: String, default: 'label' },
    width: { type: Number, default: 400 },
    height: { type: Number, default: 400 },
    cssClasses: { type: String, default: '' },
    styles: { type: Object, default: () => {} },
    plugins: { type: Array, default: () => [] },

    objetoDatosEjecucion: { type: Object, default: () => {return {"caudales":[],"horas":[]}} },
    equipoSeleccionado: { type: String, default: '' },
    cantidadValoresGrafico: { type: Number, default: 0 },
  },
  data() {
    return {
      indiceInicialEnviar: 0,
      indiceFinalEnviar: 1000,
      chartData: {
        labels: [ 'January', 'February', 'March', 'April', 'May', 'June', 'July' ],
        datasets: [
          {
            label: 'Datos ejecución ',
            backgroundColor: '#f87979',
            data: [40, 39, 10, 40, 39, 80, 40]
          }
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  computed: mapState(["urlApi", "cantValoresPorReq"]),
  mounted() {
    // AJUSTAR TITULO GRAFICO AL NOMBRE DEL EQUIPO
    this.chartData["datasets"][0]["label"] += this.equipoSeleccionado;
    
    console.log(1, this.objetoDatosEjecucion["horas"]);
    // COLOCAR LOS DATOS EN LOS LUGARES DE GRAFICACION
    this.chartData["labels"] = this.objetoDatosEjecucion["horas"]; // valores eje X
    this.chartData["datasets"][0]["data"] = this.objetoDatosEjecucion["caudales"]; // valores eje Y

    // queremos pedir datos por encima de lo que se pidio anteriormente
    this.indiceInicialEnviar = this.objetoDatosEjecucion["caudales"].length;
    // si la proxima peticion pide mas elementos que la cantidad existente
    // significa que "ESTAMOS CASI AL DIA CON EL GRAFICO". De otro modo, aun queda harto por pedir
    this.indiceFinalEnviar = (this.indiceInicialEnviar + this.cantValoresPorReq > this.cantidadValoresGrafico) ?
      this.cantidadValoresGrafico 
      : 
      this.objetoDatosEjecucion["caudales"].length + this.cantValoresPorReq;
    
    console.log(`Indice final a enviar ${this.indiceFinalEnviar}`);
    window.setInterval(this.getMasDatosEjecucion, 2500) // AGREGAR UN RELOJ DE FONDO QUE BUSQUE DATOS CADA 2500 milisegundos
  },
  methods: {
    logLengthListasGraficar() {
      console.log("X: ", this.chartData["labels"].length);
      console.log("Y: ", this.chartData["datasets"][0]["data"].length);
    },

    updateListasGraficar(newXDataArr, newYDataArr) {
      // console.log("newXDataArr",this.chartData["labels"])
      this.chartData["labels"] = [...this.chartData["labels"], newXDataArr]
      this.chartData["datasets"][0]["data"] = [...this.chartData["datasets"][0]["data"], newYDataArr];
      this.logLengthListasGraficar();
    },

    async getMasDatosEjecucion(){
        const POST_body = {
            "nombreEquipo": this.equipoSeleccionado,
            "indiceInicial": this.indiceInicialEnviar,
            "indiceFinal": this.indiceFinalEnviar
        }
        const tokenUsuario = window.localStorage.getItem("token");
        const POST_config = {
            'method': 'POST',
            'body': JSON.stringify(POST_body),
            'headers':{
                'Content-Type':'application/json',
                'authorization': tokenUsuario,
                }
        };
        const rawReponse = await fetch(`${this.urlApi}/ejecuciones/valoresgrafico`, POST_config).catch(err => err);
        if (rawReponse instanceof Error) {
            alert("❌ Error al pedir datos del grafico ❌");
            console.log(rawReponse)
            return;
        }
        const objetoRespuesta = await rawReponse.json();
        if (objetoRespuesta === "Indices incorrectos") {
            alert("❌ INDICES INCOHERENTES ❌");
            console.log(rawReponse)
            return;
        }
        const valoresCaudalArr = objetoRespuesta["caudalTiempo_"];
        const datosGraficoProcesados = getListasUsarDatosGrafico(valoresCaudalArr);
        // this.updateListasGraficar(datosGraficoProcesados["horas"], datosGraficoProcesados["caudales"])

        this.chartData["labels"] = this.chartData["labels"].concat(datosGraficoProcesados["horas"]);
        this.chartData["datasets"][0]["data"] = this.chartData["datasets"][0]["data"].concat(datosGraficoProcesados["caudales"]);
        
        console.log(`Cantidad de datos obtenidos: ${this.chartData["labels"].length}`)
        // queremos pedir datos por encima de lo que se pidio anteriormente
        this.indiceInicialEnviar = this.indiceFinalEnviar;

        // si la proxima peticion pide mas elementos que la cantidad existente
        // significa que "ESTAMOS CASI AL DIA CON EL GRAFICO"
        this.indiceFinalEnviar = (this.indiceInicialEnviar + this.cantValoresPorReq > objetoRespuesta["listaSize_"]) ?
          objetoRespuesta["listaSize_"]
          : 
          this.indiceInicialEnviar + this.cantValoresPorReq;  
        console.log(`POST: inicial: ${this.indiceInicialEnviar} - final: ${this.indiceFinalEnviar}`);
        console.log(`\nCANTIDAD VALORES GRAFICO ${objetoRespuesta["listaSize_"]}`);      
    },
  }
}
</script>

<template>
  <LineChartGenerator
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>
