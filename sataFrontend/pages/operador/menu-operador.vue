<script>
    import Vue from 'vue';
    import NavbarPag from '~/components/NavbarPag.vue'
    import Footer from '~/components/Footer.vue'
    import CarouselPag from '~/components/CarouselPag.vue'
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';
    import SideBar from '../../components/SideBar/SideBar.vue';

    export default Vue.extend({
    name: "MenuOperador",
    head(){
            return{
                title: "Menu Principal - Sistema de Alerta Temprana Aluvional",
                meta: [{
                    name: "menuPrincipal",
                    content: "Menu principal"
                }],
            }
        },
    data() {
        return {
            elementsObj: {
                Inicio: false,
                Perfil: false,
                Simulacion: false,
                Contacto: false,
            }
        };
    },
    mounted() {
        console.clear();
        checkIfUserShouldBeHere(["OPERADOR"]);
    },
    methods: {
        abrirMenu(eventObj) {
            const idRouteTitle = eventObj.currentTarget.id.toString().split("_key")[0];
            // this.elementsObj[idRouteTitle].esDesplegable = !this.elementsObj[idRouteTitle].esDesplegable;
            this.elementsObj[idRouteTitle] = !this.elementsObj[idRouteTitle];
            if (this.elementsObj[idRouteTitle] === true) {
                let flecha = document.getElementById("flechaSimulacion").style.transform = "rotate(90deg)";
            }
            else if (this.elementsObj[idRouteTitle] === false) {
                let flecha = document.getElementById("flechaSimulacion").style.transform = "rotate(0deg)";
            }
        },
    },
    components: { SideBar }
})
</script>

<template>
    <section class="container">
        <SideBar/>
        <section  class = "rigth-menu-panel col-6">
            <article>
                <p>
                    <h3 class="my-4">Bienvenidos</h3>
                    <h3 class= "my-4">Sistema de Alerta Temprana Aluvional</h3>
                </p>

                <img class="img" src="~/assets/img/maqueta.png" alt="conicyt-logo"/>
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
    
    .list__item {
        list-style: none;
    }
    .list__item .list__button {
        padding: 1rem 0 0 0;
        display: flex;
    }
    .list__item .list__button img {
        margin-right: 0.5rem;
    }    
    .submenu-routes-menu {
        text-align: center;
        margin-right: 4rem;
        margin-top: 0.2rem;
    }
    #flechaSimulacion {
        transition: all 0.1s;
    }
    .img { 
        max-width: fit-content;
        border-top: 1mm solid #2162ad;
        padding: 0.75em;
    }

</style>