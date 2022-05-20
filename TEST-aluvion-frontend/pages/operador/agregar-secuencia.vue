<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'



export default Vue.extend({
  name: "MainOperadorPage",
  components: { PageHeader },
  data() {
        return {
          "headerTitle": "Agregar secuencia"
        };
    },
})
</script>

<template>
    <section class="main-operador-container">
        <div class="container-header">
            <PageHeader :headerTitle="headerTitle"/>
        </div>
        <div>
            <form>
                <div class="form-group row">
                    <label for="sim_secuencia" class="col-sm-4 col-form-label">Seleccione un simulador</label>
                    <div class="col-sm-6">
                        <select id="sim_secuencia" name="simulador" class="form-select" aria-label="Simulador">
                            <option>Simulador 1</option>
                            <option>Simulador 2</option>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="add_nombre" class="col-sm-4 col-form-label">Nombre secuencia</label>
                    <div class="col-sm-6">
                        <input id="add_nombre" type="text" class="form-control" aria-describedby="addon-wrapping">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="add_descripcion" class="col-sm-4 col-form-label">Descripción</label>
                    <div class="col-sm-6">
                        <input id="add_descripcion" type="text" class="form-control" aria-describedby="addon-wrapping">
                    </div>
                </div>
            </form>
            <InputBoxComponent :textoTransparente="textoTransparente"/>
        </div>
        <div>
            <NuxtLink to="/operador/secuencia"><button class="common-menu-button atras-button">Cancelar</button></NuxtLink>
            <NuxtLink to="/operador/secuencia"><button class="common-menu-button atras-button">Guardar</button></NuxtLink>
        </div>
    </section>
</template>