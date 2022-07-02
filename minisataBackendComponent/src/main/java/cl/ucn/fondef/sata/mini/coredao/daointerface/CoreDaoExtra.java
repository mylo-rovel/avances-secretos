/*
 * Copyright (c) 2022 Fondef IDeA I+D.
 */

package cl.ucn.fondef.sata.mini.coredao.daointerface;

import cl.ucn.fondef.sata.mini.grpc.Domain;
import cl.ucn.fondef.sata.mini.model.Registro;

import java.util.List;

/**
 * The interface Core dao extra.
 */
public interface CoreDaoExtra {
    List<Registro> getRegistros(Domain.RutEntityReq rutEntityReq);
}
