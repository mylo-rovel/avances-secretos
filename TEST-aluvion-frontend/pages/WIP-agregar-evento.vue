<script>
import Vue from 'vue'
import {mapState, mapMutations} from "vuex";
// import {addGlobalEventListener} from "~/utils/utility_functions.js";
// addGlobalEventListener("click", ".plus-button", e => console.log("\n\n\naaaa\n\n\n",e))

export default Vue.extend({
    name: "AddEventFrame",
    data() {
        return {}
    },
    computed: mapState(["listaEventos"]) ,
    methods: {
        ...mapMutations(["addEvento"])
    }
})
</script>

<template>
<section>
    <article class="events-table-container">
        <table>
            <tr class="table-head">
                <div class="plus-button" @click="() => addEvento(0)">+</div>
                <th>Intensidad</th>
                <th>Duracion</th>
                <div></div>
            </tr>

            <tr v-for="(evento, index) in listaEventos" :key="`eventKey_${index}`" class="table-row">
                <div class="plus-button" @click="() => addEvento(index+1)">+</div>
                <td class="intensity-element">{{evento.getIntensidad()}}</td>
                <td class="duration-element">{{evento.getDuracion()}}</td>
                <div class="minus-button">-</div>
            </tr>
            
        </table>
    </article>
    <article class="bottom-ribbon">
        <div class="save-button">
            <button>Guardar</button>
        </div>
        <div class="total-time-container">
            <p>Duración total</p>
            <p><span>93</span>minutos</p>
        </div>
        <div class="back-button">
            <button>Atrás</button>
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
.events-table-container{
    border-radius: 20px;
    width: fit-content;
    max-height: 28rem;
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
    padding: 1.5rem 1rem;
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

</style>