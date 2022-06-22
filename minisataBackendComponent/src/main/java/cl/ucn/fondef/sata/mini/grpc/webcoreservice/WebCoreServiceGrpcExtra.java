package cl.ucn.fondef.sata.mini.grpc.webcoreservice;

import cl.ucn.fondef.sata.mini.coredao.daointerface.CoreDaoExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebCoreServiceGrpcExtra {

    @Autowired
    private CoreDaoExtra coreDaoExtra;
}
