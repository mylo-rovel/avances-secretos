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
                // Inicio: {esDesplegable:false},
                // Perfil: {esDesplegable:false},
                // Simulacion: {esDesplegable:false},
                // Contacto: {esDesplegable:false},
                Inicio: false,
                Perfil:  false,
                Simulacion:  false,
                Contacto: false,
            }
        }
    },
    methods: {
        abrirMenu(eventObj){
            const idRouteTitle = eventObj.currentTarget.id.toString().split("_key")[0];
            console.log(idRouteTitle)
            // this.elementsObj[idRouteTitle].esDesplegable = !this.elementsObj[idRouteTitle].esDesplegable;
            this.elementsObj[idRouteTitle] = !this.elementsObj[idRouteTitle];
        }
    }
})
</script>

<template>
<section class="left-menu-panel">
    <article class="menu-routes-row-container">
        <!-- <p v-for="element in Object.entries(elementsObj)" :key="`${element[0]}_key`"> 
            <span :id="`${element[0]}_key`" @click="(e) => abrirMenu(e)"> 
                <span class="menu-routes-element">
                    {{element[0]}}
                </span>
                <ul v-if="element[1].esDesplegable && element[0]==='Simulación'" class="submenu-routes-menu">
                    <p ><NuxtLink to="/operador/">Registrar simulacion</NuxtLink></p>
                    <p ><NuxtLink to="/operador/">Iniciar simulacion</NuxtLink></p>
                    <p ><NuxtLink to="/operador/">Ver simulacion</NuxtLink></p>
                    <p ><NuxtLink to="/operador/">Historial de simulaciones</NuxtLink></p>
                </ul>
            </span>
        </p> -->
        <p  :id="`Inicio_key`" @click="(e) => {}">
            <span class="menu-routes-element underlined-element">
                <NuxtLink to="/operador/">Inicio</NuxtLink>
            </span>
        </p>

        <p :id="`Perfil_key`" @click="(e) => {}">
            <span class="menu-routes-element">
                <NuxtLink to="/operador/">Perfil</NuxtLink>
            </span>
        </p>

        <p :id="`Simulacion_key`" @click="(e) => abrirMenu(e)">
            <span class="menu-routes-element">
                Simulación
            </span>
            <ul v-if="elementsObj['Simulacion']" class="submenu-routes-menu">
                <p ><NuxtLink to="/operador/">Registrar simulacion</NuxtLink></p>
                <p ><NuxtLink to="/operador/">Iniciar simulacion</NuxtLink></p>
                <p ><NuxtLink to="/operador/">Ver simulacion</NuxtLink></p>
                <p ><NuxtLink to="/operador/">Historial de simulaciones</NuxtLink></p>
            </ul>
        </p>

        <p :id="`Contacto_key`" @click="(e) => {}">
            <span class="menu-routes-element">
                <NuxtLink to="/operador/">Contacto</NuxtLink>
            </span>
        </p>

    </article>
</section>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

* {
  margin:0;
  font-family: 'Poppins', sans-serif;
  text-decoration: none;
}

p, a {
    color:rgb(65, 65, 65);
    text-decoration: none;
}

.left-menu-panel {
    background-color: #FFFFFF;
    height: 100vh;
    width: min-content;
    min-width: 19rem;
    border: 1px solid black;
    border-radius: 3px;
    border-left: 0;
    border-top:0;
    border-bottom:0;
}

.menu-routes-row-container {
    height: fit-content;
    padding-top: 69%;
    width: 100%;
    padding-left: 5.5rem;
}

.menu-routes-row-container p{
    padding-bottom: 1rem;
}

.underlined-element {
    width:fit-content;
    text-decoration: underline;
}

.menu-routes-element:hover,
.submenu-routes-menu p {
    transition: linear 0.1s;
    color: blue;
}


.submenu-routes-menu p {
    --li-padding-bottom: 0.5rem;
    margin:0;
    padding: 0;
    width: 80%;
    list-style: none;
    padding-bottom: var(--li-padding-bottom);
}

.submenu-routes-menu p:last-child {
    padding:0;
}

.submenu-routes-menu p a {
    text-decoration: none;
}

</style>