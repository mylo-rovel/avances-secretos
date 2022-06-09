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
                    <h2>Registrar Simulación</h2>
                </div>
                <div class="row">
                    <form>
                        <div class="my-4 form-group row">
                            <label for="simulador" class="col-sm-4 col-form-label ">Seleccione un simulador</label>
                            <div class="col-sm-6">
                                <select id="simulador" name="simulador" class="form-select" aria-label="Simulador">
                                    <option>Simulador 1</option>
                                    <option>Simulador 2</option>
                                </select>
                            </div>
                        </div>
                        <div class="my-4 form-group row">
                            <label for="add_nombre" class="col-sm-4 col-form-label">Secuencias</label>
                            <!--<div class="col-sm-6">
                                <input id="add_nombre" type="text" class="form-control" aria-describedby="addon-wrapping">
                            </div>-->
                            <div class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula1" class="conf-secuencias col-form-label">Válvula 1</label>
                                </div>
                                <div class="col-sm-6">
                                    <NuxtLink to="/WIP-agregar-evento"><button class="btn-secuencias form-control">Configurar Secuencia 1</button></NuxtLink>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula2" class="conf-secuencias col-form-label">Válvula 2</label>
                                </div>
                                <div class="col-sm-6">
                                    <NuxtLink to="/WIP-agregar-evento"><button class=" btn-secuencias form-control">Configurar Secuencia 2</button></NuxtLink>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula3" class="conf-secuencias col-form-label">Válvula 3</label>
                                </div>
                                <div class="col-sm-6">
                                    <NuxtLink to="/WIP-agregar-evento"><button class="btn-secuencias form-control">Configurar Secuencia 3</button></NuxtLink>
                                </div>
                            </div>
                        </div>                  
                        <div class="my-4 form-group row">
                            <label for="add_descripcion" class="col-sm-4 col-form-label">Descripción</label>
                            <div class="col-sm-6">
                                <input id="add_descripcion" type="text" class="form-control" aria-describedby="addon-wrapping">
                            </div>
                        </div>
                        <div class = "row my-4 conf-secuencias btn-opciones">
                            <div class= "col">
                                <NuxtLink to="/operador/main-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col">    
                                <NuxtLink to="/operador/registrar-simulacion"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
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
    .conf-secuencias{
        margin: 0 auto;
        padding-left: 4rem;
    }
    .btn-opciones{
        width: fit-content;
    }
    .btn-secuencias{
        background-color: #ecf0f1;
        border: 1px solid #d0d3d4;
        color: black;
        padding: 0.25rem 1.5rem;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 1rem;
        margin: 0;
        cursor: pointer;
        border-radius: 10px;
    }
    .btn-secuencias:hover{
        background: #ecf0f1;
        border: 1px solid #d0d3d4;
        color: black;
        transform: scale(1.1);
    }
</style>