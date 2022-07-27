<script>

    import {Line} from 'vue-chartjs'
    //import * as Vue from 'vue'
    
    export default {
        extends: Line,
        props: ["chartData", "options"],
        mounted(){
            this.renderChart(this.chartData, this.options);
        }
        
    };

</script>
<template>
</template>