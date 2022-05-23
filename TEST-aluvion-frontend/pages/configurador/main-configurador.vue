<script>
import Vue from 'vue'
import Styles from '~/components/Styles.vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
    name: "main-configurador",
    components: { PageHeader, Styles },
    data(){
        return {
            "headerTitle": "Control de sistema automatico"
        };
    },
})
</script>

<template>
    <section>
        <div>       
            <PageHeader :headerTitle="headerTitle" />
            <ButtonMenu :headerTitle="headerTitle" />

        </div>
        <div class="container-body-button-menu">
            <NuxtLink to="/configurador/agregar-simulador"><button class ="common-menu-button">Agregar simulador</button></NuxtLink>
            <button class="common-menu-button">Inhabilitar simulador</button>
        </div>
    </section>
</template>