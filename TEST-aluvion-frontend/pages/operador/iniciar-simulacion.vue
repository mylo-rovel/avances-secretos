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
            "submitbutton": "guardar",
            "tituloPag": "Iniciar Simulación",
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
                <PageHeader/>
            </div>
            <!--<div class= "container">
                <div class="cuadricula">
                    <div class="my-4 text-center">
                        <h2>Iniciar Simulación</h2>
                    </div>
                    <div class= "">        
                        <form>
                            <div class="row my-4"> 
                                <label for="equipo_simulacion" class="col-form-label col-sm-4  ">Seleccione Equipo</label>
                                <div class="col-sm-4">
                                    <select id="equipo_simulacion" name="equipo_simulacion" class="form-select" aria-label="Simulador">
                                        <option>Equipo simulador de lluvia</option>
                                        <option>Equipo simulador 2.0</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row my-4">
                                <label for="simulacion" class=" col-form-label col-sm-4">Seleccione Simulación</label>
                                <div class="col-sm-4">
                                    <select id="simulacion" name="simulacion" class="form-select" aria-label="Simulacion">
                                        <option>Simulación 1</option>
                                        <option>Simulación 2</option>
                                    </select>
                                </div>
                            </div>
                                <div class="contenido-botones row my-4">
                                    <div class="col">
                                        <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                    </div>
                                    <div class="col">
                                        <NuxtLink to="/operador/iniciar-simulacion"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>-->           
            <div class= "container">
                <div>
                    <div class="row my-4">
                        <h3>Iniciar Simulación</h3>
                    </div>
                    <div class="row">
                        <form>
                            <div class="my-4 form-group row">
                                <label for="equipo-simulacion" class="col-sm-4 col-form-label ">Seleccione un equipo</label>
                                <div class="col-sm-6">
                                    <select id="equipo-simulacion" name="equipo-simulacion" class="form-select" aria-label="Simulador" required>
                                        <option value="" disabled selected></option>
                                            <option>Equipo simulador de lluvia </option>
                                            <option>Simulador 2.0</option>
                                    </select>
                                </div>
                            </div>
                            <div class="my-4 form-group row">
                                <label for="simulacion" class="col-sm-4 col-form-label ">Seleccione una simulación</label>
                                <div class="col-sm-6">
                                    <select id="simulacion" name="simulacion" class="form-select" aria-label="Simulacion" required>
                                        <option value="" disabled selected></option>
                                            <option>Simulación 1</option>
                                            <option>Simulación 2</option>
                                    </select>
                                </div>
                            </div>
                            <div class = "row my-4 ">
                                <div class= "col-12 contenido-botones my-4">
                                    <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                </div>                            
                                <div class="col-12 contenido-botones my-4">    
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
   
    .contenido-botones{
        width: fit-content;
        margin: auto ;
    }
    
</style>