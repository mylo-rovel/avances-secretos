
<!-- eslint-disable dot-notation -->
<template>
  <LineChartGenerator :chart-data="dynChartData" :chart-options="dynChartOptions" class="myChart"/>
</template>

<script>
  // DataPage.vue
  import { Line as LineChartGenerator } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement, Filler } from 'chart.js'
  import { getDefaultCharDataModel } from '../utils/charts_utils'
  import { getDefaultChartOptions } from '../utils/charts_utils'
  ChartJS.register( Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement, Filler )
  export default {
    name: 'LineChart',
    components: { LineChartGenerator },
    props: {
      chartDataProps: { type: Object, default: () => {return {"labelsL":[],"dataL":[],"optionsL":[]}} },
    },
    data() {
      return {
      }
    },
    computed: {
      dynChartData() {
        const chartData = getDefaultCharDataModel();
        chartData.labels = this.chartDataProps.labelsL;
        chartData.datasets = this.chartDataProps.dataL;
        return chartData;
      },
      dynChartOptions() {
        const chartOptions = getDefaultChartOptions();
        chartOptions.scales = this.chartDataProps.optionsL;
        return chartOptions;
      }
    },
}
</script>