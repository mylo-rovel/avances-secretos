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
  ChartJS.register( Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement )
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
        //chartData.labels = this.chartDataProps.labelsL;
        //chartData.datasets[0].data = this.chartDataProps.dataL;
        return chartData;
      },
      dynChartOptions() {
        const chartOptions = getDefaultChartOptions();
        //chartOptions.options = this.chartDataProps.optioncsL;
        return chartOptions;
      }
    },
}
</script>