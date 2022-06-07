<script>

import Vue from 'vue'

import SubmitButton from '~/components/SubmitButton.vue'
import CancelButton from '~/components/CancelButtom.vue'
import NavbarPag from '~/components/NavbarPag.vue'

export default Vue.extend({
    name: "ejemplo",
    components: { SubmitButton, CancelButton},
  
    data() {
        return {
          "submitbutton": "enviar",
          "cancelbutton": "cancelar"
        };
    },
    methods:{
        getMenu(){
            return{
                listElements: document.querySelectorAll('.list__button--click')
            }; 
        },
        displayOpcions(){
            listElements.forEach(listElement => {
                listElement.addEventListener('click', ()=>{
                    listElement.classList.toggle('arrow');
                    let height = 0;
                    let menu = listElement.nextElementSibling;
                    if(menu.clientHeight == "0"){
                        height=menu.scrollHeight;
                    }
                    menu.style.height = `${height}px`;
                })
            });
        }
    }

})
</script>
         

<template>
    <nav class="nav">
        <ul class="list">
            <li class="list__item">
                <div class="list__button">
                    <img src="assets/dashboard.svg" class="list__img">
                    <NuxtLink to="/operador/main-operador"><a class="nav__link">Inicio</a></NuxtLink>
                </div>
            </li>
            <li class="list__item">
                <div class="list__button">
                    <img src="assets/stats.svg" class="list__img">
                    <a href="#" class="nav__link">Perfil</a>
                </div>
            </li>
            <li class="list__item list__item--click">
                <div class="list__button list__button--click">
                    <img src="assets/docs.svg" class="list__img">
                    <a href="/operador/agregar-secuencia" class="nav__link">Secuencia</a>
                    <img src="assets/arrow.svg" class="list__arrow">
                </div>
                <ul class="list__show">
                    <li class="list__inside">
                        <a href="/operador/agregar-secuencia" class="nav__link nav__link--inside">Crear Secuencia</a>
                    </li>
                    <li class="list__inside">
                        <a href="#" class="nav__link nav__link--inside">Listado de Secuencias</a>
                    </li>
                </ul>
            </li>
            <li class="list__item list__item--click">
                <div class="list__button list__button--click">
                    <img src="assets/bell.svg" class="list__img">
                    <a href="#" class="nav__link">Simulación</a>
                    <img src="assets/arrow.svg" class="list__arrow">
                </div>
                <ul class="list__show">
                    <li class="list__inside">
                        <a href="#" class="lala nav__link nav__link--inside">Iniciar Simulación</a>
                    </li>
                    <li class="list__inside">
                        <a href="#" class="nav__link nav__link--inside">Ver Simulación</a>
                    </li>
                    <li class="list__inside">
                        <a href="#" class="nav__link nav__link--inside">Historial de Simulaciones</a>
                    </li>
                </ul>
            </li>
            <li class="list__item">
                <div class="list__button">
                    <img src="assets/message.svg" class="list__img">
                    <a href="#" class="nav__link">Contacto</a>
                </div>
            </li>
        </ul>
    </nav>
</template>


<style>

    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap');

    *{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body{
        background: #EAEBEF;
        font-family: 'Poppins', sans-serif;
    }

    .nav{
        width: 300px;
    }

    .nav__link{
        color: #303440;
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
        margin: 0 auto;
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

        .list__show .list__inside a{
        width: 2rem;
        height:2rem;
        background-color:red;
        display:grid;
    }
</style>