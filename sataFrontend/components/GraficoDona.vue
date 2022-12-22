
<!-- eslint-disable dot-notation -->
<template>
  <Doughnut :chart-data="dynChartData" :chart-options="dynChartOptions"/>
</template>

<script>
  // DataPage.vue
  import { Doughnut } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, CategoryScale, PointElement, Filler } from 'chart.js'
  import { getDefaultDonutDataModel } from '../utils/charts_utils'
  import { getDefaultDonutOptions } from '../utils/charts_utils'
  ChartJS.register( Title, Tooltip, Legend, ArcElement , CategoryScale, PointElement, Filler )
  export default {
    name: 'DoughnutChart',
    components: { Doughnut },
    props: {
      chartDataProps: { type: Object, default: () => {return {"dataL":[]}} },
    },
    data() {
      return {
      }
    },
    computed: {
      dynChartData() {
        const chartData = getDefaultDonutDataModel();
        //chartData.labels = this.chartDataProps.labelsL;
        chartData.datasets[0].data = this.chartDataProps.dataL;
        chartData.datasets[0].backgroundColor = ["#FF6384","#bbbbbb"];
        return chartData;
      },
      dynChartOptions() {
        const chartOptions = getDefaultDonutOptions();
        //chartOptions.options = this.chartDataProps.optioncsL;
        return chartOptions;
      }
    },
}
</script>