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
                filesList: [],
                tituloPag: "Registrar nuevo equipo",
                indexPaginaRenderizada: 0,
                enumeracionesDict: {},
                currentObjects : {
                    currentPlaca: null,
                    currentComponente: null,
                    currentPin: null
                },
                equipo: {
                    nombre:"",
                    descripcion: "",
                    urlRepositorio: "",
                    estado: "",
                    listaPlacas: [],
                    listaComponentes: []
                }
            };
        },
        mounted() {
            // esto sera ejecutado cuando se renderice la pagina. dara las opciones de los dropdown menu
            this.enumeracionesDict = JSON.parse(window.localStorage.getItem("listaItemsEnum"));
        },
        methods: {
            logState() {
                console.log({...this})
            },
            updateTextField(stateField) {
                // const thisReference = this; es para no perder la referencia al 'this' al hacer una high order function
                const thisReference = this;
                return function(eventObj) {
                    const newValue = eventObj.target.value;
                    // if ( si el nuevo input matchea una regex) {}
                    thisReference.equipo[stateField] = newValue;
                }
            },
            updateFileReceived(e) {
                var files = e.target.files || e.dataTransfer.files;
                if (!files.length) return;
                console.log(files);
                this.filesList = files;
            },
            goToNextPage() {
                const maxIndexPagina = 1;
                if ( this.indexPaginaRenderizada >= maxIndexPagina ) return;
                this.indexPaginaRenderizada += 1 
            },
            goToPrevPage() { 
                if ( this.indexPaginaRenderizada <= 0 ) return;
                this.indexPaginaRenderizada -= 1 
            },


            // acá va algo pero no recuerdo
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
        <!-- Primera parte: Primeros datos del equipo -->
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
                    <input class="inputBox" id="file-upload" type="file" @change="(e) => updateFileReceived(e)" multiple="multiple"/>        
                </article> 
                
            </article>
        </div>

        <!-- Segunda parte: Datos de las placas a usar -->
        <div v-if="3 === indexPaginaRenderizada">
            <article class="lista-contenedores">
                <article class="contenedor-input">
                    <label for="inputNuevoNombreEquipo">Nombre placa</label>
                    <input class="inputBox" id="inputNuevoNombreEquipo" type="text"  @input="(e) => updateTextField('nombre')(e)">
                </article>

                <article class="contenedor-input">
                    <label for="inputNuevoDescripcionEquipo">Descripción placa</label>
                    <textarea class="inputBox" rows="4" id="inputNuevoDescripcionEquipo"  @input="(e) => updateTextField('descripcion')(e)"></textarea>
                </article>

                <article class="contenedor-input">
                    <label for="dropdownTipoPlaca">TipoComponente:</label>
                    <select v-model="equipo.listaPlacas" class="inputBox" name="dropdownTipoPlaca" id="dropdownTipoPlaca">
                        <option v-for="(estado, rowIndex) in enumeracionesDict['TipoPlaca']" :value="estado">
                            {{estado}}
                        </option>
                    </select>
                </article>
                
            </article>
        </div>

        <article class="page-control-ribbon">
            <button @click="logState"> LogState </button>
            <button @click="goToNextPage"> Siguiente </button>
            <button @click="goToPrevPage"> Atrás </button>
        </article>
    </section>
</template>

<style>

.page-control-ribbon {
    display:flex;
    width: fit-content;
    margin: 0 auto;
    flex-wrap: wrap;
}

.page-control-ribbon button{
    margin: 0 2rem;
    background-color: #ececec;
    border: 1px solid #666666;
    padding: 0.25rem 0.85rem;
    border-radius: 10px;
}

.page-control-ribbon button:last-child,
.page-control-ribbon button:first-child {
    margin: 0;
}



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
    height: clamp(300px, calc(800px - 45vw), 550px);
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