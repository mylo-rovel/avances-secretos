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
                "submitbutton": "guardar",
                selectEquipo: "",
            };
        },
        methods: {
            /*mostrarEquipo(){
                var equipoActual = document.getElementById('equipo_simulacion').selectedIndex;
                document.getElementById('nombre_equipo').value = equipo[equipoActual].nombre;
                console.log(equipoActual)

                selectEquipo = document.getElementById("equipo_simulacion").addEventListener('change', 
                function(){
                    var seleccionOpcion = this.options[selectEquipo.selectedIndex];
                    console.log(seleccionOpcion.value +':'+seleccionOpcion.text);
                    console.log("probando");
                });
            },
        
           desactivarForm(formulario){
                if((formulario.equipo_simulacion.value == 0) || (formulario.add_descripcion.value == 0)){
                    alert("falta info");
                    return false;
                }else{
                    return true;
                }
            },*/

            mensaje: function (event){
                var hola = "probando";
                console.log(hola);
                alert(hola);
            },

            nombreEquipo() {
                for (let i = 0; i < equipos.length; i++) {
                    if (document.getElementById('id_equipo').value == equipos[i].id) {
                        document.getElementById('nombre_equipo').value = equipos[i].nombre;
                    }
                }
            },
            //Cuando se haga click al boton secuencia
            modalPrueba(){
                document.getElementById("boton_prueba").addEventListener("click",
                function() {
                    document.querySelector(".bg-modal2").style.display = "flex";
                });
            }
        }
    })
</script>

<template>
    <section>
        <div>
            <NavbarPag/> 
        </div>
        <div class="container-header">
            <PageHeader />
        </div>
        <div class= "container">
            <div>
                <div class="row my-4">
                    <h2>Registrar Simulación</h2>
                </div>
                
                <div class="row">
                    <form id ="form-registrar-simulacion" action="/nueva-simulacion" @submit = "desactivarForm(this);" method="post">
                        <div class="my-4 form-group row">
                            <label for="id_equipo" class="col-sm-4 col-form-label ">Seleccione un equipo</label>
                            <div class="col-sm-6">
                                <select id="id_equipo" name="nombre_equipo" class="form-select" @onclick="seleccionarEquipo();" aria-label="Simulador" required>
                                    <option value="" disabled selected></option>
                                        <option>Equipo simulador de lluvia</option>
                                        <option>Simulador 2.0</option>
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
                                <!-- asociar funcion con el evento click
                                tomar el valor del id del boton-->
                                    <NuxtLink  to="/operador/agregar-evento"><button id="secuencia1" class="btn-secuencias form-control">Configurar Secuencia 1</button></NuxtLink>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula2" class="conf-secuencias col-form-label">Válvula 2</label>
                                </div>
                                <div class="col-sm-6">
                                    <NuxtLink to="/operador/agregar-evento"><button class=" btn-secuencias form-control">Configurar Secuencia 2</button></NuxtLink>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula3" class="conf-secuencias col-form-label">Válvula 3</label>
                                </div>
                                <div class="col-sm-6">
                                    <NuxtLink to="/operador/agregar-evento"><button class="btn-secuencias form-control">Configurar Secuencia 3</button></NuxtLink>
                                </div>
                            </div>
                        </div>
                        <button id="boton_prueba" type="button" class="btn btn-primary" @click = "modalPrueba()" >pinchar aqui</button> 
                        <div class= "bg-modal2">
                            <div class="modal-content2">
                                <h3>hola holaholaholaholaholaholaholaholaholaholaholahola</h3>
                            </div>
                        </div>                  
                        <div class="my-4 form-group row">
                            <label for="add_descripcion_equipo" class="col-sm-4 col-form-label">Descripción</label>
                            <div class="col-sm-6">
                                <input id="add_descripcion_equipo" type="text" class="form-control" aria-describedby="addon-wrapping" required>
                            </div>
                        </div>
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <NuxtLink to="/operador/registrar-simulacion"><SubmitButton :submitbutton = "submitbutton" /></NuxtLink>
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
    .conf-secuencias{
        margin: 0 auto;
        padding-left: 4rem;
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
        /*transform: scale(1.1);*/
    }
    .bg-modal2 {
        display: none;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        position: absolute;
        top: 0;
        justify-content: center;
        align-items: center;
    }
    .modal-content2 {
        /*width: 500px;
        height: 300px;*/
        background-color: white;
        border-radius: 10px;
        text-align: center;
        padding: 5px;
        position: relative;
        margin-left: 40px;
        margin-right: 40px;
    }
</style>