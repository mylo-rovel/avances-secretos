<script >

   // import {Line} from 'vue-chartjs'
    // import LineChart from '~/components/LineChart.vue'
    // import Vue from 'vue';
    //import * as Vue from 'vue'
    import Vue from 'vue';
    // import {getChart} from '~/utils/charts_utils.js';

    export default {
        data(){
            return{
                chartData: {
                    labels: ["Jan", "Feb", "Mar", "Apr"],
                    datasets: [
                        {
                            label:"Numbers",
                            borderColor: "#4bcc96",
                            borderWidth: 4,
                            pointBackgroundColor: "#4bcc96",
                            pointRadius:4,
                            pointHoverBorderColor: "#000",
                            fill: false,
                            data: [10, 15, 30, 20]
                        },
                    ],
                },
                chartOptions: {
                    maintainAspectRatio: false,
                    responsive: true,
                    tooltips: {
                        backgroundColor: "#00055e",
                        titleFontColor: "#ffffff",
                        bodyFontColor: "#ffffff",
                        position: "nearest",
                        mode:"nearest",
                        intersect: 0,
                        bodySpacing: 4,
                        xPadding:20,
                    },
                },
            }
        },
        async mounted() {
            const a = await import("~/utils/charts_utils.js");
            console.log(a);
        }
    }
</script>
<template>
    <div>
        <h1 class="title">Vue Chart></h1>
        <div class="chart-div">
            <!-- <LineChart :chartData="chartData" :options="chartOptions"/> -->
        </div>
    </div>
</template>
<style>
    .title{
        color: blue;
    }
    .line-chart{
        width:50vw;
        height:50vh;
    }

</style>
