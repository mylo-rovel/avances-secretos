<script>
    import Vue from 'vue';
    import NavbarPag from '~/components/NavbarPag.vue'
    import Footer from '~/components/Footer.vue'
    import CarouselPag from '~/components/CarouselPag.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';

    export default Vue.extend({
        name: "MenuOperador",
        data() {
            return {
                elementsObj : {
                    Inicio: false,
                    Perfil:  false,
                    Simulacion:  false,
                    Contacto: false,
                }
            }
        },
        mounted() {
            console.clear();
            checkIfUserShouldBeHere(["OPERADOR"]);
        },
        methods: {
            abrirMenu(eventObj){
                const idRouteTitle = eventObj.currentTarget.id.toString().split("_key")[0];
                // this.elementsObj[idRouteTitle].esDesplegable = !this.elementsObj[idRouteTitle].esDesplegable;
                this.elementsObj[idRouteTitle] = !this.elementsObj[idRouteTitle];
                if(this.elementsObj[idRouteTitle] === true){
                    let flecha = document.getElementById("flechaSimulacion").style.transform = "rotate(90deg)";
                }else if(this.elementsObj[idRouteTitle] === false){
                    let flecha = document.getElementById("flechaSimulacion").style.transform = "rotate(0deg)";
                }
            },
        }
    })
</script>

<template>
    <section class="row">
        <!--<div><CarouselPag /></div>-->
        <div>
            <NavbarPag/> 
        </div>
        <section class="left-menu-panel col-4">
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
                <li class="list__item mb-3" :id="`Inicio_key`" @click="(e) => {}">
                    <div class="list__button">
                        <img src="~/assets/home.svg" class="img-icono">
                        <NuxtLink to="#">Inicio</NuxtLink>
                    </div>
                </li>
                <!--<p :id="`Inicio_key`" @click="(e) => {}" class="container">
                    <span class="menu-routes-element row ">
                        <NuxtLink class="col-md-auto" to="/operador/ejemplo">
                            <img src="~/assets/home.svg" width="24" height="24"class=" img-icono col-md-auto">
                            <span class="menu-routes-element col-md-auto">Inicio</span>
                        </NuxtLink>
                    </span>
                </p>
                <p :id="`Perfil_key`" @click="(e) => {}" class="container row">
                        <span class="menu-routes-element ">    
                            <img src="~/assets/profile.svg" width="24" height="24" class="col-md-auto img-icono"><NuxtLink class="col-md-auto" to="/operador/">Perfil</NuxtLink>
                        </span>
                </p> -->
                <li class="list__item mb-3 " :id="`Perfil_key`" @click="(e) => {}">
                    <div class="list__button">    
                        <img src="~/assets/profile.svg" class="img-icono">
                        <NuxtLink to="#">Perfil</NuxtLink>
                    </div>
                </li>
                 <li class="list__item  mb-3" :id="`Simulacion_key`" @click="(e) => abrirMenu(e)" >
                    <div class="list__button ">
                        <img src="~/assets/stats.svg" class="img-icono">
                        <span class="menu-routes-element">Simulación</span>
                        <img id= "flechaSimulacion" src="~/assets/arrow.svg" class="list__arrow">
                    </div>
                    <ul v-if="elementsObj['Simulacion']" class=" submenu-routes-menu">
                        <p class="list__inside"><NuxtLink to="/operador/registrar-simulacion">Registrar simulación</NuxtLink></p>
                        <p ><NuxtLink to="/operador/iniciar-simulacion">Iniciar simulación</NuxtLink></p>
                        <p><NuxtLink to="/operador/ver-simulacion">Ver simulación</NuxtLink></p>
                        <p><NuxtLink to="/operador/lista-simulaciones">Historial de simulaciones</NuxtLink></p>
                    </ul>
                </li>
                <!--<p :id="`Simulacion_key`" @click="(e) => abrirMenu(e)" class="container row" >
                    <img src="~/assets/stats.svg" width="24" height="24"class="img-icono col-md-auto">
                    <span class="menu-routes-element col-md-auto">Simulación</span>
                    <img id= "flechaSimulacion" src="~/assets/arrow.svg" width="24" height="24"class=" col-md-auto flecha flecha-inv">

                    <ul v-if="elementsObj['Simulacion']" class="submenu-routes-menu">
                        <p ><NuxtLink to="/operador/registrar-simulacion">Registrar simulación</NuxtLink></p>
                        <p ><NuxtLink to="/operador/iniciar-simulacion">Iniciar simulación</NuxtLink></p>
                        <p ><NuxtLink to="/operador/ver-simulacion">Ver simulación</NuxtLink></p>
                        <p ><NuxtLink to="/operador/lista-simulaciones">Historial de simulaciones</NuxtLink></p>
                    </ul>
                </p>
                <p :id="`Contacto_key`" @click="(e) => {}" class="container row">
                    <span class="menu-routes-element col-md-auto">
                        <img src="~/assets/message.svg" width="24" height="24" class="col-md-auto img-icono ">
                        <NuxtLink to="/operador/">Contacto</NuxtLink>
                    </span>
                </p>-->
                <li class="list__item mb-3" :id="`Contacto_key`" @click="(e) => {}">
                    <div class="list__button">
                        <img src="~/assets/message.svg" class="img-icono">
                        <NuxtLink to="#">Contacto</NuxtLink>
                    </div>
                </li>

            </article>
        </section>
        <section  class = "rigth-menu-panel col-6">
            <article>
                <p>
                    <h3 class="my-4">Bienvenidos</h3>
                    <h3 class= "my-4">Sistema de Alerta Temprana Aluvional</h3>
                </p>
                <p>

                </p>
            </article>
        </section>
    </section>
</template>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');

    * {
    margin:0;
    font-family: 'Poppins', sans-serif;
    text-decoration: none;
    box-sizing: border-box;
    padding: 0;
    }

    p, a {
        color:rgb(65, 65, 65);
        text-decoration: none;
    }
    
    span {
        cursor: pointer;
    }

    .left-menu-panel {
        background-color: #FFFFFF;
        height: 100vh;
        width: min-content;
        min-width: 27rem;
        border: 1px solid black;
        border-radius: 3px;
        border-left: 0;
        border-top:0;
        border-bottom:0;
    }

    .menu-routes-row-container {
        height: fit-content;
        padding-top: 35%;
        width: 100%;
        padding-left: 5rem;
    }

    .menu-routes-row-container p{
        padding-bottom: 1rem;
    }

    .underlined-element {
        width:fit-content;
        text-decoration: underline;
    }
    .submenu-routes-menu {
        transition: linear 0.1s;
        color: black;
        font-size: 0.85rem;
    }

    .menu-routes-element:hover,
    .submenu-routes-menu p {
        transition: linear 0.1s;
        color: black;
        font-size: 0.85rem;
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
    .img-icono{
        box-sizing: border-box;
        margin:0;
        padding:0;
        display: block;
        float: none;
        line-height: normal;
        position:static;
        z-index: auto;
    }

    .flecha-inv{
        margin-left: auto;
        transition: transform .3s;
    }
    .lista-items{
        list-style: none;
        width: 100%;
        text-align: center;
        overflow: hidden;
    }

</style>