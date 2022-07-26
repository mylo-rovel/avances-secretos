<script lang="js">
    import Vue from 'vue'
    // import {mapState, mapMutations} from "vuex";
    // import {addGlobalEventListener} from "~/utils/utility_functions.js";
    // addGlobalEventListener("click", ".plus-button", e => console.log("\n\n\naaaa\n\n\n",e))
    //#EAEBEF

    export default Vue.extend({
        name: "ejemplo",
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
    <nav class="nav ">
        <ul class=" list ">
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
            <li class="list__item" :id="`Inicio_key`" @click="(e) => {}">
                <div class="list__button">
                    <img src="~/assets/home.svg" class="img-icono">
                    <NuxtLink to="/operador/">Inicio</NuxtLink>
                </div>
            </li>
            <li class="list__item" :id="`Perfil_key`" @click="(e) => {}">
                    <div class="list__button">    
                        <img src="~/assets/profile.svg" class="img-icono">
                        <NuxtLink to="/operador/">Perfil</NuxtLink>
                    </div>
            </li>
            <li class="list__item " :id="`Simulacion_key`" @click="(e) => abrirMenu(e)" >
                <div class="list__button list__button--click">
                    <img src="~/assets/stats.svg" class="img-icono">
                    <span class="menu-routes-element">Simulación</span>
                    <img src="~/assets/arrow.svg" class="list__arrow">
                </div>
                <ul v-if="elementsObj['Simulacion']" class="list__show">
                    <li class="list__inside">
                        <NuxtLink to="/operador/registrar-simulacion">Registrar simulacion</NuxtLink>
                    </li>
                    <li class="list__inside">
                        <NuxtLink to="/operador/iniciar-simulacion">Iniciar simulacion</NuxtLink>
                    </li>
                    <li class="list__inside">
                        <NuxtLink to="/operador/ver-simulacion">Ver simulacion</NuxtLink>
                    </li>
                    <li class="list__inside">
                        <NuxtLink to="/operador/lista-secuencia">Historial de simulaciones</NuxtLink>
                    </li>
                </ul>
            </li>
            <li class="list__item" :id="`Contacto_key`" @click="(e) => {}">
                <div class="list__button">
                    <img src="~/assets/message.svg" class="img-icono">
                    <NuxtLink to="/operador/">Contacto</NuxtLink>
                </div>
            </li>
        </ul>
    </nav>
</template>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap');
    *{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body{
        font-family: 'Poppins', sans-serif;
    }

    .nav{
        width: 300px;
    }

    .nav__link{
        color: #212529;
        display: block;
        padding: 15px 0;
        text-decoration: none;
    }

    .nav__link--inside{
        border-radius: 6px ;
        padding-left: 20px;
        text-align: left;
    }

    .nav__link--inside:hover{
        background: #F6F8FA;
    }

    .list{
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        flex-direction: column;
        border-radius: 0 16px 16px 0;
        background: #fff;
    }
    .list__item{
        list-style: none;
        width: 100%;
        text-align: center;
        overflow: hidden;
    }

    .list__item--click{
        cursor: pointer;
    }

    .list__button{
        display: flex;
        align-items: center;
        gap: 1em;
        width: 70%;
        margin:  auto;
    }

    .arrow .list__arrow{
        transform: rotate(90deg);
    }

    .list__arrow{
        margin-left: auto;
        transition: transform .3s;
    }

    .list__show{
        width: 80%;
        margin-left: auto;
        border-left: 2px solid #303440;
        list-style: none;
        transition: height .4s;
        height: 0;
    }
    .list__inside{
        box-sizing: border-box;
        display: list__item;
        float: none;
        line-height: normal;
        position: static;
        z-index: auto;
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