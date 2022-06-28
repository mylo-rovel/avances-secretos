// rpc RecordRoute(stream Point) returns (RouteSummary) {}
// rpc uploadArchivo(stream ArchivosEquipoEntityReq)  returns (MensajeReply){}
public void uploadArchivo(List<archivosEquipo> archivos, int numFiles) throws InterruptedException {
  info("*** uploadArchivo");
  final CountDownLatch finishLatch = new CountDownLatch(1);
  StreamObserver<MensajeResultadoOperacion> responseObserver = new StreamObserver<MensajeResultadoOperacion>() {
    @Override
    public void onNext(MensajeResultadoOperacion mensajeServer) {
      info("Mensaje recibido {0}", mensajeServer.getMensajeTexto);
    }



    @Override
    public void onError(Throwable t) {
      Status status = Status.fromThrowable(t);
      logger.log(Level.WARNING, "Subida de imagenes fallida: {0}", status);
      finishLatch.countDown();
    }

    @Override
    public void onCompleted() {
      info("Subida de imagenes completada");
      finishLatch.countDown();
    }
  };

  StreamObserver<ImagenesEquipo> requestObserver = asyncStub.subirImagenesEquipo(responseObserver);
  try {
    // Send numPoints points randomly selected from the features list.
    Random rand = new Random();
    for (int i = 0; i < numPoints; ++i) {

      requestObserver.onNext(point);
      // Sleep for a bit before sending the next one.
      Thread.sleep(rand.nextInt(1000) + 500);
      if (finishLatch.getCount() == 0) {
        // RPC completed or errored before we finished sending.
        // Sending further requests won't error, but they will just be thrown away.
        return;
      }
    }
  } catch (RuntimeException e) {
    // Cancel RPC
    requestObserver.onError(e);
    throw e;
  }
  // Mark the end of requests
  requestObserver.onCompleted();

  // Receiving happens asynchronously
  finishLatch.await(1, TimeUnit.MINUTES);
}}
