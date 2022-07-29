<script>
import Vue from 'vue'
import Styles from '~/components/Styles.vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
    name: "main-admin",
    components: { PageHeader, Styles },
    data(){
        return {
            "headerTitle": "Gestión de Cuentas de Usuarios"
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
            <NuxtLink to="/administrador/agregar-usuario"><button class ="common-menu-button">Agregar usuario</button></NuxtLink>
            <button class="common-menu-button">Editar usuario</button>
            <button class="common-menu-button">Inhabilitar usuario</button>
            <button class="common-menu-button">Usuarios</button>
        </div>
    </section>
</template>