export const setListasDesplegables = (listaRawSimulaciones) => {
	const listaSimulacionesCrudas = listaRawSimulaciones["simulacionAcotada_"];
	return listaSimulacionesCrudas.reduce( (acc, item) => {
		if (!(item['nombreEquipo_'] in acc)) {
			acc = {...acc, [item['nombreEquipo_']]: {listaSimulaciones:[], listaIds:[]}};
		}
		acc[item['nombreEquipo_']]["listaSimulaciones"].push(item['nombre_']);
		acc[item['nombreEquipo_']]["listaIds"].push(item['id_']);
		return acc;
	}, {})
}

export const checkIfUserShouldBeHere = (rolesApropiados) => {
	// console.clear()
	const rolUsuario = window.localStorage.getItem("rol");
	const tokenUsuario = window.localStorage.getItem("token");
	if ( !(rolesApropiados.includes(rolUsuario))  || !tokenUsuario) {
		console.clear();
		window.localStorage.clear();
		const anchorElement = document.createElement("a");
		anchorElement.href= "/";
		anchorElement.click();
	}
}

export const getQuickAccessEnumsDict = (enumArr) => {
	return enumArr.reduce((acc, item) => {
	  return {...acc, [item["nombreEnum"]]: item["valoresEnum"]}
	}, {});
}

export const cerrarSesionUsuario = () => {
	window.localStorage.clear();
	const anchorElement = document.createElement("a");
	anchorElement.href= "/";
	anchorElement.click();
}