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
export const getEquiposDesplegables = (listaRawEquipos) => {
	const listaEquiposCrudos = listaRawEquipos["equipoAcotado_"];
	return listaEquiposCrudos.reduce( (acc, item) => {
		if (!(item['nombre_'] in acc)) {
			acc = {...acc, [item['nombre_']]: { idEquipo:[]}};
		}
		acc[item['nombre_']]["idEquipo"].push(item['id_']);
		//acc[item['nombreEquipo_']]["listaIds"].push(item['id_']);
		return acc;
	}, {})
}

export const getValvulasDesplegables = (listaValvulas) => {
	const listaValvulasCrudas = listaValvulas["componente_"];
	return listaValvulasCrudas.reduce( (acc, item) => {
		if (!(item['nombre_'] in acc)) {
			acc = {...acc, [item['nombre_']]: { listaIdComponente:[]}};
		}
		acc[item['nombre_']]["listaIdComponente"].push(item['id_']);
		return acc;
	}, {})
}

export const getValvulasDict = (listaValvulas) => {
	const listaValvulasCrudas = listaValvulas["componente_"];
	return listaValvulasCrudas.reduce( (acc, item) => {
		if (!(item['nombre_'] in acc)) {
			acc = {...acc, [item['nombre_']]: 0};
		}
		acc[item['nombre_']] = item['id_'];
		return acc;
	}, {})
}

// NECESARIA PARA CHEQUEAR SI EL USUARIO TIENE LOS PERMISOS PARA ESTAR EN LA PAGINA QUE VISITA
// USADA EN TODAS LAS PAGINAS
export const checkIfUserShouldBeHere = (rolesApropiados) => {
	// console.clear()
	const rolUsuario = window.localStorage.getItem("rol");
	const tokenUsuario = window.localStorage.getItem("token");
	const fechaMax = new Date(window.localStorage.getItem("fechaExpiracion"));
	if ( !(rolesApropiados.includes(rolUsuario))  || !tokenUsuario || (fechaMax <= Date.now())) {
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

export const getListasUsarDatosGrafico = (valoresArr) => {
	// 4933.33#20:27:52.447177
	return valoresArr.reduce( (acc, itemCaudal) => {
		const [caudal, hora] = itemCaudal.split("#");
		acc["caudales"].push(parseFloat(caudal));
		acc["horas"].push(hora);
		return acc
	}, {"caudales":[],"horas":[]})
}

export const listaEquipos = [
	{
		"idEquipo_":1,
		"nombreEquipo_":"Genesis66",
		"descripcion":"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
		"sensorPluv":2,
		"sensorHum":1,
		"sensorTemp":1,
		"cantValv":1
	},
	{
		"idEquipo_":2,
		"nombreEquipo_":"Genesis44",
		"descripcion":"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
		"sensorPluv":1,
		"sensorHum":1,
		"sensorTemp":1,
		"cantValv":2
	},
	{
		"idEquipo_":3,
		"nombreEquipo_":"Genesis55",
		"descripcion":"",
		"sensorPluv":1,
		"sensorHum":1,
		"sensorTemp":1,
		"cantValv":1
	},
	{
		"idEquipo_":4,
		"nombreEquipo_":"Sigma22",
		"descripcion":"",
		"sensorPluv":1,
		"sensorHum":2,
		"sensorTemp":1,
		"cantValv":1
	},
]

export const listaSimuEnEjecucion = [
	{
		"idSimulacion_":1,
		"nombreEquipo_":"Genesis66",
		"nombreSimulacion_":"Lluvia de 1994"
	},
	{
		"idSimulacion_":2,
		"nombreEquipo_":"Sigma35",
		"nombreSimulacion_":"Lluvia de 2010"
	},
	{
		"idSimulacion_":3,
		"nombreEquipo_":"simulador12",
		"nombreSimulacion_":"Lluvia de 2006"
	},
	{
		"idSimulacion_":4,
		"nombreEquipo_":"Tin2151171",
		"nombreSimulacion_":"Lluvia de 1994"
	},
]
  