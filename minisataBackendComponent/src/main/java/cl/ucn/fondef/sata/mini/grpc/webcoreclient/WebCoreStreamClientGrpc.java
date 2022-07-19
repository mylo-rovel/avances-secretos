package cl.ucn.fondef.sata.mini.grpc.webcoreclient;

import cl.ucn.fondef.sata.mini.grpc.Domain.*;
import cl.ucn.fondef.sata.mini.utilities.BytesChunker;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * The type Web core stream client grpc.
 */
@Slf4j
@Service
public class WebCoreStreamClientGrpc extends WebCoreClientGrpcBase {

    /**
     * Upload archivo.
     *
     * @param file the file
     * @throws IOException the io exception
     */
// rpc uploadArchivo(stream ArchivoEntityReq)  returns (MensajeReply){}
    public void uploadArchivo(MultipartFile file) throws IOException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<MensajeReply> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(MensajeReply mensajeServer) {
                log.info("Mensaje recibido " + mensajeServer.getMensajeTexto());
            }

            @Override
            public void onCompleted() {
                log.info("Subida de imagenes completada");
                finishLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                log.warn("Subida de imagenes fallida: " + status);
                finishLatch.countDown();
            }
        };

        byte[][] fileBytesChunks = BytesChunker.getBytesChunksArray(file);
        StreamObserver<ArchivoEntityReq> requestObserver = this.asyncStub.uploadArchivo(responseObserver);

        try {
            for (int i = 0; i < fileBytesChunks.length; i++) {
                ArchivoEntity archivoEnviar = ArchivoEntity.newBuilder()
                        .setNombreEquipo("Simu1")
                        .setTipoArchivo(ArchivoEntity.TipoArchivo.JPEG)
                        .setArchivo(ByteString.copyFrom(fileBytesChunks[i]))
                        .build();
                ArchivoEntityReq archivoEntityReq = ArchivoEntityReq.newBuilder()
                        .setArchivo(archivoEnviar)
                        .build();
                log.info("ENVIANDO OBJETO: " + archivoEntityReq);
                requestObserver.onNext(archivoEntityReq);
                if (finishLatch.getCount() == 0) {
                    return; // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                }
            }
        } catch (RuntimeException e) {
            log.warn("Error RuntimeException:\n" + e);
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        requestObserver.onCompleted();
    }

}
