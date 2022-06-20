<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'
import CancelButtom from '~/components/CancelButtom.vue'
import SubmitButton from '~/components/SubmitButton.vue'
import NavbarPag from '~/components/NavbarPag.vue'


export default Vue.extend({
  name: "RegistrarSimulacion",
  components: { PageHeader, CancelButtom, SubmitButton},
  data() {
        return {
          "cancelbutton": "cancelar",
          "submitbutton": "guardar"
        };
    },
})
</script>

<template>
    <section>
        <div>
            <NavbarPag :tituloPag="tituloPag"/> 
        </div>
        <div class="container-header">
            <PageHeader :headerTitle="headerTitle"/>
        </div>
        <div class= "container">
            <div>
                <div class="row my-4">
                    <h2>Ver Simulación</h2>
                </div>
                <div class="row">
                    <form>
                        <div class="my-4 form-group row">
                            <label for="simulador" class="col-sm-4 col-form-label ">Aqui va un gráfico</label>
                            <div class="col-sm-6">
                                <select id="simulador" name="simulador" class="form-select" aria-label="Simulador">
                                    <option>Simulador 1</option>
                                    <option>Simulador 2</option>
                                </select>
                            </div>
                        </div>

                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <NuxtLink to="/menu-operador"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>