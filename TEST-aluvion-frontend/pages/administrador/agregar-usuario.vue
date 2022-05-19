<script lang="ts">
import Vue from 'vue'
import Styles from '~/components/Styles.vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
    name: "agregar-usuarios",
    components: {  PageHeader },
    data(){
        return {
            "headerTitle": "Gestión de Cuentas de Usuarios"
        };
    },
})
</script>

<template>
    <section>       
        <PageHeader :headerTitle="headerTitle" />  

        
    </section>
</template>

