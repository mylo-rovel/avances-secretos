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
          "headerTitle": "Agregar secuencia",
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
            <div >
                <div class="row my-4">
                    <h2>Iniciar Simulación</h2>
                </div>
                <div class="row">
                    <form>
                        <div class="row">
                            <div class="col">
                                <div class=" my-4 form-group">
                                    <label for="sim_secuencia" class="col-form-label ">Seleccione un simulador</label>
                                    <div class="col-sm-6">
                                        <select id="sim_secuencia" name="simulador" class="form-select" aria-label="Simulador">
                                            <option>Simulador 1</option>
                                            <option>Simulador 2</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col">
                                <div class=" my-4 form-group">
                                    <label for="sim_secuencia" class=" col-form-label ">Seleccione una secuencia</label>
                                    <div class="col-sm-6">
                                        <select id="sim_secuencia" name="simulador" class="form-select" aria-label="Simulador">
                                            <option>Secuencia 1</option>
                                            <option>Secuencia 2</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="my-4 form-group">
                                <label for="add_descripcion" class="col-sm-4 col-form-label">Válvulas</label>
                                <div class="col-sm-6">
                                    <ul class="row">
                                        <a class="mb-3 dropdown-item" href="#">Válvula 1</a>
                                        <a class="mb-3 dropdown-item" href="#">Válvula 2</a>
                                        <a class="mb-3 dropdown-item" href="#">Válvula 3</a>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="contenido1">
                            <div class = "row my-4">
                                <div class= "col ">
                                    <NuxtLink to="/operador/secuencia"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>
                                <div class="col">    
                                    <NuxtLink to="/operador/secuencia"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
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