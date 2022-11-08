<!-- eslint-disable dot-notation -->
<template>
    <DoughnutChartGenerator :chart-data="chartData" class="myChart"/>
</template>
  
<script>
  // DataPage.vue
  import { Doughnut as DoughnutChartGenerator } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale, Plugin } from 'chart.js'
  ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)
  
  export default {
    name: 'BarChart',
    components: { DoughnutChartGenerator },
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