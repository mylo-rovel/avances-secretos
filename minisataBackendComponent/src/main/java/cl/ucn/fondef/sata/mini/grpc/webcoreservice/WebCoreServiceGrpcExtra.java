package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Web core service grpc extra.
 */
@Service
public class WebCoreServiceGrpcExtra {

    @Autowired
    private CoreDaoExtra coreDaoExtra;
}