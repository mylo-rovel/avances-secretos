<script lang="js">

    import Vue from 'vue'
    import PageHeader from '~/components/PageHeader.vue'
    import CancelButtom from '~/components/CancelButtom.vue'
    import SubmitButton from '~/components/SubmitButton.vue'
    import SecuenciaButton from '~/components/SecuenciaButton.vue'
    import NavbarPag from '~/components/NavbarPag.vue'
    import Modal from '~/components/Modal.vue'
    import * as d3 from "d3";
    

    export default Vue.extend({
        name: "RegistrarSimulacion",
        components: { PageHeader, CancelButtom, SubmitButton, Modal, SecuenciaButton},
        data() {
            return {
                "cancelbutton": "cancelar",
                "submitbutton": "guardar",
                selectEquipo: "",
                "infoContenido": "Simulación registrada :D",
                "nombreSecuencia": "Configurar Secuencia",
            };
        },
        head(){
            return{
                title: "Sistema de Alerta Temprana Aluvional - Registrar Simulación",
                meta: [{
                    name: "registrarSimulacion",
                    content: "Registrar simulación"
                }],
            }
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
            },
            grafico(){
                console.log(d3);
                const graf = d3.selectAll("div");
                graf.data([1,2,3]).enter().append('p').text(dta => dta);                
                return graf;
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
            abrirModal(){
                document.getElementById("submit-btn").addEventListener("click",
                function() {
                    document.querySelector(".back-modal").style.display = "flex";
                });
            },
            cerrarModal(){
                document.querySelector(".close-modal").addEventListener("click",
                function() {
                    document.querySelector('.back-modal').style.display = "none";
                });
            },
            async obtenerValvulas(){
                const serverPath ='http://localhost:8081/api/equipo/valvulas/1';
                const otherHeaders = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjb3JyZW90ZXN0ZW8yQGpvdG1haWwuY29tIiwiaWF0IjoxNjU1MDU1MjAyLCJzdWIiOiJPcGVyYWRvciIsImlzcyI6Ik1haW4iLCJleHAiOjE2NTUxNDE2MDJ9.woGqMNxHLqywEZw9W_RBqf6dRECPMaDtNkQ0gg91bw8',
                    }
                }
                const serverResponse = await this.$axios.$get(serverPath, otherHeaders).catch(err => err);
                if (serverResponse instanceof Error) {
                    alert("ERROR. rayos :(", serverResponse)
                    return false;
                }
                alert(serverResponse);
                return true;
            },

            enviarRegistro: function(event){
                event.preventDefault();
                let formulario = document.getElementById("form-registrar-simulacion");
                let registros = JSON.stringify(formulario);
                console.log(registros);
            },

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
                    <h4>Registrar Simulación</h4>
                </div>
                
                <div class="row">
                    <form id ="form-registrar-simulacion" action="/nueva-simulacion" method="post">
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
                            <label for="add_nombre_simulacion" class="col-sm-4 col-form-label">Nombre Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_nombre_simulacion" type="text" class="form-control" aria-describedby="addon-wrapping" required>
                            </div>
                        </div>
                        <div class="my-4 form-group row">
                            <label for="add_nombre" class="col-sm-4 col-form-label">Secuencias</label>
                            <div  v-for="(evento, posicionSecuencia) in [66,198,287]" :key="`secKey_${posicionSecuencia}`"  class="row mb-2">
                                <div class="col-sm-4">
                                    <label for="secuencia-valvula3" class="conf-secuencias col-form-label">Válvula {{posicionSecuencia+1}}</label>
                                </div>
                                <div class="col-sm-6">
                                   <NuxtLink to="/operador/agregar-evento"><SecuenciaButton :nombreSecuencia = "nombreSecuencia" :posicionSecuencia = "posicionSecuencia"/></NuxtLink>
                                </div>
                                
                            </div>
                        </div>
                        <div class="my-4 form-group row">
                            <label for="add_descripcion_equipo" class="col-sm-4 col-form-label">Descripción Simulación</label>
                            <div class="col-sm-6">
                                <input id="add_descripcion_equipo" type="text" class="form-control" aria-describedby="addon-wrapping" required>
                            </div>
                        </div>
                        <div class = "row my-4">
                            <div class= "col-12 contenido-botones my-4">
                                <NuxtLink to="/menu-operador"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                            </div>                            
                            <div class="col-12 contenido-botones my-4">    
                                <SubmitButton :submitbutton = "submitbutton" @click = "enviarRegistro()" />
                                
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
    .back-modal{
        display: none;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        position: relative;
        top: 0;
        justify-content: center;
        align-items: center;
    }
    .modal-content {
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
    .close-modal {
        position: absolute;
        top: 0;
        right: 14px;
        font-size: 42px;
        transform: rotate(45deg);
        cursor: pointer;
    }
</style>