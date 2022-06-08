<script>
import Vue from 'vue'
// import {mapState, mapMutations} from "vuex";
// import {addGlobalEventListener} from "~/utils/utility_functions.js";
// addGlobalEventListener("click", ".plus-button", e => console.log("\n\n\naaaa\n\n\n",e))

export default Vue.extend({
    name: "AddEventFrame",
    data() {
        return {
            elementsObj : {
                "Inicio": {open:false},
                "Perfil": false,
                "Secuencia": false,
                "Simulacion": false,
                "Contacto": false
            }
        }
    },
    methods: {
        abrirModal(elemento) => () {
            console.log(elemento);
        }
    }
})
</script>

<template>
<section class="left-menu-panel">
    <article class="menu-routes-container">
        <p :id="element" v-for="element in Object.entries(elementsObj)" :click=""> 
            <span class="menu-routes-element"> {{element[0]}} </span>
        </p>
    </article>
</section>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

* {
  margin:0;
  font-family: 'Poppins', sans-serif;
}

.left-menu-panel {
    background-color: #FFFFFF;
    height: 100vh;
    width: fit-content;
    padding: 7rem;
    border: 1px solid black;
    border-radius: 3px;
    border-left: 0;
    border-top:0;
    border-bottom:0;
    display: grid;
    place-items: center;
}

.menu-routes-container {
    height:fit-content;
    width: fit-content;
}

.menu-routes-container p{
    padding-bottom: 1.25rem;
}

.menu-routes-container p:first-child span {
    width:fit-content;
    text-decoration: underline;
    color: rgb(100, 100, 100);
}

.menu-routes-element:hover {
    color: blue;
}

</style>