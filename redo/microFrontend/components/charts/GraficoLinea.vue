<!-- eslint-disable dot-notation -->
<template>
  <LineChartGenerator :chart-data="dynChartData" class="myChart"/>
</template>

<script>
  // DataPage.vue
  import { Line as LineChartGenerator } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement } from 'chart.js'
  import { getDefaultCharDataModel } from '../../utils/charts_utils';
  ChartJS.register( Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement )

  export default {
    name: 'LineChart',
    components: { LineChartGenerator },
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