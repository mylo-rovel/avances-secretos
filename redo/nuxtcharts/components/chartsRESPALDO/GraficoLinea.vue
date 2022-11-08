<!-- eslint-disable dot-notation -->
<template>
  <LineChartGenerator :chart-data="chartData" class="myChart"/>
</template>

<script>
  // DataPage.vue
  import { Line as LineChartGenerator } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement } from 'chart.js'
  ChartJS.register( Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement )

  export default {
    name: 'LineChart',
    components: { LineChartGenerator },
    props: {
      chartDataProps: { x:[], y:[] },
    },
    data() {
      return {
        chartData: {
          labels: [ 'Dato1', 'Dato2', 'Dato3'],
          datasets: [
            {
              label: 'Datos linea',
              backgroundColor: '#f87979',
              data: [40, 20, 12]
            }
          ],
        }
      }
    },
    mounted() {
      
    },
  }
</script>