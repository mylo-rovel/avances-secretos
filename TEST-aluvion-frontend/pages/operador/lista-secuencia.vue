<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
  name: "ListadoSecuencia",
  components: { PageHeader },
  data() {
        return {
          "headerTitle": "Listado de secuencias",
          "tituloPag": "Sistema de Alerta Temprana Aluvional"
        };
    },
})
</script>

<template>
  <section>
    <div class="container-fluid">
      <NavbarPag :tituloPag="tituloPag"/>
    </div>
    <div class="container">
        <PageHeader :headerTitle="headerTitle"/>
    </div>
    <div class="container">
      <div class="my-4 table-responsive">
        <table id="tabla-secuencias" class="table table-lifht table-bordered table-hover table-striped">
          <thead class="bg-dark text-white">
            <th scope="row">Simulador</th>
            <th scope="row">Secuencia</th>
            <th scope="row">Descripción</th>
            <th scope="row">Ver</th>
          </thead>
          <tbody id="tBody-secuencias"></tbody>
        </table>
      </div>
    </div>
  </section>

</template>