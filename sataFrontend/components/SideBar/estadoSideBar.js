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
        "nombre":"Registrar Simulación",
        "icono":"pencil",
        "to":"registrar-simulacion"
    }, 
    {
        "nombre": "Iniciar Simulación",
        "icono": "play",
        "to":"iniciar-simulacion"
    },
    {
        "nombre": "Lista de Equipos",
        "icono": "list",
        "to":"lista-equipos"
    },
    {
        "nombre":"Equipos en Ejecución",
        "icono":"eye",
        "to":"equipos-en-ejecucion"
    },
    {
        "nombre": "Historial Simulaciones",
        "icono": "magnifying-glass",
        "to":"lista-ejecuciones"
    }
]