<script>
import { collapsed, toggleSidebar, sidebarWidth, listaTitulosMenu } from './estadoSideBar';
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { fas } from "@fortawesome/free-solid-svg-icons/";
import '@fortawesome/fontawesome-svg-core/styles.css';
library.autoAddCss = false;
library.add(fas);

export default {
    name: 'SideBar',
    components: { FontAwesomeIcon },
    setup(){
        return { collapsed, toggleSidebar, sidebarWidth, listaTitulosMenu }
    }
}

</script>

<template>
    <div class="sidebar" :style="{width: sidebarWidth}">
        <span class="collapse-icon" :class="{'rotate-180': collapsed}" @click="toggleSidebar">
            <font-awesome-icon icon="angle-double-left" />
        </span>
        <h4 class="title" v-if="collapsed">
            SATA
        </h4>
        <div class="routes">
            <div class="title" v-if="!collapsed">
                <h2 >Sistema</h2>
                <h2>Alerta</h2>
                <h2>Temprana</h2>
                <h2>Aluvional</h2>
            </div>
            <div v-for="(elementObj, rowIndex) in listaTitulosMenu" :key="`eventKey_${rowIndex}`">
                <NuxtLink :class= "collapsed ? 'route-text-collapsed' : 'route-text-expanded' " :to='elementObj["to"]'>
                    <font-awesome-icon style="align-self: center;" :icon='elementObj["icono"]' />
                    <div style="margin-left: 20px;" v-if="!collapsed">
                        {{elementObj["nombre"]}}
                    </div>
                </NuxtLink>
            </div>
        </div>
        <img class="common-anid-logo" src="~/assets/img/anid-logo.png" alt="conicyt-logo">
    </div>
   
</template>


<style>

    .routes{
        align-self: center;
    }

    .route-text-collapsed{
        color: rgb(240, 243, 255);
        display: flex;
        align-items: center;
        cursor:pointer;
        position: relative;
        user-select: none;
        transition: 0.2s ease;
        margin-bottom: 4.5rem;
        padding: 0.5rem 1rem;
        overflow: hidden;
        border-radius: 5px;
    }

    .route-text-expanded{
        color: rgb(240, 243, 255);
        display: flex;
        align-items: center;
        cursor:pointer;
        position: relative;
        user-select: none;
        transition: 0.1s ease;
        margin-top: 2rem;
        padding: 0.2rem 0.5rem;
        overflow:hidden;
        border-radius: 5px;
    }

    .route-text-collapsed:hover{
        color: white;
        background-color: rgb(66, 160, 204);
    }

    .route-text-expanded:hover{
        color: white;
        background-color: rgb(92, 198, 247);
    }

    .common-anid-logo { 
        max-width: fit-content;
    }

    .title{
        margin-top: 40px;
        overflow: hidden;
    }

    .sidebar{
        color: white;
        background-color: rgb(51, 63, 99);
        position: fixed;
        z-index: 1;
        top:0;
        left: 0;
        bottom: 0;
        padding: 0.5rem;

        transition:  0.3s ease;
        
        display: flex;
        flex-direction: column;
        user-select: none;
        justify-content: space-between;
        overflow-y: auto;
        overflow-x: hidden;

    }

    .collapse-icon{
        position: absolute;
        top: 0;
        padding: 0.75em;
        margin-left: 0.8em;
        color:rgb(255, 255, 255);
        transition: 0.2s linear;
    }

    .rotate-180{
        transform: rotate(180deg);
        transition: 0.2s linear;
    }





</style>