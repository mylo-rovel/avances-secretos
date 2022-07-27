<script lang="js">
    import Vue from 'vue';
    import {mapState, mapMutations} from "vuex";

    export default Vue.extend({
        name: "GraficoEjecucion",
        // props:["datosGraficar"],
        // components: { PageHeader, CancelButtom, SubmitButton },
        data() {
            return {
            listaValoresGrafico: [],
            };
        },
        computed: mapState(["urlApi"]),
        
        async mounted(){
            // Try to set up WebSocket connection with the handshake at "http://localhost:8081/stomp"
            const sock = new SockJS('http://localhost:8081/stomp');

            // Create a new StompClient object with the WebSocket endpoint
            const webSockClient = Stomp.over(sock);
            this.webSockClient = webSockClient;

            // Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
            webSockClient.connect({}, frame => {
                // Subscribe to "/sockdata/datosgrafico". 
                // Whenever a message arrives do the processing.
                webSockClient.subscribe("/sockdata/datosgrafico", payload => {                
                    const receivedObj = JSON.parse(payload.body);
                    this.listaValoresGrafico.concat(receivedObj.valoresGrafico);
                    console.log(receivedObj);
                });

            });

        },
        methods:{
            sendSockReq() {
                this.webSockClient.send('/sockapi/ejecucion', {}, 
                    JSON.stringify({indiceInicial: 0, indiceFinal: this.listaValoresGrafico.length-1}));
                console.log("req enviada");
            }
        }
    })
</script>

<template>
    <section>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.js" integrity="sha512-lyIq9fRcCeSCXhp41XC/250UBmypAHV8KW+AhLcSEIksWHBfhzub6XXwDe67wTpOG8zrO2NAU/TYmEaCW+aQSg==" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js" integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g==" crossorigin="anonymous"></script>

        <button @click="sendSockReq" >apretáme</button>
    </section>
</template>

<style>

</style>