<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
  name: "ListadoSecuencia",
  components: { PageHeader },
  data() {
        return {
          "headerTitle": "Listado de secuencias"
        };
    },
})
</script>

<templete>
  <section class="main-operador-container">
    <div class="container-header">
        <PageHeader :headerTitle="headerTitle"/>
    </div>
    <div class="col-12">
      <div class="table-responsive">
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

</templete>