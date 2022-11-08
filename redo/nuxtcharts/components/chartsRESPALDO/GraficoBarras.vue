<!-- eslint-disable dot-notation -->
<template>
  <BarChartGenerator :chart-data="chartData" class="myChart"/>
</template>

<script>
  // DataPage.vue
  import { Bar as BarChartGenerator} from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

  export default {
    name: 'BarChart',
    components: { BarChartGenerator },
    props: {
      chartDataProps: { x:[], y:[] },
    },
    data() {
      return {
        chartData: {
          labels: [ 'Dato1', 'Dato2', 'Dato3'],
          datasets: [
            {
              label: 'Datos Barra',
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