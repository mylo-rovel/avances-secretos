<script>
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
  },
  data() {
    return {
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
  mounted() {
    console.log("aaabbb");
    console.log(this.objetoDatosEjecucion);
    this.chartData["datasets"][0]["label"] += this.equipoSeleccionado;
    this.chartData["datasets"][0]["data"] = this.objetoDatosEjecucion["caudales"];
    this.chartData["labels"] = this.objetoDatosEjecucion["horas"];
    console.log(this.objetoDatosEjecucion["horas"])
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
