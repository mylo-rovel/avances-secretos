<script>
    import Vue from 'vue';
    import Styles from '~/components/Styles.vue';
    import PageHeader from '~/components/PageHeader.vue';
    import NavbarPag from '~/components/NavbarPag.vue';
    import { checkIfUserShouldBeHere } from '~/utils/utility_functions.js';

    export default Vue.extend({
        name: "main-configurador",
        components: { PageHeader, Styles },
        data(){
            return {
                tituloPag: "Registrar nuevo equipo",
                indexPaginaRenderizada: 0,
                enumeracionesDict: {},
                equipo: {
                    nombre:"",
                    descripcion: "",
                    urlRepositorio: "",
                    estado: "",
                    listaPlacas: []
                }
            };
        },
        mounted() {
            // esto sera ejecutado cuando se renderice la pagina. dara las opciones de los dropdown menu
            this.enumeracionesDict = JSON.parse(window.localStorage.getItem("listaItemsEnum"));
        },
        methods: {
            logState() {
                console.log({...this.equipo})
            },
            updateTextField(stateField) {
                // const thisReference = this; es para no perder la referencia al 'this' al hacer una high order function
                const thisReference = this;
                return function(eventObj) {
                    const newValue = eventObj.target.value;
                    // if ( si el nuevo input matchea una regex) {}
                    thisReference.equipo[stateField] = newValue;
                }
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
        <h1 class="pageTitle">Registrar equipo</h1>
        <div v-if="0 === indexPaginaRenderizada">
            <article class="lista-contenedores">
                <article class="contenedor-input">
                    <label for="inputNuevoNombreEquipo">Nombre equipo</label>
                    <input class="inputBox" id="inputNuevoNombreEquipo" type="text"  @input="(e) => updateTextField('nombre')(e)">
                </article>

                <article class="contenedor-input">
                    <label for="inputNuevoDescripcionEquipo">Descripción equipo</label>
                    <textarea class="inputBox" rows="4" id="inputNuevoDescripcionEquipo"  @input="(e) => updateTextField('descripcion')(e)"></textarea>
                </article>

                <article class="contenedor-input">
                    <label for="inputEnlaceRepositorioEquipo">Enlace repositorio</label>
                    <input class="inputBox" id="inputEnlaceRepositorioEquipo" type="text"  @input="(e) => updateTextField('urlRepositorio')(e)">
                </article>

                <article class="contenedor-input">
                    <label for="dropdownEstadoEquipo">Estado equipo:</label>
                    <select v-model="equipo.estado" class="inputBox" name="dropdownEstadoEquipo" id="dropdownEstadoEquipo">
                        <option v-for="(estado, rowIndex) in enumeracionesDict['EstadoEquipo']" :value="estado">
                            {{estado}}
                        </option>
                    </select>
                </article>

                <article class="contenedor-input">
                    <label htmlFor="file-upload" className="custom-file-upload">Subir PDF</label>
                    <input class="inputBox" id="file-upload" type="file" onChange={handleFileInput} accept=".pdf"/>        
                </article> 
                
                <button @click="logState"> aaa </button>
            </article>
        </div>
    </section>
</template>

<style>
.pageTitle {
    margin-top: 0.5rem;
    width: fit-content;
    font-size: 1.5rem;
    padding: 0 0 0.5rem 2rem;
}

.contenedor-input label, 
.contenedor-input .inputBox {
    display: block;
}

#inputNuevoDescripcionEquipo {
    resize: none;
}

.lista-contenedores {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    width: 85vw;
    margin: 0 auto;
    height: clamp(300px, calc(800px - 45vw), 650px);
}

.lista-contenedores .contenedor-input {
    padding: 0 0.5rem 2rem 0.5rem;
    width: fit-content;
    margin: 0 auto;
}

.lista-contenedores .contenedor-input .inputBox {
    width:clamp(300px, 30vw, 400px);
}

</style>