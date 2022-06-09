<script lang="ts">

import Vue from 'vue'
import PageHeader from '~/components/PageHeader.vue'
import CancelButtom from '~/components/CancelButtom.vue'
import SubmitButton from '~/components/SubmitButton.vue'
import NavbarPag from '~/components/NavbarPag.vue'


export default Vue.extend({
  name: "MainOperadorPage",
  components: { PageHeader, CancelButtom, SubmitButton},
  data() {
        return {
          "cancelbutton": "cancelar",
          "submitbutton": "iniciar"
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
            <div class="card">
                <div class="row my-4">
                    <h2>Iniciar Simulación</h2>
                </div>
                <div class="row">
                    <form>
                        <div class="grid">
                            <div class="g-col-6 g-col-md-4">
                                <div class=" my-4 form-group">
                                    <label for="sim_secuencia" class="col-form-label  ">Seleccione Equipo</label>
                                    <div class="col-sm-6 center">
                                        <select id="sim_secuencia" name="simulador" class="form-select" aria-label="Simulador">
                                            <option>Equipo simulador de lluvia</option>
                                            <option>Equipo simulador 2.0</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="g-col-6 g-col-md-4">
                                <div class=" my-4 form-group">
                                    <label for="sim_secuencia" class=" col-form-label ">Seleccione Simulación</label>
                                    <div class="col-sm-6 center">
                                        <select id="sim_secuencia" name="simulador" class="form-select" aria-label="Simulador">
                                            <option>Simulación 1</option>
                                            <option>Simulación 2</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="contenido1">
                            <div class = "row my-4">
                                <div class= "col ">
                                    <NuxtLink to="/operador/main-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>
                                <div class="col">    
                                    <NuxtLink to="/operador/iniciar-simulacion"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>
<style>
    .contenido1{
        width: 700px;
        margin: 0 auto;
        padding: 20px 20px 20px 20px;
    }
</style>