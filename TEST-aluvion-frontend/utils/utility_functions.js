export const setListasDesplegables = (listaRawSimulaciones) => {
	const listaSimulacionesCrudas = listaRawSimulaciones["simulacionAcotada_"];
	return listaSimulacionesCrudas.reduce( (acc, item) => {
		if (!(item['nombreEquipo_'] in acc)) {
			acc = {...acc, [item['nombreEquipo_']]: []};
		}
		acc[item['nombreEquipo_']].push(item['nombre_']);
		return acc;
	}, {})
}