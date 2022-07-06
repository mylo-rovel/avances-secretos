export const setListasDesplegables = (listaRawSimulaciones) => {
	const listaSimulacionesCrudas = listaRawSimulaciones["simulacionAcotada_"]
	return listaSimulacionesCrudas.reduce( (acc, item) => {
		nombreEquipo = item['nombreEquipo_'];
		nombreSimulacion = item['nombre_'];
		if (!(nombreEquipo in acc)) {
			acc = {...acc, [nombreEquipo]: []};
		}
		acc[nombreEquipo].push(nombreSimulacion);
		return acc;
	}, {})
}