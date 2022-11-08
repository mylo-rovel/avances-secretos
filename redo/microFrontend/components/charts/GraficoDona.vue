<!-- eslint-disable dot-notation -->
<template>
    <DoughnutChartGenerator :chart-data="dynChartData" class="myChart"/>
</template>
  
<script>
  // DataPage.vue
  import { Doughnut as DoughnutChartGenerator } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale } from 'chart.js'
  import { getDefaultCharDataModel } from '../../utils/charts_utils'
  ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale)
  
  export default {
    name: 'BarChart',
    components: { DoughnutChartGenerator },
    props: {
      chartDataProps: { type: Object, default: () => {return {"x":[],"y":[]}} },
    },
    data() {
      return {
      }
    },
    computed: {
      dynChartData() {
        const chartData = getDefaultCharDataModel();
        chartData.labels = this.chartDataProps.x;
        chartData.datasets[0].data = this.chartDataProps.y;
        return chartData;
      }
    },   
  }
</script>