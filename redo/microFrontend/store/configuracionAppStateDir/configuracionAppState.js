export const configuracionAppState = {
    // OBJETO CONFIGURACION DE LA APP
    // CAMBIAR ESTOS VALORES PUEDE ROMPER LA APLICACION
    
    // DIRECCION DEL SERVIDOR DEL COMPONENTE "Backend Application"
    urlApi: "http://localhost:3001/api",
    // urlApi: "http://192.168.43.73:8081/api",

    // DURACION DEL TOKEN EN ESTA APP => DEBE SER MENOR O IGUAL A LA DURACION REAL DEL TOKEN
    // nunca debe ser menor o igual a 0 o habrán errores
    duracionTokenDias: 6,

    // CANTIDAD DE ELEMENTOS A PEDIR PARA ACTUALIZAR EL GRAFICO
    // Su proposito es no pedir listas gigantes si es que se quiere renderizar un grafico
    // de una simulacion que lleva horas corriendo
    cantValoresPorReq: 50,
}