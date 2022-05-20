<script>
import Vue from 'vue'
import Styles from '~/components/Styles.vue'
import PageHeader from '~/components/PageHeader.vue'

export default Vue.extend({
    name: "agregar-usuarios",
    components: {  PageHeader },
    data(){
        return {
            "headerTitle": "Agregar usuario"
        };
    },
})
</script>

<template>
    <section>       
        <PageHeader :headerTitle="headerTitle" />
        <section>
            <div class="bg-modal2">
                <div class="modal-content2">
                    <div class="row">
                        <div class="col-12">
                            <form action="/add-new-user" method="post">
                                <div class="form-group row">
                                    <label for="add_nombre" class="col-sm-4 col-form-label">Nombre</label>
                                    <div class="col-sm-6">
                                        <input id="add_nombre" type="text" class="form-control" placeholder="Nombre" aria-describedby="addon-wrapping" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_apellido" class="col-sm-4 col-form-label">Apellido</label>
                                    <div class="col-sm-6">
                                        <input id="add_apellido" type="text" class="form-control" placeholder="Apellidos" aria-describedby="addon-wrapping" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_rut" class="col-sm-4 col-form-label">Rut</label>
                                    <div class="col-sm-6">
                                        <input id="add_rut" type="text" class="form-control" placeholder="Rut" aria-describedby="addon-wrapping" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_correo" class="col-sm-4 col-form-label">Correo electrónico</label>
                                    <div class="col-sm-6">
                                        <input id="add_correo" type="email" class="form-control" placeholder="Correo electrónico" aria-describedby="addon-wrapping" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_contrasena" class="col-sm-4 col-form-label">Contraseña</label>
                                    <div class="col-sm-6">
                                        <input id="add_contrasena" type="password" class="form-control" placeholder="Ingrese una contraseña" aria-describedby="addon-wrapping" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_rol" class="col-sm-4 col-form-label">Rol</label>
                                    <div class="col-sm-6">
                                        <select id="add_rol" name="rol" class="form-select" placeholder="Seleccione una opción" aria-label="Rol usuario">
                                            <option>Administrador</option>
                                            <option>Configurador</option>
                                            <option>Operador</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="add_rol" class="col-sm-4 col-form-label">Estado</label>
                                    <div class="col-sm-6">
                                        <select id="add_estado" name="estado" class="form-select" aria-label="Estado Mascota">
                                            <option>Activo</option>
                                            <option>Inactivo</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="d-grid">
                                    <div class="row">
                                        <div class="col">
                                            <NuxtLink to="/administrador/main-admin"><button class="btn btn-danger btn-login text-uppercase fw-bold" >Cancelar</button></NuxtLink>
                                            
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Ingresar</button>
                                        </div>
                                    </div>          
                                </div>
                                <hr class="my-4">
                            </form>                       
                        </div>    
                    </div>
                    <div class="close2">+</div>
                </div>
            </div>

        </section>       
    </section>
</template>

