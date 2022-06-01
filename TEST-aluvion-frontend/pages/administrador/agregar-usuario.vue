<script>
import Vue from 'vue'
import Styles from '~/components/Styles.vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
    name: "agregar-usuarios",
    components: {  PageHeader },
    data(){
        return {
            "headerTitle": "Agregar usuario",
            "submitbutton":"agregar",
            "cancelbutton":"cancelar"
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
        <div class="container">
            <div class="row my-4">
                <div class="row my-4">
                    <h2>Agregar Usuario</h2>
                </div>
                <div class="row">
                    <div >
                        <form class= "g-3" action="/add-new-user" method="post">
                            <div class="row mb-3">
                                <label for="add_nombre" class="form-label col-sm-4 col-form-label">Nombre</label>
                                <div class="col-sm-6">
                                    <input id="add_nombre" type="text" class="form-control" placeholder="Nombre" aria-describedby="addon-wrapping" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_apellido" class="col-sm-4 col-form-label">Apellido</label>
                                <div class="col-sm-6">
                                    <input id="add_apellido" type="text" class="form-control" placeholder="Apellidos" aria-describedby="addon-wrapping" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_rut" class="col-sm-4 col-form-label">Rut</label>
                                <div class="col-sm-6">
                                    <input id="add_rut" type="text" class="form-control" placeholder="Rut" aria-describedby="addon-wrapping" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_correo" class="col-sm-4 col-form-label">Correo electrónico</label>
                                <div class="col-sm-6">
                                    <input id="add_correo" type="email" class="form-control" placeholder="Correo electrónico" aria-describedby="addon-wrapping" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_contrasena" class="col-sm-4 col-form-label">Contraseña</label>
                                <div class="col-sm-6">
                                    <input id="add_contrasena" type="password" class="form-control" placeholder="Ingrese una contraseña" aria-describedby="addon-wrapping" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_rol" class="col-sm-4 col-form-label">Rol</label>
                                <div class="col-sm-6">
                                    <select id="add_rol" name="rol" class="form-select" placeholder="Seleccione una opción" aria-label="Rol usuario">
                                        <option>Administrador</option>
                                        <option>Configurador</option>
                                        <option>Operador</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="add_rol" class="col-sm-4 col-form-label">Estado</label>
                                <div class="col-sm-6">
                                    <select id="add_estado" name="estado" class="form-select" aria-label="Estado Mascota">
                                        <option>Activo</option>
                                        <option>Inactivo</option>
                                    </select>
                                </div>
                            </div>
                            <div class="contenido-botones">
                                <div class="row my-4">
                                    <div class="col">
                                        <NuxtLink to="/administrador/main-admin"><CancelButtom :cancelbutton = "cancelbutton"/></NuxtLink>
                                    </div>
                                    <div class="col">
                                        <NuxtLink to="/administrador/agregar-usuario"><SubmitButton :submitbutton = "submitbutton"/></NuxtLink>
                                    </div>
                                </div>          
                            </div>
                        </form>                       
                    </div>    
                </div>
            </div>
        </div>
    </div>       
    </section>
</template>
<style>
    .contenido-botones{
        width: 700px;
        margin: 0 auto;
        padding: 20px 20px 20px 20px;
    }
    .contenido-inputs{
        width: 1000px;
        margin: 0 auto;
        padding: 20px 20px 20px 20px;
    }

</style>

