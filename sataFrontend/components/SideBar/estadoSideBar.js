import { ref, computed } from 'vue'

export const collapsed = ref(true)
export const toggleSidebar = () => (collapsed.value = !collapsed.value)

export const SIDEBAR_WIDTH = 185
export const SIDEBAR_WIDTH_COLLAPSED = 80
export const sidebarWidth = computed(
    () => `${collapsed.value  ? SIDEBAR_WIDTH_COLLAPSED : SIDEBAR_WIDTH }px`
)

export const listaTitulosMenu = [
    {
        "nombre": "Inicio",
        "icono": "house",
        "to":"menu-operador"
    },
    {
        "nombre":"Registrar Simulacion",
        "icono":"pencil",
        "to":"registrar-simulacion"
    }, 
    {
        "nombre": "Iniciar Simulacion",
        "icono": "play",
        "to":"iniciar-simulacion"
    },
    {
        "nombre":"Equipos en Ejecucion",
        "icono":"eye",
        "to":"equipos-en-ejecucion"
    },
    {
        "nombre": "Historial Simulaciones",
        "icono": "magnifying-glass",
        "to":"lista-ejecuciones"
    }
]