<script lang="js">
    import Vue from 'vue'
    import {mapState, mapMutations} from "vuex";
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'

    export default Vue.extend({
        name: "AgregarEvento",
        data() {
            return {
                duracionTotalListaEventos: 0,
                intensidadTotalListaEventos: 0,
                "cancelbutton": "atrás",
                "submitbutton": "guardar",
            }
        },
        computed: mapState(["listaEventos", "secuencias", "urlApi"]) ,
        methods: {
            ...mapMutations(["addEvento", "removeEvento", "setNuevoValorEvento", "setListaEventos"]),

            updateIntensidadTotalListaEventos() {
                this.intensidadTotalListaEventos = this.listaEventos.reduce((acc, evento) => acc + evento['intensidad'], 0);
            },
            updateDuracionTotalListaEventos() {
                this.duracionTotalListaEventos = this.listaEventos.reduce((acc, evento) => acc + evento['duracion'], 0);
            },
            actualizarEvento(eventObj, attriToModify, rowIndex){
                const dataObject = {newValue: eventObj.target.value, attriToModify, rowIndex};
                this.setNuevoValorEvento(dataObject);
                this.updateDuracionTotalListaEventos();
                this.updateIntensidadTotalListaEventos();
            },

            async guardarListaEventos(){
                console.clear()
                this.setListaEventos();
            }
        }
    })
</script>

<template>
    <section class="add-events-container">
        <article class="events-table-container">
            <table>
                <tr class="table-head">
                    <div class="plus-button" @click="() => {addEvento(0); updateDuracionTotalListaEventos(); updateIntensidadTotalListaEventos();}">+</div>
                    <th>Intensidad</th>
                    <th>Duración</th>
                </tr>
                <tr v-for="(evento, rowIndex) in listaEventos" :key="`eventKey_${rowIndex}`" class="table-row">
                    <div class="plus-button" @click="() => {addEvento(rowIndex+1); updateDuracionTotalListaEventos(); updateIntensidadTotalListaEventos();}">+</div>
                    <td class="event-element intensity-element">
                        <input type="number"  :placeholder="evento['intensidad']"  :value="evento['intensidad']"   @input="(e) => actualizarEvento(e, `intensidad`, rowIndex)" ></td>
                    <td class="event-element duration-element">
                        <input type="number"  :placeholder="evento['duracion']"    :value="evento['duracion']"     @input="(e) => actualizarEvento(e, `duracion`, rowIndex)"   ></td>
                    <div class="minus-button" @click="() => {removeEvento(rowIndex); updateDuracionTotalListaEventos(); updateIntensidadTotalListaEventos();}">-</div>
                </tr>
                
            </table>
            {{intensidadTotalListaEventos}}
        </article>
        <article class="bottom-ribbon">
            <div class="col-6 contenido-botones my-4">
                <NuxtLink to= "/operador/registrar-simulacion"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
            </div>
            <div class="col-6 contenido-botones my-4 total-duration-container">
                <p class="total-duration-title">Duración total</p>
                <p class="total-duration-time-row"><span class="total-duration-number">{{duracionTotalListaEventos}}</span>minutos</p>
            </div>
            <div class="col-6 contenido-botones my-4">
                <NuxtLink to= "/operador/registrar-simulacion"><SubmitButton :submitbutton ="submitbutton" @click="guardarListaEventos()" /></NuxtLink>
            </div>
        </article>
    </section>
</template>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

    * {
        margin:0;
        font-family: 'Poppins', sans-serif;
        font-weight: 400;
    }

    .add-events-container {
        width: fit-content;
        margin: 0 auto;
    }

    .events-table-container{
        border-radius: 20px;
        width: fit-content;
        height: 25rem;
        border: 1px solid black;
        margin:0 auto;
        margin-top: 5%;
        padding:2rem 6rem;
        overflow-y: scroll;
    }

    td, th {
        border-top: 1px solid black;
        border-bottom: 1px solid black;
        text-align: center;
        padding: 1.5rem 0rem;
    }

    th {
        border-top: 0;
        padding: 0 3rem;
        padding-bottom: 1.5rem;
        font-size: 1.3rem;
    }

    .intensity-element {
        border-right: 1px solid black;
    }

    .event-element input {
        min-width: 60px;
        max-width: 80px;
    }

    /* THIS SELECTORS ARE JUST FOR THE PLUS MINUS BUTTON */
    .table-row, .table-head{
        position: relative;
    }

    .plus-button{
        position: absolute;
        z-index: 999;
        top: 85%;
        left: -3.4rem;
        place-self: center;
        padding: 0 1rem;
        border: 1px solid black;
        text-align: center;
        border-radius: 20px;
        background-color: rgb(255, 255, 255);
        font-weight: 800;
    }

    .minus-button{
        position: absolute;
        z-index: 998;
        top: 35%;
        right: -3.2rem;
        place-self: center;
        padding: 0 1rem;
        border: 1px solid black;
        text-align: center;
        border-radius: 20px;
        background-color: rgb(255, 198, 198);
        font-weight: 800;
    }

    /*  BOTTOM          ||||| */
    /*   PART           VVVVV */

    .bottom-ribbon{
        width: fit-content;
        margin: 0 auto;
        display: flex;
        font-size: 1.1rem;
    }

    .total-duration-container {
        text-align: center;
        padding: 0rem 1.5rem;
    }

    .bottom-ribbon button{
        width: 10rem;
    }


    .total-duration-title{
        width: 10rem;
    }

    .total-duration-number{
        border: 1px solid black;
        padding: 0.1rem 0.6rem;
        margin-right: 0.4rem;
    }


    @media screen and (max-width: 614px) {
    /* start of large tablet styles */
        th, .bottom-ribbon {
            font-size: 3vw;
        }
        .events-table-container{
            padding:2rem 6rem;
            overflow-y: scroll;
        }
    }

    @media screen and (max-width: 767px) {
    /* start of medium tablet styles */

    }

    @media screen and (max-width: 479px) {
    /* start of phone styles */

    }

</style>