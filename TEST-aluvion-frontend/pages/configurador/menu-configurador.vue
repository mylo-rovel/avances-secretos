<script>
    import Vue from 'vue';
    import PageHeader from '~/components/PageHeader.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';

    export default Vue.extend({
        name: "MenuOperador",
        components: { PageHeader, SubmitButton, NavbarPag },
        data() {
            return {
                tituloPag: "Menu configurador", 
                elementsObj : {
                    Inicio: false,
                    Perfil:  false,
                    Equipo:  false,
                    Contacto: false,
                }
            }
        },
        mounted() {
            checkIfUserShouldBeHere(["CONFIGURADOR"]);
            document.getElementById("flechaEquipo").addEventListener("click", () => {
                    document.getElementById("flechaEquipo").style.transform = "rotate(90deg)";
            });
        },
        methods: {
            abrirMenu(eventObj){
                const idRouteTitle = eventObj.currentTarget.id.toString().split("_key")[0];
                this.elementsObj[idRouteTitle] = !this.elementsObj[idRouteTitle];
            },
            girarFlecha(){

            }
        }
    })
</script>

<template>
    <section class="left-menu-panel">

        <article class="menu-routes-row-container">
            <p :id="`Inicio_key`" @click="(e) => {}">
                <span class="menu-routes-element underlined-element lista-items">
                    <img src="~/assets/home.svg" class="img-icono">
                    <NuxtLink to="/menu-operador">Inicio</NuxtLink>
                </span>
            </p>
            <p :id="`Perfil_key`" @click="(e) => {}">
                    <span class="menu-routes-element">    
                        <img src="~/assets/profile.svg" class="img-icono"><NuxtLink to="/operador/">Perfil</NuxtLink>
                    </span>
            </p>
            <p :id="`Equipo_key`" @click="(e) => abrirMenu(e)" >
                <img src="~/assets/stats.svg" class="img-icono">
                <span class="menu-routes-element">Equipo</span>
                <img id= "flechaEquipo" src="~/assets/arrow.svg" class=" flecha flecha-inv" @click = "girarFlecha">

                <ul v-if="elementsObj['Equipo']" class="submenu-routes-menu">
                    <p ><NuxtLink to="/configurador/registrar-equipo">Registrar equipo</NuxtLink></p>
                    <p ><NuxtLink to="/configurador/iniciar-simulacion">Editar equipo</NuxtLink></p>
                    <p ><NuxtLink to="/configurador/ver-simulacion">Visualizar equipo</NuxtLink></p>
                </ul>
            </p>
            <p :id="`Contacto_key`" @click="(e) => {}" class="row">
                <span class="menu-routes-element">
                    <img src="~/assets/message.svg" class="img-icono">
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
        min-width: 25rem;
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
    .flecha .flecha-inv{
    transform: rotate(90deg);
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