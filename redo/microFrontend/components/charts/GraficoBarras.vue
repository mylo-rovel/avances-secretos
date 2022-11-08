<!-- eslint-disable dot-notation -->
<template>
  <BarChartGenerator :chart-data="dynChartData" class="myChart"/>
</template>

<script>
  // DataPage.vue
  import { Bar as BarChartGenerator} from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
  import { getDefaultCharDataModel } from '../../utils/charts_utils'
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

  export default {
    name: 'BarChart',
    components: { BarChartGenerator },
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